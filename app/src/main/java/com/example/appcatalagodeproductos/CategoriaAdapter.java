package com.example.appcatalagodeproductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private Context context;
    private ArrayList<Categoria> listaCategorias;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }

    public CategoriaAdapter(Context context, ArrayList<Categoria> listaCategorias, OnCategoriaClickListener listener) {
        this.context = context;
        this.listaCategorias = listaCategorias;
        this.listener = listener;
    }

    @Override
    public CategoriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriaViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);

        holder.tvNombre.setText(categoria.getNombre());
        holder.tvDescripcion.setText(categoria.getDescripcion());
        holder.tvCantidad.setText(categoria.getCantidadProductos() +
                (categoria.getCantidadProductos() == 1 ? " producto" : " productos"));
        holder.ivCategoria.setImageResource(categoria.getImagenResId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCategoriaClick(categoria);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoria;
        TextView tvNombre, tvDescripcion, tvCantidad;

        public CategoriaViewHolder(View itemView) {
            super(itemView);
            ivCategoria = itemView.findViewById(R.id.ivCategoria);
            tvNombre = itemView.findViewById(R.id.tvNombreCategoria);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionCategoria);
            tvCantidad = itemView.findViewById(R.id.tvCantidadProductos);
        }
    }
}