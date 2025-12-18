package com.adventofcode.util;

public enum Year {
    YEAR_2024("2024"), YEAR_2025("2025");
    
    private final String year;
    
    Year(String year) {
        this.year = year;
    }
    
    public String getYear() {
        return this.year;
    }
}