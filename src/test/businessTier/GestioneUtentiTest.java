package test.businessTier;

import java.util.HashMap;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import businessTier.GestioneUtenti;

public class GestioneUtentiTest extends TestCase {

	private GestioneUtenti gestore;
	
	String[][] datiUtentiTest = {
			
			{"filiale", "8"},
			{"filiale", "9"},
			{"filiale", "11"},
			{"filiale", "12"},
			{"filiale", "13"},
			{"filiale", "17"}
			
	};
	
	String[] utentiExpectedCount = {"3","3","3","3","3","0"};
	
	@Before
	public void setUp() throws Exception {
		gestore = new GestioneUtenti();
	}

	@After
	public void tearDown() throws Exception {
		gestore = null;
	}

	@Test
	public void testRecuperoDatiUtenti() {
		for(int i = 0 ; i < datiUtentiTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("filter", datiUtentiTest[i][0]);
			test.put("idFiliale", datiUtentiTest[i][1]);
			HashMap<String,String> login = gestore.recuperoDatiUtenti(test);
			assertEquals(utentiExpectedCount[i], login.get("resLength"));
		}
	}
		
}
	