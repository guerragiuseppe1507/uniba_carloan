package test.util;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.NumberPlateValidator;

public class NumberPlateValidatorTest extends TestCase {

	String[] plateTest = {"AAA-BB-111", "AAa-BB-111", "AAA-BB-A11", "A-B-1", "AAA--111", "AA-B-11", "AAA-9B-111", "4AA-BB-111", "@@@-BB-111", 
			"AAA+BB-111", "GH-BR-11", "^}O-BB-111", "AAA-BB-1.11", "AAA-111", "AAA-BB-111-FF", "JK-BB-1", "AZ-A-0", "PAL-LA-01"};	
	boolean[] plateExpected = {true, false, false, true, false, true, false, false, false, 
			false, true, false, false, false, false, true, true, true};
	
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testValidate() {
		for(int i = 0; i < plateTest.length; i++){
			
			boolean result = NumberPlateValidator.validate(plateTest[i], "Italia");
			assertEquals(plateExpected[i], result);
			
		}
	}

}
