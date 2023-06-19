package com.dagnerchuman.minimarket.activity.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dagnerchuman.minimarket.R;
//import com.dagnerchuman.minimarket.DetalleProductoActivity;
import com.dagnerchuman.minimarket.api.ConfigApi;
//import com.dagnerchuman.minimarket.communication.Communication;
//import com.dagnerchuman.minimarket.communication.MostrarBadgeCommunication;
import com.dagnerchuman.minimarket.entity.service.DetallePedido;
import com.dagnerchuman.minimarket.entity.service.Producto;
import com.dagnerchuman.minimarket.utils.Carrito;
import com.dagnerchuman.minimarket.utils.DateSerializer;
import com.dagnerchuman.minimarket.utils.TimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductosRecomendadosAdapter extends RecyclerView.Adapter<ProductosRecomendadosAdapter.ViewHolder> {
    private List<Producto> productos;


    public ProductosRecomendadosAdapter(List<Producto> productos) {
        this.productos = productos;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.productos.get(position));
    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }

    public void updateItems(List<Producto> producto) {
        this.productos.clear();
        this.productos.addAll(producto);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Producto p) {
            ImageView imgPlatillo = itemView.findViewById(R.id.imgProducto);
            TextView namePlatillo = itemView.findViewById(R.id.nameProducto);
            Button btnOrdenar = itemView.findViewById(R.id.btnOrdenar);

            String url = ConfigApi.baseUrlE + "/api/documento-almacenado/download/" + p.getFoto().getFileName();

            Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    //.networkPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .error(R.drawable.image_not_found)
                    .into(imgPlatillo);
            namePlatillo.setText(p.getNombre());
            btnOrdenar.setOnClickListener(v -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setProducto(p);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(p.getPrecio());
                //mostrarBadgeCommunication.add(detallePedido);
                //successMessage(Carrito.agregarPlatillos(detallePedido));
            });


        }
    }
}
