package esarc.bts.ticketscan.model.dateevent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEvent {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Date date;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public static DateEvent stringToDate(String json) throws ParseException {
		DateEvent dateEvent = new DateEvent();
		dateEvent.setDate(sdf.parse(json));
		
		return dateEvent;
	}
	
	public String toString() {
		return sdf.format(this.date);
		
	}
	

}
