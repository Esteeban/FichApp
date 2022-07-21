package com.example.fichapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearFicha extends AppCompatActivity {

    Button button_add;
    EditText num, name, surname, email, address, phone, date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ficha);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }

    private void createFicha(String num, String name, String surname, String email, String address,String phone, String date){
        Map<String, Object> ficha = new HashMap<>();
        ficha.put("numero_ficha",num);
        ficha.put("nombre_paciente",name);
        ficha.put("apellido_paciente",surname);
        ficha.put("correo",email);
        ficha.put("direccion",address);
        ficha.put("telefono",phone);
        ficha.put("fecha",date);
        ficha.put("estado", "Pendiente");

        db.collection("Fichas").document(num).set(ficha).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Ficha agregada con éxito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al crear una Ficha",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void crear_ficha(View view){
        Intent intent = new Intent(this, menu.class);

        num = findViewById(R.id.num_ficha);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);

        button_add = findViewById(R.id.btn_addFicha);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ficha_num = num.getText().toString().trim();
                String ficha_name = name.getText().toString().trim();
                String ficha_surname = surname.getText().toString().trim();
                String ficha_email = email.getText().toString().trim();
                String ficha_address = address.getText().toString().trim();
                String ficha_phone = phone.getText().toString().trim();
                String ficha_date = date.getText().toString().trim();

                if( ficha_num.isEmpty() || ficha_name.isEmpty() || ficha_surname.isEmpty() || ficha_email.isEmpty() || ficha_address.isEmpty() || ficha_phone.isEmpty() || ficha_date.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    createFicha(ficha_num,ficha_name,ficha_surname,ficha_email,ficha_address,ficha_phone,ficha_date);
                    startActivity(intent);
                }
            }
        });
    }



}