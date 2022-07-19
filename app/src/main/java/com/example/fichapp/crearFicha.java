package com.example.fichapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class crearFicha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ficha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }

}