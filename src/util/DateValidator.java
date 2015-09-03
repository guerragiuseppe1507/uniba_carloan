package util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateValidator {

	public static boolean isBornDateValid(LocalDate d){
		
		boolean isValid = false;
		if (d.getDayOfWeek() == DayOfWeek.SUNDAY 
        		|| LocalDate.now().minusYears(18).isBefore(d)
        		|| LocalDate.now().minusYears(100).isAfter(d)) {
			isValid = true;
		}
		return isValid;
		
	}
	
	public static boolean isContractLimitValid(LocalDate d){
		boolean isValid = false;
		if (d.getDayOfWeek() == DayOfWeek.SUNDAY 
        		|| LocalDate.now().plusDays(1).isAfter(d)) {
			isValid = true;
		}
		return isValid;
	}
	
}
