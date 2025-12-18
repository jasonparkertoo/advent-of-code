package com.adventofcode.y2025;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.util.PathUtil;

import static com.adventofcode.util.PathUtil.getExampleLines;
import static com.adventofcode.util.Year.YEAR_2025;
import static com.adventofcode.util.Day.DAY2;
import static com.adventofcode.util.PathUtil.getChallengeLines;

public class Day2Tests {

	@Test
	void testA() {
	    var day2 = new Day2(PathUtil.getExampleLines(YEAR_2025, DAY2));
		var results = day2.sumInvalidIds();

		var expected = 1227775554;
		var actual = results.getFirst();

		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	void testB() {
	    var day2 = new Day2(getChallengeLines(YEAR_2025, DAY2));
		var results = day2.sumInvalidIds();

		var expected = 15873079081L;
		var actual = results.getFirst();

		Assertions.assertEquals(expected, actual);
	}	
	
	@Test
	void testC() {
	    var day2 = new Day2(getExampleLines(YEAR_2025, DAY2));
		var results = day2.sumInvalidIds();

		var expected = 4174379265L;
		var actual = results.getLast();

		Assertions.assertEquals(expected, actual);
	}	
	
	@Test
	void testD() {
	    var day2 = new Day2(getChallengeLines(YEAR_2025, DAY2));
		var results = day2.sumInvalidIds();

		var expected = 22617871034L;
		var actual = results.getLast();

		Assertions.assertEquals(expected, actual);
	}
}