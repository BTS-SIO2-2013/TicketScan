package esarc.bts.ticketscan.model.ticket;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import esarc.bts.ticketscan.R;

public class TicketAdapter extends ArrayAdapter<Ticket> {

    public TicketAdapter(final Context context, final List<Ticket> ticket) {
        super(context, R.layout.ticketview, ticket);

    }

    @Override
    public final View getView(final int position, final View convertView,
            final ViewGroup parent) {
        View ligne = convertView;

        if (ligne == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ligne = inflater.inflate(R.layout.ticketview, parent, false);
        }

        TextView libele = (TextView) ligne.findViewById(R.id.texteNom);
        TextView code = (TextView) ligne.findViewById(R.id.textBillet);

        Ticket ticket = getItem(position);

        if (libele != null && ticket != null) {
            libele.setText(ticket.getClient().getNom() + " "
                    + ticket.getClient().getPrenom());
        }
        if (code != null && ticket != null) {
            code.setText(String.valueOf(ticket.getCode()));
        }
        if (ticket != null && ticket.getValide()) {
            ligne.setBackgroundColor(android.graphics.Color.BLUE);
        } else {
            ligne.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        }

        return ligne;
    }

}
