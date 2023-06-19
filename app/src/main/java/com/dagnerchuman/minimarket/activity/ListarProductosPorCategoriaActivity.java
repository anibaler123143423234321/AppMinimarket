package com.dagnerchuman.minimarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import com.dagnerchuman.minimarket.R;

import com.dagnerchuman.minimarket.activity.adapter.ProductosPorCategoriaAdapter;

import com.dagnerchuman.minimarket.entity.service.Producto;

import com.dagnerchuman.minimarket.viewmodel.ProductoViewModel;


import java.util.ArrayList;
import java.util.List;

public class ListarProductosPorCategoriaActivity extends AppCompatActivity {
    private ProductoViewModel productoViewModel;
    private ProductosPorCategoriaAdapter adapter;
    private List<Producto> productos = new ArrayList<>();
    private RecyclerView rcvProductoPorCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos_por_categoria);
        init();
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.productoViewModel = vmp.get(ProductoViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductosPorCategoriaAdapter(productos);
        rcvProductoPorCategoria = findViewById(R.id.rcvProductosPorCategoria);
        rcvProductoPorCategoria.setAdapter(adapter);
        rcvProductoPorCategoria.setLayoutManager(new LinearLayoutManager(this));
        //rcvProductoPorCategoria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadData() {
        int idC = getIntent().getIntExtra("idC", 0);//Recibimos el idCategoria
        productoViewModel.listarProductosPorCategoria(idC).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });
    }
/*
    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarPlatillos(dp));
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this);
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, findViewById(R.id.toolbar), R.id.bolsaCompras);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }
    */
}