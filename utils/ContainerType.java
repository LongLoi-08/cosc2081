package utils;

public enum ContainerType {
    DRY_STORAGE("DRY STORAGE"),
    OPEN_TOP("OPEN TOP"),
    OPEN_SIDE("OPEN_SIDE"),
    REFRIGERATED("REFRIGERATED"),
    LIQUID("LIQUID");

    private final String type;

    private ContainerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
