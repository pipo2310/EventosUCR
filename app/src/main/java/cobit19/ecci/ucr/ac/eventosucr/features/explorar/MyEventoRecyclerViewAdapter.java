package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.EventoFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Evento} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventoRecyclerViewAdapter extends RecyclerView.Adapter<MyEventoRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFecha;
        public final TextView mNombre;
        public final TextView mInstitucion;
        public Evento mEvento;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFecha = (TextView) view.findViewById(R.id.explorar_evento_carta_fecha);
            mNombre = (TextView) view.findViewById(R.id.explorar_evento_carta_nombre);
            mInstitucion = (TextView) view.findViewById(R.id.explorar_evento_carta_institucion);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombre.getText() + "'";
        }
    }

    private final List<Evento> mEventos;
    private final OnListFragmentInteractionListener mListener;

    public MyEventoRecyclerViewAdapter(List<Evento> eventos, OnListFragmentInteractionListener listener) {
        mEventos = eventos;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_evento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mEvento = mEventos.get(position);
        holder.mFecha.setText(UtilDates.obtenerFechaParaExplorarEventoCarta(mEventos.get(position).getFecha()));
        holder.mNombre.setText(mEventos.get(position).getNombre());
        holder.mInstitucion.setText(mEventos.get(position).getInstitucion());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mEvento);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventos.size();
    }
}
