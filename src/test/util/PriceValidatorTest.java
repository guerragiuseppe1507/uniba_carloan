package test.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.PriceValidator;
import junit.framework.TestCase;

public class PriceValidatorTest extends TestCase{
	
	private static final String err = PriceValidator.PRICE_ERR;
	
	String[] priceTest = {".9", "fdhfd", "9.", "900", "9000" , "0", ",", "0,0", "", "     ", "90.00 €", "90.00",
			"999.99", "999.999", "000.00", ".55", "dieci euro"};
	String[] priceExpected = {"0.90 €", err, "9.00 €", "900.00 €", err, "0.00 €", err, err, "0.00 €", "0.00 €", err, "90.00 €",
			"999.99 €", err, "000.00 €", "0.55 €", err};
	
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testValidatePrice() {
		for(int i = 0; i < priceTest.length; i++){
			
			String result = PriceValidator.validatePrice("€", priceTest[i]);
			assertEquals(priceExpected[i], result);
			
		}
	}

}
