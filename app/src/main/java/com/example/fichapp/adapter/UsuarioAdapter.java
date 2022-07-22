package com.example.fichapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichapp.R;
import com.example.fichapp.model.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UsuarioAdapter extends FirestoreRecyclerAdapter<Usuario, UsuarioAdapter.ViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsuarioAdapter(@NonNull FirestoreRecyclerOptions<Usuario> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Usuario model) {
        holder.numero_ficha.setText(model.getNumero_ficha());
        holder.direccion.setText(model.getDireccion());
        holder.estado.setText(model.getEstado());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseno_ficha, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numero_ficha, direccion, estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numero_ficha = itemView.findViewById(R.id.tvNFicha);
            direccion = itemView.findViewById(R.id.tvDireccion);
            estado = itemView.findViewById(R.id.tvEstado);
        }
    }
}

