package sg.edu.nus.iss.ssa.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import sg.edu.nus.iss.ssa.constants.StoreConstants;

public class ReportValidator {

	public boolean isDateValid(String date) {
		if(date.isEmpty()){
			return true;
		}
		else if (!date.matches("\\d{4}-[01]\\d-[0-3]\\d"))
	    	return false;	        
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    df.setLenient(false);
	    try {
	        df.parse(date);
	        return true;
	    } catch (ParseException ex) {
	        return false;
	    }
	}
}
