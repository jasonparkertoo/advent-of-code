package com.adventofcode.y2024.util;

import java.nio.file.Path;

public class PathUtil {

    private static final Path exampleDir = Path.of("src", "test", "resources", "examples");
    private static final Path challengeDir = Path.of("src", "test", "resources", "challenges");

    public static Path getExampleData(String fileName) {
        return Path.of(PathUtil.exampleDir.toString(), fileName);
    }

    public static Path getChallengeData(String fileName) {
        return Path.of(PathUtil.challengeDir.toString(), fileName);
    }
}