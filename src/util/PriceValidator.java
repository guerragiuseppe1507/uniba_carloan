package util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class PriceValidator {
	
	public static final String PRICE_ERR = "Valore non valido";
	
	public static String validatePrice(String currency, String price){
		if (price.matches(" *") || 
				price == null){
			price = "0.00";
			price += " "+currency;
		}else if (price.matches("[0-9]{0,3}[.][0-9]{0,2}$")){
			if (price.startsWith(".")){
				price = "0" + price;
			}
			if (price.endsWith(".")){
				price += "00";
			}
			if (price.matches("[0-9]{1,3}.[0-9]")){
				price += "0";
			}
			price += " "+currency;
		}else if (price.matches("[0-9]{0,3}$")){
			price = price + ".00";
			price += " "+currency;
		}else {
			 price = PriceValidator.PRICE_ERR;
		}
		return price;
	}
	
	public static DecimalFormat getDecimalFormat(){
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ITALIAN);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('\0'); 
		DecimalFormat df = new DecimalFormat("#.00", otherSymbols); 
		df.setGroupingUsed(false);
		return df;
		
	}

}
