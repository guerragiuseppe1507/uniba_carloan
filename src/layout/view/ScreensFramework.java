package layout.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Questa classe è la principale dell' intera applicazione ed è qui che troviamo il main.
 * Il compito principale di ScreensFramework è quello di riunire sotto un unica classe
 * creazione e caricamento di tutte le schermate della gui.
 * Inoltre è qui che viene creato lo stage applicativo su cui verranno visualizzate le schermate
 * caricate tramite fogli fxml.
 */
public class ScreensFramework extends Application {
	
	public static final double DEFAULT_WIDTH = 1280.00 ;
	public static final double DEFAULT_HEIGHT = 750.00 ;
	public static final double DEFAULT_MENU_WIDTH = 1280.00 ;
	public static final double DEFAULT_MENU_HEIGHT = 30.00 ;

	/* Aggiungere qui ID e source file per ogni schermata del programma.
	 * L' ID è necessario per permettere l'individuazione delle schermate da parte dei 
	 * controller dell' interfaccia e il file indica quale fxml caricare per la specifica schermata
	 */

	public static String loginFile = "Login.fxml";
	public static String homeManagerDiSistemaFile = "HomeManagerDiSistema.fxml";
	public static String homeManagerDiSistemaTitle = "Home Manager Di Sistema";
	public static String homeManagerDiFilialeFile = "HomeManagerDiFiliale.fxml";
	public static String homeManagerDiFilialeTitle = "Home Manager Di Filiale";
	public static String homeDipendenteDiFilialeFile = "HomeDipendenteDiFiliale.fxml";
	public static String homeDipendenteDiFilialeTitle = "Home Dipendente Di Filiale";
	public static String insertManagerFile = "InsertManager.fxml";
	public static String insertManagerTitle = "Gestione Manager";
	public static String manageAutoFile = "ManageAuto.fxml";
	public static String manageAutoTitle = "Gestione Auto";
	public static String gestioneDipendentiFile = "GestioneDipendenti.fxml";
	public static String gestioneDipendentiTitle = "Gestione Dipendenti";
	public static String gestioneProfiloFile = "InfoProfilo.fxml";
	public static String gestioneProfiloTitle = "Gestione Profilo";
	public static String gestioneContrattoFile = "GestioneContratto.fxml";
	public static String gestioneContrattoTitle = "Miei Contratti";
	public static String gestioneContrattoFilialeTitle = "Gestione Contratti Filiale";
	public static String inserimentoContrattoFile = "InserimentoContratto.fxml";
	public static String inserimentoContrattoTitle = "Stipulazione Contratto";
	public static String modificaContrattoTitle = "Modifica Contratto";
	public static String chiusuraContrattoFile = "ChiusuraContratto.fxml";
	public static String chiusuraContrattoTitle = "Chiusura Contratto";
	
	//Schermate pop-up
	
	public static String InserimentoClienteFile = "NuovoCliente.fxml";
	public static String InserimentoClienteTitle = "Nuovo Cliente";
	
	private ScreensController mainContainer;
	public static Stage PRIMARY_STAGE;
	public static String APP_NAME = "CARLOAN";
	
	@Override
	public void start(Stage primaryStage){
		
		ScreensFramework.PRIMARY_STAGE = primaryStage;
		
		//Vieni istanziato il controller delle schermate.
		mainContainer = new ScreensController();
		
		mainContainer.showScreen(ScreensFramework.loginFile);
		
		//Aggiunta di tutte le schermate ad un unica radice e impostazione dello stage
		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		
		//Impostazione delle dimensioni
		primaryStage.setWidth(DEFAULT_WIDTH); 
		primaryStage.setHeight(DEFAULT_HEIGHT);
		
		//La schermata non è personalizzabile nelle dimensioni(per una maggiore affidabilità a differenti risoluzioni)
		primaryStage.setResizable(false);
		
		
		primaryStage.setTitle(APP_NAME);
		
		//Impostazione della scena iniziale e visualizzazione della stessa
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}

