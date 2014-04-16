package esarc.bts.ticketscan.model.dateevent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateEvent {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date                    date;

    public final Date getDate() {
        return this.date;
    }

    public final void setDate(final Date pDate) {
        this.date = pDate;
    }

    public static DateEvent stringToDate(final String json)
            throws ParseException {
        DateEvent dateEvent = new DateEvent();
        dateEvent.setDate(sdf.parse(json));

        return dateEvent;
    }

    @Override
    public final String toString() {
        return sdf.format(this.date);

    }

}
