package com.example.trabajoandroid;

import android.app.Application;
import android.os.StrictMode;

public class MyApplication extends Application {
    private juegoControlador controlador;
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(gfgPolicy);
        controlador = new juegoControlador();
    }
    public juegoControlador getControlador() {
        return controlador;
    }
}
