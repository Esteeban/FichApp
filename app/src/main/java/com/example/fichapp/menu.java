package com.example.fichapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void CrearFicha(View view){
        Intent intent = new Intent(this, CrearFicha.class);
        startActivity(intent);
    }
    public void VerFichas(View view){
        Intent intent = new Intent(this, verFichas.class);
        startActivity(intent);
    }
    public void EditarFichas(View view){
        Intent intent = new Intent(this, editarFichas.class);
        startActivity(intent);
    }
    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_overflow,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.id_ajustes){
            Toast.makeText(this,"Configuración de cuenta de usuario",Toast.LENGTH_SHORT).show();
            cuenta(null);
        }else if(id == R.id.id_cerrarsesion){
            Toast.makeText(this,"Sesión cerrada",Toast.LENGTH_SHORT).show();
            cerrar(null);
        }
        return super.onOptionsItemSelected(item);
    }
    public void cerrar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void cuenta(View view){
        Intent intent = new Intent(this, configurarCuenta.class);
        startActivity(intent);
    }
}