package com.example.trabajoandroid.vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trabajoandroid.R;
import Bussiness.modelo.Circuito;

import java.util.ArrayList;

public class CircuitoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Circuito> circuitos;

    public CircuitoAdapter(Context context, ArrayList<Circuito> circuitos) {
        this.context = context;
        this.circuitos = circuitos;
    }

    @Override
    public int getCount() {
        return circuitos.size();
    }

    @Override
    public Object getItem(int position) {
        return circuitos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar el layout si es necesario
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_circuito, parent, false);
        }

        // Obtener las vistas
        TextView textViewNombre = convertView.findViewById(R.id.textViewCircuito);
        TextView textViewDificultad = convertView.findViewById(R.id.textViewDificultad);

        // Asignar los valores
        Circuito circuito = circuitos.get(position);
        textViewNombre.setText(circuito.getNombre());
        textViewDificultad.setText(circuito.getDificultad());

        return convertView;
    }
}