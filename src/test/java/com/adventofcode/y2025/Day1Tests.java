package com.adventofcode.y2025;

import static com.adventofcode.util.Day.DAY1;
import static com.adventofcode.util.Year.YEAR_2025;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.util.PathUtil;

public class Day1Tests {

	@Test
	void TestA() {
	    var day1 = new Day1(PathUtil.getExampleLines(YEAR_2025, DAY1));
		
		var expected = 3;			
		var actual = day1.solve();
		
		Assertions.assertEquals(expected, actual.partOne());
	}

	@Test
	void TestB() {
	    var day1 = new Day1(PathUtil.getChallengeLines(YEAR_2025, DAY1));
		
		var expected = 1195;			
		var actual = day1.solve();
		
		Assertions.assertEquals(expected, actual.partOne());
	}

	@Test
	void TestC() {
	    var day1 = new Day1(PathUtil.getExampleLines(YEAR_2025, DAY1));
		
		var expected = 6;			
		var actual = day1.solve();
		
		Assertions.assertEquals(expected, actual.partTwo());
	}
	
	@Test
	void TestD() {
	    var day1 = new Day1(PathUtil.getChallengeLines(YEAR_2025, DAY1));
		
		var expected = 6770;			
		var actual = day1.solve();
		
		Assertions.assertEquals(expected, actual.partTwo());
	}
}