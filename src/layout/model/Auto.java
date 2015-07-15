package layout.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Auto {
		private StringProperty modello;
		private StringProperty nomeFiliale;
		private StringProperty status;
		private StringProperty chilometraggio;
		private StringProperty fasce;
		private StringProperty targa;
		
		public Auto(String Modello, String NomeFiliale, String Status,String Targa, String Chilometraggio, String Fasce){
			
			this.modello=new SimpleStringProperty(Modello);
			this.status=new SimpleStringProperty(Status);
			this.targa= new SimpleStringProperty(Targa);
			this.nomeFiliale = new SimpleStringProperty(NomeFiliale);
			this.chilometraggio = new SimpleStringProperty(Chilometraggio);
			this.fasce=new SimpleStringProperty(Fasce);
			
		}

		
		public String modello(){return modello.get();}
		public String getnomeFiliale(){return nomeFiliale.get();}
		public String getChilometraggio(){return chilometraggio.get();}
		public String getStatus(){return status.get();}
		public String getFasce(){return fasce.get();}
		public String getTarga(){return targa.get();}
		
		public StringProperty modelloProperty(){return modello;}
		public StringProperty nomeFilialeProperty(){return nomeFiliale;}
		public StringProperty chilometraggioProperty(){return chilometraggio;}
		public StringProperty statusProperty(){return status;}
		public StringProperty fasceProperty(){return fasce;}
		public StringProperty targaProperty(){return targa;}

	}


