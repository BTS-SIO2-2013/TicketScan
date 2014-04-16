package esarc.bts.ticketscan.model.salle;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import esarc.bts.ticketscan.R;

public class SalleAdapter extends ArrayAdapter<Salle> {

    public SalleAdapter(final Context context, final List<Salle> salle) {
        super(context, R.layout.salleview, salle);
    }

    @Override
    public final View getView(final int position, final View convertView,
            final ViewGroup parent) {
        View ligne = convertView;

        if (ligne == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ligne = inflater.inflate(R.layout.salleview, parent, false);
        }

        TextView nom = (TextView) ligne.findViewById(R.id.textNom);

        Salle salle = getItem(position);

        if (nom != null && salle != null) {
            nom.setText(salle.getNom());
        }

        return ligne;
    }

}
