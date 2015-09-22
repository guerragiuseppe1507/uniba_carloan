package test.businessTier;

import java.util.HashMap;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import businessTier.GestioneClienti;

public class GestioneClientiTest extends TestCase {

	private GestioneClienti gestore;
	
	String[][] datiClienteTest = {
			
			{"singolo", "8"}	
			
	};
	
	String[] clientiExpectedMail = {"tizio8@foo.it"};
	
	String[][] inserisciTest = {
			
			{"cliente1", "cliente1", "tizio@foo.it", "Bari(BA)", "2015-07-30", "12g2323gy2324", "121251221412"},
			{"cliente3", "cliente3", "tizio2@foo.it", "Bari(BA)", "2014-04-30", "34f3424g66h46", "25367588964"},
			{"cliente4", "cliente4", "tizio3@foo.it", "Bari(BA)", "2015-03-30", "trgkw5kerekk", "232646558823"},
			{"cliente5", "cliente5", "tizio4@foo.it", "Bari(BA)", "2015-08-30", "fabaawabwbw4", "232366576874"},
			{"cliente6", "cliente6", "tizio5@foo.it", "Bari(BA)", "2012-07-30", "awbdwadb4b677", "235646578434"},
			{"cliente7", "cliente7", "tizio6@foo.it", "Bari(BA)", "2015-09-30", "dmgsmrssmfesf4", "23423455858"},
			{"cliente8", "cliente8", "tizio7@foo.it", "Bari(BA)", "2011-05-30", "sdnafat4tmsst4", "235465684266"},
			{"cliente9", "cliente9", "tizio8@foo.it", "Bari(BA)", "2010-07-30", "fmafmfhmtrdna3", "2346574884226"},
			
	};
	
	String[] inserisciExpectedResult = {"false", "false", "false", "false", "false", "false", "false", "false"};
	
	@Before
	public void setUp() throws Exception {
		gestore = new GestioneClienti();
	}

	@After
	public void tearDown() throws Exception {
		gestore = null;
	}

	@Test
	public void testRecuperoDatiClienti() {
		for(int i = 0 ; i < datiClienteTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("filtro", datiClienteTest[i][0]);
			test.put("id_cliente", datiClienteTest[i][1]);
			HashMap<String,String> login = gestore.recuperoDatiClienti(test);
			assertEquals(clientiExpectedMail[i], login.get("email0"));
		}
		
	}
	
	@Test
	public void testInserisciDatiCliente() {
		for(int i = 0 ; i < inserisciTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("nome", inserisciTest[i][0]);
			test.put("cognome", inserisciTest[i][1]);
			test.put("mail", inserisciTest[i][2]);
			test.put("residenza", inserisciTest[i][3]);
			test.put("data_di_nascita", inserisciTest[i][4]);
			test.put("cod_fiscale", inserisciTest[i][5]);
			test.put("cod_patente", inserisciTest[i][6]);
			HashMap<String,String> login = gestore.inserisciDatiCliente(test);
			assertEquals(inserisciExpectedResult[i], login.get("esito"));
		}
		
	}
	
}