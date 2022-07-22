package com.example.fichapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class editarFichas extends AppCompatActivity {

    Button button_buscar_ficha;
    EditText numero_ficha;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_fichas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void FichaEncontrada(View view){
        Intent intent = new Intent(this, fichaEncontrada.class);

        numero_ficha = findViewById(R.id.text_buscarficha);
        button_buscar_ficha = findViewById(R.id.button_buscarficha);

        button_buscar_ficha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num_ficha = numero_ficha.getText().toString().trim();
                if(num_ficha.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese numero de ficha", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference ficha = db.collection("Fichas").document(num_ficha);
                    ficha.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot ficha_encontrada= task.getResult();
                                if(ficha_encontrada.exists()){
                                    Log.d("[FICHA]", ficha_encontrada.getData().toString());
                                    Toast.makeText(getApplicationContext(), "Ficha encontrada", Toast.LENGTH_SHORT).show();
                                    intent.putExtra("numero_ficha", ficha_encontrada.get("numero_ficha").toString());
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), "No se encuentra la ficha", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Log.d("[FICHA]", "get failed with ", task.getException());
                            }
                        }
                    });

                }
            }
        });


    }

    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }
}