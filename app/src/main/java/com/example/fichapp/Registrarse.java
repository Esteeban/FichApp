package com.example.fichapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {

    Button button_registrarse;
    EditText name, surname, user_name, email, password, password_confirm;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
    }

    private void Register(String name,String surname,String user_name,String email,String password){
        Map<String, Object> user_register = new HashMap<>();
        user_register.put("nombre", name);
        user_register.put("apellido", surname);
        user_register.put("usuario", user_name);
        user_register.put("correo", email);
        user_register.put("contrasena", password);


        db.collection("Usuarios").document(user_name).set(user_register).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error de Registro",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registrarse(View view){
        Intent intent = new Intent(this, MainActivity.class);

        name = findViewById(R.id.reg_name);
        surname = findViewById(R.id.reg_surname);
        email = findViewById(R.id.reg_email);
        user_name = findViewById(R.id.reg_user_name);
        password = findViewById(R.id.reg_password);
        password_confirm = findViewById(R.id.reg_confirm_pssword);
        button_registrarse = findViewById(R.id.btn_registrarse);

        button_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_register = name.getText().toString().trim();
                String surname_register = surname.getText().toString().trim();
                String user_name_register = user_name.getText().toString().trim();
                String email_register = email.getText().toString().trim();
                String password_register = password.getText().toString().trim();
                String password_confirm_register = password_confirm.getText().toString().trim();

                if(name_register.isEmpty() || surname_register.isEmpty() || user_name_register.isEmpty() || email_register.isEmpty() || password_register.isEmpty() || password_confirm_register.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference userexist = db.collection("Usuarios").document(user_name_register);
                    userexist.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot user_register = task.getResult();
                                if (user_register.exists()) {
                                    Toast.makeText(getApplicationContext(), "El usuario ya esta registrado", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (password_register.equals(password_confirm_register)) {
                                        Register(name_register, surname_register, user_name_register, email_register, password_register);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Las contraseñas deben ser identicas", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}