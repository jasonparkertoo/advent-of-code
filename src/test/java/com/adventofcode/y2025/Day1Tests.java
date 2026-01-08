package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY1;
import static com.adventofcode.input.Year.YEAR_2025;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

public class Day1Tests {

    private static final Data exampleData = new Data(EXAMPLE, YEAR_2025, DAY1);
    private static final Data challengeData = new Data(CHALLENGE, YEAR_2025, DAY1);
    
	@Test
	void testA() {
	    var day1 = new Day1(exampleData);
		var results = day1.solve();
		var expected = 3;			
		var actual = results.getFirst();
		
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void testB() {
	    var day1 = new Day1(challengeData);
		var results = day1.solve();
		
		var expected = 1195;			
		var actual = results.getFirst();
		
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void testC() {
	    var day1 = new Day1(exampleData);
		var results = day1.solve();
		
		var expected = 6;			
		var actual = results.getLast();
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	void testD() {
	    var day1 = new Day1(challengeData);
		var results = day1.solve();
		
		var expected = 6770;			
		var actual = results.getLast();
		
		Assertions.assertEquals(expected, actual);
	}
}