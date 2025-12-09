package com.example.appcatalagodeproductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private Context context;
    private ArrayList<ItemPedido> listaItems;
    private OnCantidadChangeListener listener;

    public interface OnCantidadChangeListener {
        void onCantidadChanged();
    }

    public PedidoAdapter(Context context, ArrayList<ItemPedido> listaItems, OnCantidadChangeListener listener) {
        this.context = context;
        this.listaItems = listaItems;
        this.listener = listener;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        ItemPedido item = listaItems.get(position);
        Producto producto = item.getProducto();

        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText(String.format(Locale.getDefault(), "$%.2f c/u", producto.getPrecio()));
        holder.tvCantidad.setText(String.valueOf(item.getCantidad()));
        holder.tvSubtotal.setText(String.format(Locale.getDefault(), "Subtotal: $%.2f", item.getSubtotal()));
        holder.ivProducto.setImageResource(producto.getImagenResId());

        // Configurar listeners usando el ID del producto (más seguro)
        holder.btnAumentar.setOnClickListener(v -> {
            CarritoManager.getInstance().aumentarCantidad(producto.getId());
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                notifyItemChanged(pos);
            }
            if (listener != null) {
                listener.onCantidadChanged();
            }
        });

        holder.btnDisminuir.setOnClickListener(v -> {
            CarritoManager.getInstance().disminuirCantidad(producto.getId());
            if (CarritoManager.getInstance().estaVacio()) {
                notifyDataSetChanged();
            } else {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    notifyItemChanged(pos);
                }
            }
            if (listener != null) {
                listener.onCantidadChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public void actualizarLista(ArrayList<ItemPedido> nuevaLista) {
        this.listaItems = nuevaLista;
        notifyDataSetChanged();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProducto;
        TextView tvNombre, tvPrecio, tvCantidad, tvSubtotal;
        Button btnAumentar, btnDisminuir;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            ivProducto = itemView.findViewById(R.id.ivProductoPedido);
            tvNombre = itemView.findViewById(R.id.tvNombrePedido);
            tvPrecio = itemView.findViewById(R.id.tvPrecioPedido);
            tvCantidad = itemView.findViewById(R.id.tvCantidadPedido);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotalPedido);
            btnAumentar = itemView.findViewById(R.id.btnAumentar);
            btnDisminuir = itemView.findViewById(R.id.btnDisminuir);
        }
    }
}