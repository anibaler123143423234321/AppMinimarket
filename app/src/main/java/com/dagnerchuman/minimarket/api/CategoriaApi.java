package com.dagnerchuman.minimarket.api;

import com.dagnerchuman.minimarket.entity.GenericResponse;
import com.dagnerchuman.minimarket.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
