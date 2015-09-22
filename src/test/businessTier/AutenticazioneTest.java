package test.businessTier;

import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import businessTier.Autenticazione;
import util.Md5Encrypter;
import junit.framework.TestCase;

public class AutenticazioneTest extends TestCase{
	
	private static final String MS = "Manager Di Sistema";
	private static final String MF = "Manager Di Filiale";
	private static final String DF = "Dipendente Di Filiale";
	private static final String UNA = "utenteNonAssegnato";
	
	String[][] loginTest = {
			
			{"utente5", "password"},
			{"utente1", "password"},	
			{"ciao", "password"},
			{"admin", "admin"},
			{"utente13", "password"}
			
	};
	
	//l'id è diverso da null se e solo se l utente è mf o df 
	//servono per istanziare il singleton insieme agli altri dati
	String[] loginExpectedId = {"6", "2", null, null, null};
	String[] loginExpectedType = {MF, DF, null, MS, UNA};
	
	String[][] registerTest = {
			
			{"utente1", "email@foo.it", "password"},
			{"utente21", "pin@fino.non", "password"},	
			{"utente2", "a@b.c", "password"},
			{"utente7", "tri@r.it", "password"},
			{"utente13", "bla@k.no", "password"}
			
	};
	
	String[] registerExpectedResult = {"false", "false", "false", "false", "false"};
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testLogin() {
		Autenticazione aut = new Autenticazione();
		for(int i = 0 ; i < loginTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("username", loginTest[i][0]);
			test.put("password", Md5Encrypter.encrypt(loginTest[i][1]));
			HashMap<String,String> login = aut.login(test);
			assertEquals(loginExpectedId[i], login.get("id"));
			assertEquals(loginExpectedType[i], login.get("tipoUtente"));
		}
		
	}
	
	@Test
	public void testRegister() {
		Autenticazione aut = new Autenticazione();
		for(int i = 0 ; i < registerTest.length ; i++){
			HashMap<String,String> test = new HashMap<String,String>();
			test.put("username", registerTest[i][0]);
			test.put("email", registerTest[i][1]);
			test.put("password", Md5Encrypter.encrypt(registerTest[i][2]));
			HashMap<String,String> login = aut.register(test);
			assertEquals(registerExpectedResult[i], login.get("esito"));
		}
		
	}

}
