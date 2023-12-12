package ru.em.taskmanagersystem.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum StatusEnum {

    WAITING(1, "В ожидании"),
    IN_PROGRESS(2, "В процессе"),
    COMPLETE(3, "Завершена");

    private final int id;
    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static List<Map<String, Object>> getAllStatuses() {
        List<Map<String, Object>> statuses = new ArrayList<>();
        for (StatusEnum status : StatusEnum.values()) {
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("id", status.getId());
            statusMap.put("value", status.getValue());
            statuses.add(statusMap);
        }
        return statuses;
    }

    @JsonCreator
    public static StatusEnum forValue(String value) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value");
    }
}


