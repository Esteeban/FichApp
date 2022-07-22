package com.example.fichapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public void Registrar(View view){
        Intent intent = new Intent(this, Registrarse.class);
        startActivity(intent);
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
                    Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference user = db.collection("Usuarios").document(userLogin);
                    user.get().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            DocumentSnapshot usuario = task.getResult();
                            if(usuario.exists()){
                                usuario_login = usuario.get("contrasena").toString();
                                if(passwordLogin.equals(usuario_login)){
                                    Toast.makeText(getApplicationContext(), "Ingreso correcto", Toast.LENGTH_SHORT).show();
                                    intent.putExtra("user_name", userLogin);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d(TAG, "No such document");
                                Toast.makeText(getApplicationContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
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