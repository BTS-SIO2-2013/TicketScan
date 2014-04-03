package esarc.bts.ticketscan.model.dateevent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEvent extends Date {

	public static DateEvent stringToDate(String json) throws ParseException {
		DateEvent dateEvent = new DateEvent();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dateEvent = (DateEvent) sdf.parse(json);
		return dateEvent;
	}
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(this);
		
	}
	

}
