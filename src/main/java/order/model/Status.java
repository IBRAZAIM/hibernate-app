package order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    CREATED("Создан"),
    IN_DELIVERY("В пути"),
    DELIVERED("Доставлено");
    private final  String displayName;
}
