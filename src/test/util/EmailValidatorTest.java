package test.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.EmailValidator;
import junit.framework.TestCase;

public class EmailValidatorTest extends TestCase{
	
	String[] emailTest = {".@","daniele&libero.it","%%%%%@hotmail/","aaa%$$£$ttt","giuseppe)(oo.com","salla[].éélibhot","cazzarola.fastweb","234545555.it@gui","$@soi.to", "cern@fis. ot","cir@ soi. ret", "giuseppe @ honda.it","val rio@hon.tre","giuseppe@piu.it  "
			,"giuseppe.cio@libero.it", "@com", "@.com", "giuseppe@libero.it", "giovanni32@gmail.com", "ciaociao@boh.ret", 
			"11ciao34@tau.rar","1234@ar.com","mario.it", "1234", "@", "@mol.it", "&&32vario@mail.com", "@gmail", ".it", " ","vincyfetta91@libero"};	
	boolean[] emailExpected = {false,false,false,false,false,false,false,true,true, false, false, false, false, true, true, false, false, true, true, true, true, true, false, false, false, false, true, false, false, false,true};
	
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testIsValidEmailAddress() {
		for(int i = 0; i < emailTest.length; i++){
			
			boolean result = EmailValidator.isValidEmailAddress(emailTest[i]);
			assertEquals(emailExpected[i], result);
			
		}
	}

}
