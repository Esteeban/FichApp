package com.example.fichapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class fichaEncontrada extends AppCompatActivity {
    Button btncamara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_encontrada);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btncamara = findViewById(R.id.btn_addFicha);

        btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
    }

    private void abrirCamara (){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,1);
        }
    }
    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }

}