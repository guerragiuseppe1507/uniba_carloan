package util;

public class PriceValidator {
	
	public static final String PRICE_ERR = "Valore non valido";
	
	public static String validatePrice(String currency, String price){
		if (price.matches(" *") || price == null){
			price = "0.00";
			price += " "+currency;
		}else if (price.matches("[0-9]{0,4}[.][0-9]{0,2}$")){
			if (price.startsWith(".")){
				price = "0" + price;
			}
			if (price.endsWith(".")){
				price += "00";
			}
			if (price.matches("[0-9]{1,4}.[0-9]")){
				price += "0";
			}
			price += " "+currency;
		}else if (price.matches("[0-9]{0,4}$")){
			price = price + ".00";
			price += " "+currency;
		}else {
			 price = PriceValidator.PRICE_ERR;
		}
		return price;
	}

}
