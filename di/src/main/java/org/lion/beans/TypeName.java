package org.lion.beans;

public class TypeName {
    private final String name;
    private final String type;
    private final String required;
    private final String defaultValue;

    public TypeName(String name, String type, String required, String defaultValue) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRequired() {
        return required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return "TypeName{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", required='" + required + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}

