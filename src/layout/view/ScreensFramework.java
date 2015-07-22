package layout.view;

import util.Md5Encrypter;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Questa classe � la principale dell' intera applicazione ed � qui che troviamo il main.
 * Il compito principale di ScreensFramework � quello di riunire sotto un unica classe
 * creazione e caricamento di tutte le schermate della gui.
 * Inoltre � qui che viene creato lo stage applicativo su cui verranno visualizzate le schermate
 * caricate tramite fogli fxml.
 */
public class ScreensFramework extends Application {
	
	public static final double DEFAULT_WIDTH = 1280.00 ;
	public static final double DEFAULT_HEIGHT = 750.00 ;
	public static final double DEFAULT_MENU_WIDTH = 1280.00 ;
	public static final double DEFAULT_MENU_HEIGHT = 30.00 ;

	/* Aggiungere qui ID e source file per ogni schermata del programma.
	 * L' ID � necessario per permettere l'individuazione delle schermate da parte dei 
	 * controller dell' interfaccia e il file indica quale fxml caricare per la specifica schermata
	 */
	public static String loginID = "Login";
	public static String loginFile = "Login.fxml";
	public static String homeManagerDiSistemaID = "HomeManagerDiSistema";
	public static String homeManagerDiSistemaFile = "HomeManagerDiSistema.fxml";
	public static String homeManagerDiSistemaTitle = "Home Manager Di Sistema";
	public static String homeManagerDiFilialeID = "HomeManagerDiFiliale";
	public static String homeManagerDiFilialeFile = "HomeManagerDiFiliale.fxml";
	public static String homeManagerDiFilialeTitle = "Home Manager Di Filiale";
	public static String homeDipendenteDiFilialeID = "HomeDipendenteDiFiliale";
	public static String homeDipendenteDiFilialeFile = "HomeDipendenteDiFiliale.fxml";
	public static String homeDipendenteDiFilialeTitle = "Home Dipendente Di Filiale";
	public static String insertManagerID = "InsertManager";
	public static String insertManagerFile = "InsertManager.fxml";
	public static String insertManagerTitle = "Gestione Manager";
	public static String manageAutoID = "ManageAuto";
	public static String manageAutoFile = "ManageAuto.fxml";
	public static String manageAutoTitle = "Gestione Auto";
	public static String gestioneDipendentiID = "GestioneDipendenti";
	public static String gestioneDipendentiFile = "GestioneDipendenti.fxml";
	public static String gestioneDipendentiTitle = "Gestione Dipendenti";
	public static String gestioneProfiloID = "InfoProfilo";
	public static String gestioneProfiloFile = "InfoProfilo.fxml";
	public static String gestioneProfiloTitle = "Gestione Profilo";
	public static String gestioneContrattoID = "GestioneContratto";
	public static String gestioneContrattoFile = "GestioneContratto.fxml";
	public static String gestioneContrattoTitle = "Gestione Contratto";
	public static String InserimentoContrattoID = "InserimenoContratto";
	public static String InserimentoContrattoFile = "InserimenoContratto.fxml";
	public static String InserimentoContrattoTitle = "Inserimeno Contratto";
	
	
	
	
	private ScreensController mainContainer;
	public static Stage PRIMARY_STAGE;
	public static String APP_NAME = "CARLOAN";
	
	@Override
	public void start(Stage primaryStage){
		
		this.PRIMARY_STAGE = primaryStage;
		
		//Vieni istanziato il controller delle schermate.
		mainContainer = new ScreensController();
		
		
		mainContainer.loadScreen(ScreensFramework.loginID,ScreensFramework.loginFile);
		//le altre verranno caricate al login in base all utente

	
		//Impostazione della schermata principale da visualizzare al lancio dell' applicazione.
		mainContainer.setScreen(ScreensFramework.loginID); 
		
		//Aggiunta di tutte le schermate ad un unica radice e impostazione dello stage
		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		
		//Impostazione delle dimensioni
		primaryStage.setWidth(DEFAULT_WIDTH); 
		primaryStage.setHeight(DEFAULT_HEIGHT);
		
		//La schermata non � personalizzabile nelle dimensioni(per una maggiore affidabilit� a differenti risoluzioni)
		primaryStage.setResizable(false);
		
		
		primaryStage.setTitle(APP_NAME);
		
		//Impostazione della scena iniziale e visualizzazione della stessa
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void loadManagerSistemaScreens(ScreensController c){
		//Il controller carica tutte le schermate date come parametro al metodo loadScreen.
		c.loadScreen(ScreensFramework.homeManagerDiSistemaID,ScreensFramework.homeManagerDiSistemaFile);
		c.loadScreen(ScreensFramework.insertManagerID,ScreensFramework.insertManagerFile);

	}
	
	public static void loadManagerFilialeScreens(ScreensController c){
		//Il controller carica tutte le schermate date come parametro al metodo loadScreen.
		c.loadScreen(ScreensFramework.homeManagerDiFilialeID,ScreensFramework.homeManagerDiFilialeFile);
		c.loadScreen(ScreensFramework.manageAutoID,ScreensFramework.manageAutoFile);
		c.loadScreen(ScreensFramework.gestioneDipendentiID,ScreensFramework.gestioneDipendentiFile);
		c.loadScreen(ScreensFramework.gestioneProfiloID,ScreensFramework.gestioneProfiloFile);
		c.loadScreen(ScreensFramework.gestioneContrattoID,ScreensFramework.gestioneContrattoFile);
		
	}
	
	public static void loadDipendenteFilialeScreens(ScreensController c){
		//Il controller carica tutte le schermate date come parametro al metodo loadScreen.
		c.loadScreen(ScreensFramework.homeDipendenteDiFilialeID,ScreensFramework.homeDipendenteDiFilialeFile);
		c.loadScreen(ScreensFramework.gestioneProfiloID,ScreensFramework.gestioneProfiloFile);
		c.loadScreen(ScreensFramework.InserimentoContrattoID,ScreensFramework.InserimentoContrattoFile);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}

