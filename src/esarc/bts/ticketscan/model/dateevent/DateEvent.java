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

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (date == null) {
            result += 0;
        } else {
            result += date.hashCode();
        }
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DateEvent other = (DateEvent) obj;
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }
        return true;
    }

}
