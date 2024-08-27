package ru.rdsystems.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rdsystems.schedule.entity.ScheduleEntity;

import java.util.List;
import java.util.Map;

public interface ScheduleRepo extends JpaRepository<ScheduleEntity, String> {

    @Query(value = """
          select
            s.schedule_name,
            sp.slot_type,
            sl.begin_time,
            sl.end_time,
            e.employee_name as executor_name,
            e.status as executor_status,
            e.position as executor_position,
            e_a.employee_name as admin_name,
            e_a.status as admin_status,
            e_a.position as admin_position
          from {h-schema}schedule s
          join {h-schema}schedule_period sp on sp.schedule_id = s.id
          join {h-schema}slot sl on sp.slot_id = sl.id
          join {h-schema}schedule_template st on sl.schedule_template_id = st.id
          join {h-schema}employee e_a on sp.administrator_id = e_a.id
          left join {h-schema}employee e on sp.executor_id = e.id
          where (
            coalesce(s.id, :id) = :id
                or
            coalesce(s.schedule_name, :name) = :name
          )
          order by
            sl.begin_time asc,
            sl.end_time desc
          """,
            nativeQuery = true)
    List<Map<String, Object>> getSchedule(String id, String name);
}
