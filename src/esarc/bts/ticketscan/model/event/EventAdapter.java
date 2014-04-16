package esarc.bts.ticketscan.model.event;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import esarc.bts.ticketscan.R;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(final Context context, final List<Event> event) {
        super(context, R.layout.eventview, event);
    }

    @Override
    public final View getView(final int position, final View convertView,
            final ViewGroup parent) {
        View ligne = convertView;

        if (ligne == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ligne = inflater.inflate(R.layout.eventview, parent, false);
        }

        TextView libelle = (TextView) ligne.findViewById(R.id.textNom);
        TextView dateEvent = (TextView) ligne.findViewById(R.id.textDate);

        Event event = getItem(position);

        if (libelle != null && event != null) {
            libelle.setText(event.getLibelle());
        }
        if (dateEvent != null && event != null) {
            dateEvent.setText(event.getDateEvent().toString());
        }

        return ligne;
    }
}
