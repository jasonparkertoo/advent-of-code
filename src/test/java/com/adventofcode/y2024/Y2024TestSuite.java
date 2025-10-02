package com.adventofcode.y2024;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Year 2024 TestSuite")
@SelectClasses({
    Day1Tests.class, Day2Tests.class,
    Day3Tests.class, Day4Tests.class,
    Day5Tests.class, Day6Tests.class
})
public class Y2024TestSuite {

}