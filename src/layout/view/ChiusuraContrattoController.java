package layout.view;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import presentationTier.FrontController;
import util.NotificationManager;
import util.PriceValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import layout.model.entities.Auto;
import layout.model.entities.Contratto;
import layout.model.entities.Fascia;

public class ChiusuraContrattoController  implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	//prezzi
	@FXML
	private Text tariffaBaseText;
	@FXML
	private Text costoText;
	@FXML
	private Text penaleText;
	@FXML
	private Text limiteText;
	@FXML
	private Label tariffaBaseLabel;
	@FXML
	private Label costoLabel;
	@FXML
	private Label penaleLabel;
	@FXML
	private Label limiteLabel;
	
	//stato
	@FXML
	private Label dataInizioLabel;
	@FXML
	private Label dataLimiteLabel;
	@FXML
	private Label dataRestituzioneLabel;
	@FXML
	private Label limiteKmLabel;
	@FXML
	private Label kmPercorsiLabel;
	
	//conto
	@FXML
	private Text contoLine1Text;
	@FXML
	private Text contoLine2Text;
	@FXML
	private Text contoLine3Text;
	@FXML
	private Text contoLine4Text;
	@FXML
	private Label contoLine1Label;
	@FXML
	private Label contoLine2Label;
	@FXML
	private Label contoLine3Label;
	@FXML
	private Label contoLine4Label;
	
	@FXML
	private Label totLabel;
	
	private Contratto contratto;
	private Auto auto;
	private int newKm;
	
	private boolean inTempo = false;
	private boolean inKm = false;
	
	private int weeksPast;
	private int daysPast;
	
	private int giorniInPiu;
	private int settimaneInPiu;
	
	private LocalDate data_rientro;
	
	private double tot;
	
	HashMap<String, String> prezzi = new HashMap<String, String>();
	
	@Override
	public void initialize(URL url, ResourceBundle rb){	
		
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}

	@Override
	public void riempiCampi() {
		setParams();
		caricaPrezzi(auto.getFascia());
		impostaPrezzi();
		impostaStatoContratto();
		impostaConto();
	}
	
	private void setParams(){
		
		contratto = (Contratto) InterStageEventsHandler.getInstance().params.get("ChiudiContratto_contratto");
		auto = (Auto) InterStageEventsHandler.getInstance().params.get("ChiudiContratto_auto");
		newKm = Integer.parseInt(InterStageEventsHandler.getInstance().options.get("ChiudiContratto_newKm"));
		data_rientro = LocalDate.now();
		
		InterStageEventsHandler.getInstance().params.remove("ChiudiContratto_contratto");
		InterStageEventsHandler.getInstance().params.remove("ChiudiContratto_auto");
		InterStageEventsHandler.getInstance().options.remove("ChiudiContratto_newKm");
		
	}
	
	private void caricaPrezzi(String fascia){
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiPrezzi"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("nomeFascia", fascia);
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			prezzi.put("tariffa_base_g",risultato.get("tariffa_base_g"));
			prezzi.put("tariffa_base_s",risultato.get("tariffa_base_s"));
			prezzi.put("costo_chilometrico",risultato.get("costo_chilometrico"));
			prezzi.put("penale_chilometri",risultato.get("penale_chilometri"));
			prezzi.put("tariffa_illim_g",risultato.get("tariffa_illim_g"));
			prezzi.put("tariffa_illim_s",risultato.get("tariffa_illim_s"));
		}
	}	 
	
	private void impostaPrezzi(){
		try{ 
			 
			if(contratto.getTariffa().equals("GIORNALIERA")){
				tariffaBaseText.setText("Tariffa base giornaliera :");
				tariffaBaseLabel.setText(
						PriceValidator.validatePrice("€", prezzi.get("tariffa_base_g")));
				if (contratto.getTipoKm().equals("LIMITATO")){
					limiteText.setText("Limite chilometri giornalieri :");
					limiteLabel.setText(Integer.toString(Fascia.LIM_KM_G)+" km");
				} 
			} else {
				tariffaBaseText.setText("Tariffa Base Settimanale :");
				tariffaBaseLabel.setText(
						PriceValidator.validatePrice("€", prezzi.get("tariffa_base_s")));
				if (contratto.getTipoKm().equals("LIMITATO")){
					limiteText.setText("Limite chilometri settimanali");
					limiteLabel.setText(Integer.toString(Fascia.LIM_KM_S)+" km");
				}
			}
			 
			if(contratto.getTipoKm().equals("LIMITATO")){
				costoText.setText("Costo chilometraggio limitato :");
				penaleText.setText("Penale chilometraggio limitato :");
				costoLabel.setText(
						PriceValidator.validatePrice("€", prezzi.get("costo_chilometrico")));
				penaleLabel.setText(
						PriceValidator.validatePrice("€", prezzi.get("penale_chilometri")));
			} else {
				costoText.setText("Costo chilometraggio Illimitato :");
				penaleText.setText("");
				penaleLabel.setText("");
				limiteText.setText("");
				limiteLabel.setText("");
				if(contratto.getTariffa().equals("GIORNALIERA")){
					costoLabel.setText(
							PriceValidator.validatePrice("€", prezzi.get("tariffa_illim_g")));
				} else {
					costoLabel.setText(PriceValidator.validatePrice("€", prezzi.get("tariffa_illim_s")));
				}
				
			}
			
		} catch (NullPointerException e){} 
		 
	}
	
	private void impostaStatoContratto(){
		dataInizioLabel.setText(contratto.getDataInizio());
		dataLimiteLabel.setText(contratto.getDataLimite());
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final LocalDate limitDate = LocalDate.parse(contratto.getDataLimite(),dtf);
		final LocalDate startDate = LocalDate.parse(contratto.getDataInizio(),dtf);
		int i = limitDate.compareTo(data_rientro);
		if(i >= 0){
			inTempo = true;
			dataRestituzioneLabel.setText(data_rientro.toString());
		} else {
			giorniInPiu = Period.between(limitDate, data_rientro).getDays();
			dataRestituzioneLabel.setText(data_rientro.toString() + " *");
		}
		
		daysPast = Period.between(startDate, data_rientro).getDays() + 1;
		weeksPast = (int) Math.ceil((double) daysPast / (double) 7);
		settimaneInPiu = (int) Math.ceil((double) giorniInPiu / (double) 7);
		int kmLimite = Integer.MAX_VALUE;
		if (contratto.getTipoKm().equals("LIMITATO")){
			if(contratto.getTariffa().equals("GIORNALIERA")){
				kmLimite = Fascia.LIM_KM_G * daysPast;
				limiteKmLabel.setText(Integer.toString(kmLimite) + " km");
			} else if(contratto.getTariffa().equals("SETTIMANALE")){
				kmLimite = Fascia.LIM_KM_S * weeksPast;
				limiteKmLabel.setText(Integer.toString(kmLimite) + " km");
			}
		} else {
			limiteKmLabel.setText("ILLIMITATI");
		}
		
		int kmPercorsi = newKm - Integer.parseInt(auto.getChilometraggio());
		if(contratto.getTipoKm().equals("ILLIMITATO") || kmLimite >= kmPercorsi){
			inKm = true;
			kmPercorsiLabel.setText(Integer.toString(kmPercorsi) + " km");
		} else {
			kmPercorsiLabel.setText(Integer.toString(kmPercorsi) + " km *");
		}
		
	}
	
	private void impostaConto(){

		DecimalFormat df = PriceValidator.getDecimalFormat();
		
		if (contratto.getTipoKm().equals("LIMITATO")){
			
			if(inKm){
				contoLine2Text.setText("Costo km x km percorsi :");
			} else {
				contoLine2Text.setText("* Costo km x limite km :");
				contoLine3Text.setText("* Penale km x km in più :");
			}
			
			if(!inTempo){
				contoLine4Text.setText("* Teriffa Base x giorni in più :");
			}
			
			
			if (contratto.getTariffa().equals("GIORNALIERA")){
				
				double tot = 0.0;
				
				contoLine1Text.setText("Tariffa base giornaliera :");
				
				double costo = Double.parseDouble(prezzi.get("tariffa_base_g"));
				tot += costo;
				contoLine1Label.setText(df.format(costo) + " €");
				if(inKm){
					costo = Double.parseDouble(prezzi.get("costo_chilometrico")) 
							* ((double) newKm - Double.parseDouble(auto.getChilometraggio()));
					tot += costo;
					contoLine2Label.setText(df.format(costo) + " €");
				} else {
					costo = Double.parseDouble(prezzi.get("costo_chilometrico")) 
							* (double) (Fascia.LIM_KM_G * daysPast);
					
					tot += costo;
					contoLine2Label.setText(df.format(costo) + " €");
					
					costo = Double.parseDouble(prezzi.get("penale_chilometri")) 
							* ((double) newKm - (double) (Fascia.LIM_KM_G * daysPast));
					tot += costo;
					contoLine3Label.setText(df.format(costo) + " €");
				}
				
				if(!inTempo){
					costo = Double.parseDouble(prezzi.get("tariffa_base_g")) 
							* (double) giorniInPiu;
					tot += costo;
					contoLine4Label.setText(df.format(costo) + " €");
				}
				
				totLabel.setText(df.format(tot) + " €");
				this.tot = tot;
				
			} else if (contratto.getTariffa().equals("SETTIMANALE")){
				
				double tot = 0.0;
				
				contoLine1Text.setText("Tariffa base settimanale :");
				
				double costo = Double.parseDouble(prezzi.get("tariffa_base_s"));
				tot += costo;
				contoLine1Label.setText(df.format(costo) + " €");
				if(inKm){
					costo = Double.parseDouble(prezzi.get("costo_chilometrico")) 
							* ((double) newKm - Double.parseDouble(auto.getChilometraggio()));
					tot += costo;
					contoLine2Label.setText(df.format(costo) + " €");
				} else {
					costo = Double.parseDouble(prezzi.get("costo_chilometrico")) 
							* (double) (Fascia.LIM_KM_S * weeksPast);
					tot += costo;
					contoLine2Label.setText(df.format(costo) + " €");
					
					costo = Double.parseDouble(prezzi.get("penale_chilometri")) 
							* ((double) newKm - (double) (Fascia.LIM_KM_S * weeksPast));
					tot += costo;
					contoLine3Label.setText(df.format(costo) + " €");
				}
				
				if(!inTempo){
					costo = Double.parseDouble(prezzi.get("tariffa_base_s")) 
							* (double) settimaneInPiu;
					tot += costo;
					contoLine4Label.setText(df.format(costo) + " €");
				}
				
				totLabel.setText(df.format(tot) + " €");
				this.tot = tot;
				
			}
			
		} else if (contratto.getTipoKm().equals("ILLIMITATO")){
		
			
			if(!inTempo){
				contoLine3Text.setText("* Teriffa Base x giorni in più :");
			}
			
			if (contratto.getTariffa().equals("GIORNALIERA")){
				
				double tot = 0.0;
				
				contoLine1Text.setText("Tariffa base giornaliera :");
				contoLine2Text.setText("Costo giornaliero x giorni :");
				
				double costo = Double.parseDouble(prezzi.get("tariffa_base_g")) ;
				tot += costo;
				contoLine1Label.setText(df.format(costo) + " €");
				
				costo = Double.parseDouble(prezzi.get("tariffa_illim_g")) 
							* (double) daysPast;
				tot += costo;
				contoLine2Label.setText(df.format(costo) + " €");
				
				if(!inTempo){
					costo = Double.parseDouble(prezzi.get("tariffa_illim_g")) 
							* (double) giorniInPiu;
					tot += costo;
					contoLine3Label.setText(df.format(costo) + " €");
				}
				
				totLabel.setText(df.format(tot) + " €");
				this.tot = tot;

			} else if (contratto.getTariffa().equals("SETTIMANALE")){
				
				double tot = 0.0;
				
				contoLine1Text.setText("Tariffa base settimanale :");
				contoLine2Text.setText("Costo settimanale x settimane :");
				
				double costo = Double.parseDouble(prezzi.get("tariffa_base_s")) ;
				tot += costo;
				contoLine1Label.setText(df.format(costo) + " €");
				
				costo = Double.parseDouble(prezzi.get("tariffa_illim_s")) 
							* (double) weeksPast;
				tot += costo;
				contoLine2Label.setText(df.format(costo) + " €");
				
				if(!inTempo){
					costo = Double.parseDouble(prezzi.get("tariffa_illim_s")) 
							* (double) settimaneInPiu;
					tot += costo;
					contoLine3Label.setText(df.format(costo) + " €");
				}
				
				totLabel.setText(df.format(tot) + " €");
				this.tot = tot;

			}
			
		} 
		
	}
	
	@FXML
	private void handleChiudiContratto(){
		
		Optional<ButtonType> result = NotificationManager.setConfirm("Operazione irreversibile\nSicuro di voler continuare ?");
		
		if (result.get() == ButtonType.OK){
			queryChiudiContratto();
		}
		
	}
	
	private void queryChiudiContratto(){
		
		String[] comando = new String[]{"businessTier.GestioneContratti", "chiudiContratto"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id_contratto", Integer.toString(contratto.getId()));
		inputParam.put("tot", PriceValidator.getDecimalFormat().format(tot));
		inputParam.put("new_status_contratto", Contratto.STATUS_CHIUSO);
		inputParam.put("data_rientro", data_rientro.toString());
		inputParam.put("id_auto", Integer.toString(auto.getId()));
		inputParam.put("new_status_auto", Auto.STATUS_DISPONIBILE);
		inputParam.put("new_chilometraggio_auto", Integer.toString(newKm));
		inputParam.put("new_filiale", Integer.toString(contratto.getIdFilialeDiArrivo()));
		
		HashMap<String, String> risultato = new HashMap<>();
		
		risultato =	FrontController.request(comando, inputParam);

		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			InterStageEventsHandler.getInstance().getCaller().callBack();
			myController.closeWinStage();
		} else {
			NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
		}
		
	}
		
}
