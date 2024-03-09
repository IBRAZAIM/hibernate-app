package order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user", "Пользователь"),
    MODERATOR("moder", "Модератор"),
    ADMIN("admin", "Администратор");

    private final String serviceName;
    private final String displayName;
}
