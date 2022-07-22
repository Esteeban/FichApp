package com.example.fichapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class configurarCuenta extends AppCompatActivity {

    EditText name, surname, name_user, email, password;
    Button btnguardar;
    String user_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_name = getIntent().getStringExtra("user_name");
        btnguardar = findViewById(R.id.btn_edituser);

        name = findViewById(R.id.conf_name);
        surname = findViewById(R.id.conf_surname);
        name_user = findViewById(R.id.conf_user_name);
        email = findViewById(R.id.conf_email);
        password = findViewById(R.id.conf_password);

        DocumentReference user = db.collection("Usuarios").document(user_name);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot user_editable= task.getResult();
                    if(user_editable.exists()){
                        name.setText(user_editable.get("nombre").toString(), TextView.BufferType.EDITABLE);
                        surname.setText(user_editable.get("apellido").toString(), TextView.BufferType.EDITABLE);
                        name_user.setText(user_editable.get("usuario").toString(), TextView.BufferType.EDITABLE);
                        email.setText(user_editable.get("correo").toString(), TextView.BufferType.EDITABLE);
                        password.setText(user_editable.get("contrasena").toString(), TextView.BufferType.EDITABLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "No se encuentra el usuario", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void subirCambios(View view){
        Intent intent = new Intent(this, menu.class);

        btnguardar.setOnClickListener(new View.OnClickListener() {

            String edit_user_name = name.getText().toString().trim();
            String edit_user_surname = surname.getText().toString().trim();
            String edit_user_username = name_user.getText().toString().trim();
            String edit_user_email = email.getText().toString().trim();
            String edit_user_password = password.getText().toString().trim();

            @Override
            public void onClick(View view) {
                if( edit_user_name.isEmpty() || edit_user_surname.isEmpty() || edit_user_username.isEmpty() || edit_user_email.isEmpty() || edit_user_password.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    edit_user(edit_user_name,edit_user_surname,edit_user_username,edit_user_email,edit_user_password);
                    startActivity(intent);
                }
            }
        });
    }

    public void edit_user(String name,String surname, String name_user, String email, String password){
        Map<String, Object> user = new HashMap<>();
        user.put("nombre",name);
        user.put("apellido",surname);
        user.put("usuario",name_user);
        user.put("correo",email);
        user.put("contrasena",password);


        db.collection("Usuarios").document(name_user).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Usuario editado con éxito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al editar un Usuario",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }

}