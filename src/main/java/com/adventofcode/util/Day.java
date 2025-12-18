package com.adventofcode.util;

public enum Day {
    DAY1("day1"),
    DAY2("day2"),
    DAY3("day3"),
    DAY4("day4"),
    DAY5("day5"),
    DAY6("day6"),
    DAY7("day7"),
    DAY8("day8"),
    DAY9("day9"),
    DAY10("day10"),
    DAY11("day11"),
    DAY12("day12");
    
    private final String day;
    
    Day(String day) {
        this.day = day;
    }
    
    public String getDay() {
        return this.day;
    }
} 