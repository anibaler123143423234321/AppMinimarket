package com.dagnerchuman.minimarket.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.dagnerchuman.minimarket.entity.GenericResponse;
import com.dagnerchuman.minimarket.entity.service.Cliente;
import com.dagnerchuman.minimarket.repository.ClienteRepository;

import org.jetbrains.annotations.NotNull;

public class ClienteViewModel extends AndroidViewModel {
    private final ClienteRepository repository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c){
        return  repository.guardarCliente(c);
    }
}
