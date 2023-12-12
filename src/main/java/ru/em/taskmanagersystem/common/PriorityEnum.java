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
public enum PriorityEnum {

    LOW(1, "Низкий"),
    MEDIUM(2, "Средний"),
    HIGH(3, "Высокий");

    private final int id;
    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }


    public static List<Map<String, Object>> getAllPriorities() {
        List<Map<String, Object>> priorities = new ArrayList<>();
        for (PriorityEnum priority : PriorityEnum.values()) {
            Map<String, Object> priorityMap = new HashMap<>();
            priorityMap.put("id", priority.getId());
            priorityMap.put("value", priority.getValue());
            priorities.add(priorityMap);
        }
        return priorities;
    }

    @JsonCreator
    public static PriorityEnum forValue(String value) {
        for (PriorityEnum priority : PriorityEnum.values()) {
            if (priority.value.equals(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid value");
    }
}
