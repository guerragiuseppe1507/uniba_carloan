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
	public static String loginID = "Login";
	public static String loginFile = "Login.fxml";
	public static String homeManagerDiSistemaID = "HomeManagerDiSistema";
	public static String homeManagerDiSistemaFile = "HomeManagerDiSistema.fxml";
	public static String homeManagerDiFilialeID = "HomeManagerDiFiliale";
	public static String homeManagerDiFilialeFile = "HomeManagerDiFiliale.fxml";
	public static String homeDipendenteDiFilialeID = "HomeDipendenteDiFiliale";
	public static String homeDipendenteDiFilialeFile = "HomeDipendenteDiFiliale.fxml";
	public static String insertManagerID = "InsertManager";
	public static String insertManagerFile = "InsertManager.fxml";
	public static String insertDipendenteDiFilialeID = "InsertDipendenteDiFiliale";
	public static String insertDipendenteDiFilialeFile = "InsertDipendenteDiFiliale.fxml";
	public static String manageAutoID = "ManageAuto";
	public static String manageAutoFile = "ManageAuto.fxml";
	
	//Variabile globale che memorizza la sessione dell' utente tramite la mail.
	public static String userId;
	private ScreensController mainContainer;
	
	@Override
	public void start(Stage primaryStage){
		
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
		
		//La schermata non è personalizzabile nelle dimensioni(per una maggiore affidabilità a differenti risoluzioni)
		primaryStage.setResizable(false);
		
		//Impostazione della scena iniziale e visualizzazione della stessa
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}

