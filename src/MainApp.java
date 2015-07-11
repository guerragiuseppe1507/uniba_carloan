import java.io.IOException;

import layout.view.InsertDipendenteController;
import layout.view.InsertManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import layout.view.LoginController;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public MainApp() {
	}
	

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		
		initRootLayout();
		//showLogin();
		//showInsertManager();
		//showInsertDipendente();
		showManageAuto();
	}
	
	public void initRootLayout(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/view/RootLayout.fxml"));
			this.rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(this.rootLayout);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public void showLogin(){
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/view/Login.fxml"));
			AnchorPane login = (AnchorPane) loader.load();
			
			this.rootLayout.setCenter(login);
			
			LoginController controller = loader.getController();
			//controller.setMainApp(this);
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public void showInsertManager(){
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/view/InsertManager.fxml"));
			AnchorPane InsertManager = (AnchorPane) loader.load();
			
			this.rootLayout.setCenter(InsertManager);
			
			//InsertManagerController controller = loader.getController();
			//controller.setMainApp(this);
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public void showInsertDipendente(){
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/view/InsertDipendentediFiliale.fxml"));
			AnchorPane InsertDipendente = (AnchorPane) loader.load();
			
			this.rootLayout.setCenter(InsertDipendente);
			
			//InsertDipendenteController controller = loader.getController();
			//controller.setMainApp(this);
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
public void showManageAuto(){
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/view/ManageAuto.fxml"));
			AnchorPane ManageAuto = (AnchorPane) loader.load();
			
			this.rootLayout.setCenter(ManageAuto);
			
			//InsertDipendenteController controller = loader.getController();
			//controller.setMainApp(this);
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	public Stage getPrimaryStage() {
		
		return this.primaryStage;
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
