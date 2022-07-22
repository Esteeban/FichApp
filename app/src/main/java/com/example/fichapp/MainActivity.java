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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DocSnippets";
    public String usuario_login = "";
    Button button_add;
    EditText user_name, password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * @param user_login
     * @param password_login
     * @return
     */
    private boolean login(String user_login, String password_login){
        getUser(user_login);

        if(password_login.equals(usuario_login)){
            Toast.makeText(getApplicationContext(), "Login correcto", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Ingrese contrasena correcta", Toast.LENGTH_SHORT).show();
            Log.d("password", usuario_login);
            return false;
        }
    }

    /**
     *
     * @param view
     */
    public void Ingresar(View view){
        Intent intent = new Intent(this, menu.class);
        user_name = findViewById(R.id.user);
        password = findViewById(R.id.password);
        button_add = findViewById(R.id.button);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLogin = user_name.getText().toString().trim();
                String passwordLogin = password.getText().toString().trim();

                if(userLogin.isEmpty() || passwordLogin.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese los datos", Toast.LENGTH_SHORT).show();
                }else{
                    if(login(userLogin, passwordLogin)){
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Error de contraseña", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    /**
     * getUser() función para obtener desde la base de datos un usuario en específico
     * @param user_name nombre de usuario ingresado en el login
     */
    public void getUser(String user_name) {
        DocumentReference user = db.collection("Usuarios").document(user_name);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot usuario = task.getResult();
                    if(usuario.exists()){
                        Log.d(TAG, "DocumentSnapshot data: " + usuario.getData());
                        usuario_login = usuario.get("contrasena").toString();
                        Log.d("password login", usuario_login);
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(getApplicationContext(), "No está registrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    //Deshabilitar botón back de android
    @Override
    public void onBackPressed(){

    }
}