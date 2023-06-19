package com.dagnerchuman.minimarket.activity.ui.compras;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dagnerchuman.minimarket.R;
import com.dagnerchuman.minimarket.activity.adapter.MisComprasAdapter;
import com.dagnerchuman.minimarket.entity.service.Usuario;
import com.dagnerchuman.minimarket.utils.DateSerializer;
import com.dagnerchuman.minimarket.utils.TimeSerializer;
import com.dagnerchuman.minimarket.viewmodel.PedidoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;


public class MisComprasFragment extends Fragment {
    private PedidoViewModel pedidoViewModel;
    private RecyclerView rcvPedidos;
    private MisComprasAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mis_compras, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init(View v) {
        rcvPedidos = v.findViewById(R.id.rcvMisCompras);
    }

    private void initViewModel() {
        pedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
    }

    private void initAdapter() {
        adapter = new MisComprasAdapter(new ArrayList<>());
        rcvPedidos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvPedidos.setAdapter(adapter);
    }

    private void loadData() {
        Context context = requireContext();
        SharedPreferences sp = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            this.pedidoViewModel.listarPedidosPorCliente(u.getCliente().getId()).observe(getViewLifecycleOwner(), response -> {
                adapter.updateItems(response.getBody());
            });
        }
    }
}