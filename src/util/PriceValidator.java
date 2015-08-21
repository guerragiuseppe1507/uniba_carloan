package util;

public class PriceValidator {
	
	public static String validatePrice(String currency, String price){
		if (price.matches("[0-9]*||[0-9]{0,4}.[0-9]{0,2}")){
			 if (price.matches("[0-9]*")){
				 price = price + ".00";
			 }
			 if (price.startsWith(".")){
				 price = "0" + price;
			 }
			 if (price.endsWith(".")){
				 price += "00";
			 }
			 if (price.matches("[0-9]*.[0-9]")){
				 price += "0";
			 }
			 if (price.matches(" *")){
				 price = "0.00";
			 }
			 
			 price += " "+currency;
		 } else {
			 price = "Invalid Value";
		 }
		return price;
	}

}
