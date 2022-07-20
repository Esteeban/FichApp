package com.example.fichapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class editarFichas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_fichas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void FichaEncontrada(View view){
        Intent intent = new Intent(this, fichaEncontrada.class);
        startActivity(intent);
    }
    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }
}