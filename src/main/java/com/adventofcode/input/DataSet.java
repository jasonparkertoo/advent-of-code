package com.adventofcode.input;

public enum DataSet {
    EXAMPLE("examples"),
    CHALLENGE("challenges");
    
    private final String directoryName;

    private DataSet(final String directoryName) {
        this.directoryName = directoryName;
    }
    
    public String directoryName() {
        return this.directoryName;
    }
}