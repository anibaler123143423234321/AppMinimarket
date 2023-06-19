package com.dagnerchuman.minimarket.api;

import com.dagnerchuman.minimarket.entity.service.DocumentoAlmacenado;
import com.dagnerchuman.minimarket.utils.DateSerializer;
import com.dagnerchuman.minimarket.utils.TimeSerializer;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    public static final String baseUrlE = "http://10.0.2.2:9090";
    public static final String ipDagner = "http://192.168.101.16:9090"; //IP DEL ORDENADOR.
    private static Retrofit retrofit;
    private static String token = "";

    private static UsuarioApi usuarioApi;
    private static ClienteApi clienteApi;
    private static DocumentoAlmacenadoApi documentoAlmacenadoApi;
    private static CategoriaApi categoriaApi;
    private static ProductoApi productoApi;
    private static PedidoApi pedidoApi;

    static {
        initClient();
    }

    private static void initClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlE)//Si quieren ejecutar la app desde su móvil, cambiar aquí con la ip de su ordenador
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    public static OkHttpClient getClient() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.level(HttpLoggingInterceptor.Level.BODY);

        StethoInterceptor stetho = new StethoInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(loggin)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);
        return builder.build();
    }

    public static void setToken(String value) {
        token = value;
        initClient();
    }

    public static UsuarioApi getUsuarioApi() {
        if (usuarioApi == null) {
            usuarioApi = retrofit.create(UsuarioApi.class);
        }
        return usuarioApi;
    }

    public static ClienteApi getClienteApi() {
        if (clienteApi == null) {
            clienteApi = retrofit.create(ClienteApi.class);
        }
        return clienteApi;
    }

    public static DocumentoAlmacenadoApi getDocumentoAlmacenadoApi() {
        if (documentoAlmacenadoApi == null) {
            documentoAlmacenadoApi = retrofit.create(DocumentoAlmacenadoApi.class);
        }
        return documentoAlmacenadoApi;
    }

    public static CategoriaApi getCategoriaApi() {
        if (categoriaApi == null) {
            categoriaApi = retrofit.create(CategoriaApi.class);
        }
        return categoriaApi;
    }

    public static ProductoApi getProductoApi() {
        if (productoApi == null) {
            productoApi = retrofit.create(ProductoApi.class);
        }
        return productoApi;
    }
    public static PedidoApi getPedidoApi(){
        if(pedidoApi == null){
            pedidoApi = retrofit.create(PedidoApi.class);
        }
        return pedidoApi;
    }

}
