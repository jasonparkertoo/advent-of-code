package com.adventofcode.y2025;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Year.YEAR_2025;
import static com.adventofcode.input.Day.DAY2;

public class Day2Tests {
    
    private static final Data exampleData = new Data(EXAMPLE, YEAR_2025, DAY2);
    private static final Data challengeData = new Data(CHALLENGE, YEAR_2025, DAY2);
        
	@Test
	void testA() {
	    var day2 = new Day2(exampleData);
		var results = day2.sumInvalidIds();

		var expected = 1227775554;
		var actual = results.getFirst();

		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	void testB() {
	    var day2 = new Day2(challengeData);
		var results = day2.sumInvalidIds();

		var expected = 15873079081L;
		var actual = results.getFirst();

		Assertions.assertEquals(expected, actual);
	}	
	
	@Test
	void testC() {
	    var day2 = new Day2(exampleData);
		var results = day2.sumInvalidIds();

		var expected = 4174379265L;
		var actual = results.getLast();

		Assertions.assertEquals(expected, actual);
	}	
	
	@Test
	void testD() {
	    var day2 = new Day2(challengeData);
		var results = day2.sumInvalidIds();

		var expected = 22617871034L;
		var actual = results.getLast();

		Assertions.assertEquals(expected, actual);
	}
}