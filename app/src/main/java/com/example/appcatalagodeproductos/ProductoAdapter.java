package com.example.appcatalagodeproductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private Context context;
    private ArrayList<Producto> listaProductos;
    private OnProductoClickListener listener;

    public interface OnProductoClickListener {
        void onProductoClick(Producto producto);
    }

    public ProductoAdapter(Context context, ArrayList<Producto> listaProductos, OnProductoClickListener listener) {
        this.context = context;
        this.listaProductos = listaProductos;
        this.listener = listener;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvCategoria.setText(producto.getCategoria());
        holder.tvPrecio.setText(String.format(Locale.getDefault(), "$%.2f", producto.getPrecio()));
        holder.ivProducto.setImageResource(producto.getImagenResId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onProductoClick(producto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void actualizarLista(ArrayList<Producto> nuevaLista) {
        this.listaProductos = nuevaLista;
        notifyDataSetChanged();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProducto;
        TextView tvNombre, tvCategoria, tvPrecio;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            ivProducto = itemView.findViewById(R.id.ivProducto);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
        }
    }
}