package util;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CountryManager {
	
	private static final String[] countrySupported = {"Italia"};
	
	public static ObservableList<String> getCountryNames(){
		ObservableList<String> countries = FXCollections.observableArrayList();

	    for (String country : countrySupported) {
	      String name = country;
	      countries.add(name);   
	    }
	    
	    Collections.sort(countries);
	    
	    return countries;
	}

}
