package com.adventofcode.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PathUtil {

    private static final Path resourceDir = Path.of("src", "test", "resources");
    
    public static Path getExampleDataPath(Year year, Day day) {
        return Path.of(PathUtil.resourceDir.toString(), year.getYear(), "examples", day.getDay());
    }

    public static Path getChallengeDataPath(Year year, Day day) {
        return Path.of(PathUtil.resourceDir.toString(), year.getYear(), "challenges", day.getDay());
    }
    
    public static List<String> getChallengeLines(Year year, Day day) {
        try {
            return Files.lines(getChallengeDataPath(year, day))
                .filter(l -> !l.isBlank())
                .toList();
        } catch (IOException ex) {
            throw new IllegalArgumentException("you suck!", ex);
        }
    }

    public static List<String> getExampleLines(Year year, Day day) {
        try {
            return Files.lines(getExampleDataPath(year, day))
                .filter(l -> !l.isBlank())
                .toList();
        } catch (IOException ex) {
            throw new IllegalArgumentException("you suck!", ex);
        }
    }
}