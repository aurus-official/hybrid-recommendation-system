package com.aurus.server.shared;

public enum CategoryType {
    IRRIGATION("irrigation"), SOIL_NUTRIENT("soil_nutrient"), MICROCLIMATE("microclimate"),
    CROP_OPERATION("crop_operation");

    public final String value;

    CategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
