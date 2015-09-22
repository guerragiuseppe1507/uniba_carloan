package test.businessTier;

import java.util.HashMap;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import businessTier.GestioneContratti;

public class GestioneContrattiTest extends TestCase {
	
	private GestioneContratti gestore;
	
	String[][] datiContrattiTest = {
			
			{"GESTIONE_FILIALE", "APERTO", "5", "8"}	
			
	};
	
	String[] contrattiExpectedCount = {"1"};
	
	@Before
	public void setUp() throws Exception {
		gestore = new GestioneContratti();
	}

	@After
	public void tearDown() throws Exception {
		gestore = null;
	}

	@Test
	public void testRecuperoDatiContratti() {
		for(int i = 0 ; i < datiContrattiTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("filter", datiContrattiTest[i][0]);
			test.put("status", datiContrattiTest[i][1]);
			test.put("id_dipendente", datiContrattiTest[i][2]);
			test.put("id_filiale", datiContrattiTest[i][3]);
			HashMap<String,String> login = gestore.recuperoDatiContratto(test);
			assertEquals(contrattiExpectedCount[i], login.get("resLength"));
		}
		
	}
	
}