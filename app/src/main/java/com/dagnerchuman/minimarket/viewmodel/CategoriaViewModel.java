package com.dagnerchuman.minimarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dagnerchuman.minimarket.entity.GenericResponse;
import com.dagnerchuman.minimarket.entity.service.Categoria;
import com.dagnerchuman.minimarket.repository.CategoriaRepository;

import java.util.List;

public class CategoriaViewModel extends AndroidViewModel {
    private final CategoriaRepository repository;


    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        this.repository = CategoriaRepository.getInstance();
    }

    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas(){
        return this.repository.listarCategoriasActivas();
    }
}
