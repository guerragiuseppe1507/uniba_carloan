package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class CountryManager {
	
	public static ArrayList<String> getCountryNames(){
		 ArrayList<String> countries = new ArrayList<String>();

	    Locale[] locales = Locale.getAvailableLocales();
	    for (Locale locale : locales) {
	      String name = locale.getDisplayCountry();

	      if (!"".equals(name)) {
	        countries.add(name);
	      }
	    }
	    
	    Collections.sort(countries);
	    
	    return countries;
	}

}
