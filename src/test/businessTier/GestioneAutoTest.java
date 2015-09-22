package test.businessTier;

import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import businessTier.GestioneAuto;
import junit.framework.TestCase;

public class GestioneAutoTest extends TestCase{
	
	private GestioneAuto gestore;
	
	String[][] datiAutoTest = {
			
			{"disponibile", "12", null},
			{"non_disponibile", "12", null},	
			{"singola", null, "57"},
			
	};
	
	String[] AutoExpectedCount = {"8", "3", "1"};
	
	@Before
	public void setUp() throws Exception {
		gestore = new GestioneAuto();
	}

	@After
	public void tearDown() throws Exception {
		gestore = null;
	}

	@Test
	public void testRecuperoDatiAuto() {
		for(int i = 0 ; i < datiAutoTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("disponibilita", datiAutoTest[i][0]);
			test.put("id_filiale", datiAutoTest[i][1]);
			test.put("id_auto", datiAutoTest[i][2]);
			HashMap<String,String> login = gestore.recuperoDatiAuto(test);
			assertEquals(AutoExpectedCount[i], login.get("resLength"));
			//assertEquals(loginExpectedType[i], login.get("tipoUtente"));
		}
		
	}
	

}
