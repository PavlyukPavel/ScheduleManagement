package ru.rdsystems.schedule.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.rdsystems.schedule.common.enums.SchedulePeriodColumnsEnum;
import ru.rdsystems.schedule.common.enums.SlotTypeEnum;
import ru.rdsystems.schedule.common.exceptions.ScheduleBadParamsException;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.DateTimeConverter;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.dto.filtersort.SchedulePeriodFilterAndSort;
import ru.rdsystems.schedule.dto.filtersort.SchedulePeriodSort;
import ru.rdsystems.schedule.dto.request.SchedulePeriodPostRq;
import ru.rdsystems.schedule.entity.SchedulePeriodEntity;
import ru.rdsystems.schedule.entity.SlotEntity;
import ru.rdsystems.schedule.repository.SchedulePeriodRepo;
import ru.rdsystems.schedule.service.EmployeeService;
import ru.rdsystems.schedule.service.SchedulePeriodService;
import ru.rdsystems.schedule.service.ScheduleService;
import ru.rdsystems.schedule.service.SlotService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSchedulePeriodService implements SchedulePeriodService {

    private final SchedulePeriodRepo schedulePeriodRepo;
    private final SlotService slotService;
    private final ScheduleService scheduleService;
    private final EmployeeService employeeService;

    @Transactional @Override public SchedulePeriodEntity post(SchedulePeriodPostRq rq, String administratorId) {
        SlotEntity slot = slotService.get(rq.getSlotId());
        List<SchedulePeriodEntity> listPeriods = schedulePeriodRepo.getIntersectPeriods(rq.getExecutorId(), rq.getScheduleId(), slot.getBeginTime(), slot.getEndTime());
        if (listPeriods.isEmpty()) {
            SchedulePeriodEntity spe = SchedulePeriodEntity.builder()
                    .id(Utils.generateID())
                    .slotId(slot)
                    .scheduleId(scheduleService.get(rq.getScheduleId()))
                    .slotType(SlotTypeEnum.valueOf(rq.getSlotType()))
                    .administratorId(employeeService.get(administratorId))
                    .executorId(Utils.isValidStr(rq.getExecutorId()) ? employeeService.get(rq.getExecutorId()) : null)
                    .build();
            schedulePeriodRepo.save(spe);
            return spe;
        } else {
            throw new ScheduleBadParamsException("Обнаружены пересекающиеся периоды!");
        }
    }

    @Override public SchedulePeriodEntity get(String id) {
        return schedulePeriodRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Период расписания %s не найден", id))
        );
    }

    @Override public List<SchedulePeriodEntity> filter(SchedulePeriodFilterAndSort filterAndSort) {
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

        if (Utils.isValidStr(filterAndSort.getFilter().getId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("id"), filterAndSort.getFilter().getId()));
        }
        if (Utils.isValidStr(filterAndSort.getFilter().getSlotId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("slotId").get("id"), filterAndSort.getFilter().getSlotId()));
        }
        if (Utils.isValidStr(filterAndSort.getFilter().getScheduleId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("scheduleId").get("id"), filterAndSort.getFilter().getScheduleId()));
        }
        if (Utils.isValidStr(filterAndSort.getFilter().getSlotType())) {
            SlotTypeEnum slotType;
            try {
                slotType = SlotTypeEnum.valueOf(filterAndSort.getFilter().getSlotType().toUpperCase());
            } catch (Exception e) {
                throw new ScheduleBadParamsException("Некорректное значение slotType");
            }
            specPredicates.add((root, query, cb) -> cb.equal(root.get("slotType"), slotType));
        }
        if (Utils.isValidStr(filterAndSort.getFilter().getAdministratorId())) {
            specPredicates.add((root, query, cb) -> cb.equal(root.get("administratorId").get("id"), filterAndSort.getFilter().getAdministratorId()));
        }
        if (Utils.isValidStr(filterAndSort.getFilter().getExecutorId())) {
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
                if (sps.getField() == SchedulePeriodColumnsEnum.beginTime || sps.getField() == SchedulePeriodColumnsEnum.endTime) {
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
