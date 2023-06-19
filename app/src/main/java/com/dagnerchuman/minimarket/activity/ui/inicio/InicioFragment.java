package com.dagnerchuman.minimarket.activity.ui.inicio;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dagnerchuman.minimarket.R;
import com.dagnerchuman.minimarket.activity.adapter.CategoriaAdapter;
import com.dagnerchuman.minimarket.activity.adapter.ImageAdapter;
import com.dagnerchuman.minimarket.activity.adapter.ProductosRecomendadosAdapter;
import com.dagnerchuman.minimarket.entity.ImageItem;
import com.dagnerchuman.minimarket.entity.service.Producto;
import com.dagnerchuman.minimarket.viewmodel.CategoriaViewModel;
import com.dagnerchuman.minimarket.viewmodel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {
    private ViewPager2 viewPager;
    private ImageAdapter imageAdapter;

    private GridView gvCategorias;
    private CategoriaViewModel categoriaViewModel;
    private CategoriaAdapter categoriaAdapter;

    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductosRecomendados;
    private ProductosRecomendadosAdapter adapter;
    private List<Producto> productos = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View view) {

        viewPager = view.findViewById(R.id.viewPager);
        ViewModelProvider vmp = new ViewModelProvider(this);
        //Categorías
        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCategorias = view.findViewById(R.id.gvCategorias);
        //Productos
        rcvProductosRecomendados = view.findViewById(R.id.rcvPlatillosRecomendados);
        rcvProductosRecomendados.setLayoutManager(new GridLayoutManager(getContext(), 1));
        productoViewModel = vmp.get(ProductoViewModel.class);
    }

    private void initAdapter() {
        //Carrusel de Imágenes
        imageAdapter = new ImageAdapter(getContext());
        viewPager.setAdapter(imageAdapter);

// Configuración de la auto-ciclo de las páginas
        viewPager.setOffscreenPageLimit(3); // Cantidad de páginas a mantener en memoria
        viewPager.setCurrentItem(0); // Página inicial

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int itemCount = viewPager.getAdapter().getItemCount();
                if (currentItem < itemCount - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
                handler.postDelayed(this, 4000); // Tiempo de desplazamiento automático en milisegundos
            }
        };
        handler.postDelayed(runnable, 4000); // Tiempo de desplazamiento automático
        //Categorías
        categoriaAdapter = new CategoriaAdapter(getContext(), R.layout.item_categorias, new ArrayList<>());
        gvCategorias.setAdapter(categoriaAdapter);
        //Productos
        adapter = new ProductosRecomendadosAdapter(productos);
        rcvProductosRecomendados.setAdapter(adapter);
    }

    private void loadData() {
        List<ImageItem> itemList = new ArrayList<>();
        itemList.add(new ImageItem(R.drawable.image1, "El mejor Producto"));
        itemList.add(new ImageItem(R.drawable.image2, "El mejor Producto"));
        itemList.add(new ImageItem(R.drawable.image3, "El mejor Producto"));
        imageAdapter.updateItem(itemList);
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            if(response.getRpta() == 1){
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else{
                System.out.println("Error al obtener las categorías activas");
            }
        });
        productoViewModel.listarProductosRecomendados().observe(getViewLifecycleOwner(), response -> {
            adapter.updateItems(response.getBody());
        });
    }

}