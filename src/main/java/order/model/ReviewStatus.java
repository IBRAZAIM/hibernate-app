package order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewStatus {
    PUBLISHED("ОПУБЛИКОВАНО"),
    NOT_PUBLISHED(" НЕ ОПУБЛИКОВАНО");
    private final String displayedName;
}
