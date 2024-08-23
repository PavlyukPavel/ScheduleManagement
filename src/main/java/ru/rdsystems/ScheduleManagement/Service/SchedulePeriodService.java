package ru.rdsystems.ScheduleManagement.Service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.rdsystems.ScheduleManagement.Common.Enums.ShedulePeriodColumnsEnum;
import ru.rdsystems.ScheduleManagement.Common.Enums.SlotTypeEnum;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleBadParamsException;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.Common.Helpers.DateTimeConverter;
import ru.rdsystems.ScheduleManagement.Common.Helpers.IDGenerator;
import ru.rdsystems.ScheduleManagement.Common.Helpers.StringUtils;
import ru.rdsystems.ScheduleManagement.DTO.FilterAndSort.SchedulePeriodFilterAndSort;
import ru.rdsystems.ScheduleManagement.DTO.FilterAndSort.SchedulePeriodSort;
import ru.rdsystems.ScheduleManagement.DTO.Request.SchedulePeriodPostRq;
import ru.rdsystems.ScheduleManagement.Entity.SchedulePeriodEntity;
import ru.rdsystems.ScheduleManagement.Entity.SlotEntity;
import ru.rdsystems.ScheduleManagement.Repository.SchedulePeriodRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulePeriodService {

    @Autowired
    private final SchedulePeriodRepo schedulePeriodRepo;

    @Autowired
    private final SlotService slotService;

    @Autowired
    private final ScheduleService scheduleService;

    @Autowired
    private final EmployeeService employeeService;

    @Transactional
    public SchedulePeriodEntity post(SchedulePeriodPostRq rq, String administratorId) {
        SlotEntity slot = slotService.get(rq.getSlotId());
        List<SchedulePeriodEntity> listPeriods = schedulePeriodRepo.getIntersectPeriods(rq.getExecutorId(), rq.getScheduleId(), slot.getBeginTime(), slot.getEndTime());
        if (listPeriods.isEmpty()) {
            SchedulePeriodEntity spe = SchedulePeriodEntity.builder()
                    .id(IDGenerator.generate())
                    .slotId(slot)
                    .scheduleId(scheduleService.get(rq.getScheduleId()))
                    .slotType(SlotTypeEnum.valueOf(rq.getSlotType()))
                    .administratorId(employeeService.get(administratorId))
                    .executorId(StringUtils.isValid(rq.getExecutorId()) ? employeeService.get(rq.getExecutorId()) : null)
                    .build();
            schedulePeriodRepo.save(spe);
            return spe;
        } else {
            throw new ScheduleBadParamsException("Обнаружены пересекающиеся периоды!");
        }
    }

    public SchedulePeriodEntity get(String id) {
        return schedulePeriodRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Период расписания %s не найден", id))
        );
    }

    public List<SchedulePeriodEntity> filter(SchedulePeriodFilterAndSort filterAndSort) {
        Specification<SchedulePeriodEntity> specification = buildSpecification(filterAndSort);
        Pageable requiredPages = getRequiredPages(filterAndSort);
        if (requiredPages != null) {
            Page<SchedulePeriodEntity> selection = schedulePeriodRepo.findAll(specification, requiredPages);
            return selection.toList();
        } else {
            return schedulePeriodRepo.findAll(specification);
        }
    }

    private Specification<SchedulePeriodEntity> buildSpecification(SchedulePeriodFilterAndSort filterAndSort) {
        var specPredicates = new ArrayList<Specification<SchedulePeriodEntity>>();
        if (filterAndSort == null) {
            return Specification.allOf();
        }

        if (StringUtils.isValid(filterAndSort.getFilter().getId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("id"), filterAndSort.getFilter().getId()));
        }
        if (StringUtils.isValid(filterAndSort.getFilter().getSlotId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("slotId").get("id"), filterAndSort.getFilter().getSlotId()));
        }
        if (StringUtils.isValid(filterAndSort.getFilter().getScheduleId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("scheduleId").get("id"), filterAndSort.getFilter().getScheduleId()));
        }
        if (StringUtils.isValid(filterAndSort.getFilter().getSlotType())) {
            SlotTypeEnum slotType;
            try {
                slotType = SlotTypeEnum.valueOf(filterAndSort.getFilter().getSlotType().toUpperCase());
            } catch (Exception e) {
                throw new ScheduleBadParamsException("Некорректное значение slotType");
            }
            specPredicates.add((root, query, cb) -> cb.equal(root.get("slotType"), slotType));
        }
        if (StringUtils.isValid(filterAndSort.getFilter().getAdministratorId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("administratorId").get("id"), filterAndSort.getFilter().getAdministratorId()));
        }
        if (StringUtils.isValid(filterAndSort.getFilter().getExecutorId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("executorId").get("id"), filterAndSort.getFilter().getExecutorId()));
        }
        if (filterAndSort.getFilter().getBeginTime() != null || filterAndSort.getFilter().getEndTime() != null) {
            specPredicates.add(
                    (root, query, cb) ->
                        {
                            Join<SlotEntity, SchedulePeriodEntity> periodsSlot = root.join("slotId");
                            List<Predicate> predicateList = new ArrayList<>();
                            if (filterAndSort.getFilter().getBeginTime() != null) {
                                predicateList.add(
                                        cb.greaterThanOrEqualTo(periodsSlot.get("beginTime"), DateTimeConverter.toOffsetTime(filterAndSort.getFilter().getBeginTime()))
                                );
                            }
                            if (filterAndSort.getFilter().getEndTime() != null) {
                                predicateList.add(
                                        cb.lessThanOrEqualTo(periodsSlot.get("endTime"), DateTimeConverter.toOffsetTime(filterAndSort.getFilter().getEndTime()))
                                );
                            }
                            return predicateList.stream()
                                    .reduce(cb::and)
                                    .orElseThrow();
                        }
                    );
        }
        return Specification.allOf(specPredicates);
    }

    private Pageable getRequiredPages(SchedulePeriodFilterAndSort filterAndSort) {
        Pageable requiredPages = null;
        if (filterAndSort.getPage() != null && filterAndSort.getSize() != null) {
            if (filterAndSort.getPage() < 0 ) {
                throw new ScheduleBadParamsException("Некорректное значение Page");
            } else if (filterAndSort.getSize() <= 0) {
                throw new ScheduleBadParamsException("Некорректное значение Size");
            }
            List<Sort.Order> sorts= new ArrayList<>();
            for (SchedulePeriodSort sps : filterAndSort.getSort()) {
                if (sps.getField() == null) {
                    throw new ScheduleBadParamsException("Не указано значение sort.field");
                } else if (sps.getDirection() == null) {
                    throw new ScheduleBadParamsException("Не указано значение sort.direction");
                }
                if (sps.getField() == ShedulePeriodColumnsEnum.beginTime || sps.getField() == ShedulePeriodColumnsEnum.endTime) {
                    sorts.add(new Sort.Order(sps.getDirection(),"slotId." + sps.getField().name()));
                } else {
                    sorts.add(new Sort.Order(sps.getDirection(), sps.getField().name()));
                }
            }
            requiredPages = PageRequest.of(filterAndSort.getPage(), filterAndSort.getSize(), Sort.by(sorts));
        }
        return requiredPages;
    }
}
