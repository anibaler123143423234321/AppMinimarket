package com.dagnerchuman.minimarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dagnerchuman.minimarket.entity.GenericResponse;
import com.dagnerchuman.minimarket.entity.service.Producto;
import com.dagnerchuman.minimarket.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {
    private final ProductoRepository repository;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        repository = ProductoRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Producto>>> listarProductosRecomendados(){
        return this.repository.listarProductosRecomendados();
    }
    public LiveData<GenericResponse<List<Producto>>> listarProductosPorCategoria(int idC){
        return this.repository.listarProductosPorCategoria(idC);
    }
}
