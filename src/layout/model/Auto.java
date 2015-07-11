package layout.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Auto {
		private int idAuto;
		private int idModello;
		private StringProperty nomeFiliale;
		private StringProperty Status;
		private StringProperty Chilometraggio;
		private StringProperty Fasce;
		private StringProperty Targa;
		
		public Auto(int idAuto, int idModello, String nomeFiliale, String Status,String Targa, String Chilometraggio, String Fasce){
			
			this.idAuto=idAuto;
			this.idModello = idModello;
			this.Status=new SimpleStringProperty(Status);
			this.Targa= new SimpleStringProperty(Targa);
			this.nomeFiliale = new SimpleStringProperty(nomeFiliale);
			this.Chilometraggio = new SimpleStringProperty(Chilometraggio);
			this.Fasce=new SimpleStringProperty(Fasce);
			
		}

		public int getIdAuto(){return idAuto;}
		public int getidModello(){return idModello;}
		
		public String getnomeFiliale(){return nomeFiliale.get();}
		public String getChilometraggio(){return Chilometraggio.get();}
		public String getStatus(){return Status.get();}
		public String getFasce(){return Fasce.get();}
		public String getTarga(){return Targa.get();}
		
		public StringProperty nomeFilialeProperty(){return nomeFiliale;}
		public StringProperty chilometraggioProperty(){return Chilometraggio;}
		public StringProperty statusProperty(){return Status;}
		public StringProperty fasceProperty(){return Fasce;}
		public StringProperty targaProperty(){return Targa;}

	}


