package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberPlateValidator {

	public static boolean validate(String s, String region){
		Matcher m = Pattern.compile(loadMatcher(region)).matcher(s);
		if (m.find()) {
		    return true;
		} else {
		    return false;
		}
	}
	
	public static String getPlateExample(String region){
		String example;
		if(region.equals("Italia")){example = "AAA-BB-1111";}
		else{example = "";}//TODO
		return example;
	}
	
	private static String loadMatcher(String region){
		String regex;
		
		if(region.equals("Italia")){regex = "^[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}$";}
		else{regex = "";}//TODO
		
		return regex;
	}
}
