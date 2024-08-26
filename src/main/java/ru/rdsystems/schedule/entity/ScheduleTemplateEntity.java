package ru.rdsystems.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import ru.rdsystems.schedule.common.helpers.DateTimeConverter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="schedule_template")
public class ScheduleTemplateEntity {
    @Id
    @Column(nullable = false, length = 32)
    @NonNull
    private String id;

    @Column(nullable = false, name = "creation_date")
    @NonNull
    @JsonIgnore
    private OffsetDateTime created;

    // Подмена полей для JSON
    @JsonProperty("created")
    public LocalDateTime getCreatedForJSON() {
        return DateTimeConverter.toLocalDateTime(created);
    }
}
