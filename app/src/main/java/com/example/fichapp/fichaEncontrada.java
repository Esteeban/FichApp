package com.example.fichapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fichaEncontrada extends AppCompatActivity {
    Button btncamara, btneditar, btneliminar;
    TextView numficha;
    EditText edit_name, edit_surname, edit_email, edit_address, edit_phone;
    Spinner estado;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_encontrada);

        btncamara = findViewById(R.id.btn_edituser);
        btneditar = findViewById(R.id.button_editar);
        btneliminar = findViewById(R.id.button_eliminar);

        numficha = findViewById(R.id.numfichatitulo);
        edit_name = findViewById(R.id.nombre_f_e);
        edit_surname = findViewById(R.id.apellido_f_e);
        edit_email = findViewById(R.id.conf_email);
        edit_address = findViewById(R.id.direccion_f_e);
        edit_phone = findViewById(R.id.contacto_f_e);
        estado = findViewById(R.id.spinner_estado);

        //Array para el spinner de definir el estado de la ficha
        ArrayList<String> listaEstado = new ArrayList<>();
        listaEstado.add("Pendiente");
        listaEstado.add("En camino");
        listaEstado.add("Finalizada");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaEstado);
        estado.setAdapter(adapter);


        //se obtiene un dato de la vista editarFichas donde llega el numero de ficha
        String valor = getIntent().getStringExtra("numero_ficha");
        Log.d("numficha",valor);
        numficha.setText(valor);

        //setText en los campos editables
        DocumentReference ficha = db.collection("Fichas").document(valor);
        ficha.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ficha_editable= task.getResult();
                    if(ficha_editable.exists()){
                        Log.d("[Ficha editable]",ficha_editable.getData().toString());
                        edit_name.setText(ficha_editable.get("nombre_paciente").toString());
                        edit_surname.setText(ficha_editable.get("apellido_paciente").toString());
                        edit_email.setText(ficha_editable.get("correo").toString(),TextView.BufferType.EDITABLE);
                        edit_address.setText(ficha_editable.get("direccion").toString(),TextView.BufferType.EDITABLE);
                        edit_phone.setText(ficha_editable.get("telefono").toString(),TextView.BufferType.EDITABLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No se encuentra la ficha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
    }

    /**
     *
     * @param view
     */
    public void subirCambios(View view){
        Intent intent = new Intent(this, menu.class);

        btneditar.setOnClickListener(new View.OnClickListener() {
            String edit_ficha_num = numficha.getText().toString();
            String edit_ficha_name = edit_name.getText().toString().trim();
            String edit_ficha_surname = edit_surname.getText().toString().trim();
            String edit_ficha_email = edit_email.getText().toString().trim();
            String edit_ficha_address = edit_address.getText().toString().trim();
            String edit_ficha_phone = edit_phone.getText().toString().trim();
            String estado_selected = estado.getSelectedItem().toString();

            @Override
            public void onClick(View view) {
                if( edit_ficha_name.isEmpty() || edit_ficha_surname.isEmpty() || edit_ficha_email.isEmpty() || edit_ficha_address.isEmpty() || edit_ficha_phone.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    edit_ficha(edit_ficha_num,edit_ficha_name,edit_ficha_surname,edit_ficha_email,edit_ficha_address,edit_ficha_phone,estado_selected);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Funcion para editar los datos de una ficha
     * @param num
     * @param name
     * @param surname
     * @param email
     * @param address
     * @param phone
     * @param estado_f
     */
    public void edit_ficha(String num,String name, String surname, String email, String address, String phone, String estado_f){
        Map<String, Object> ficha = new HashMap<>();
        ficha.put("nombre_paciente",name);
        ficha.put("apellido_paciente",surname);
        ficha.put("correo",email);
        ficha.put("direccion",address);
        ficha.put("telefono",phone);
        ficha.put("estado", estado_f);

        db.collection("Fichas").document(num).update(ficha).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Ficha editada con éxito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al editar una Ficha",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void eliminarFicha(View view){
        Intent intent = new Intent(this, menu.class);

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ficha_num = numficha.getText().toString();

                AlertDialog.Builder eliminar = new AlertDialog.Builder(fichaEncontrada.this);
                eliminar.setMessage("¿Desea eliminar la ficha?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFicha(ficha_num);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = eliminar.create();
                titulo.setTitle("Eliminar Fichas");
                titulo.show();

            }
        });

    }

    public void deleteFicha(String num){
        db.collection("Fichas").document(num).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("[Eliminar]", "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("[Eliminar]", "Error deleting document", e);
            }
        });
    }

    /**
     * Función para abrir la camara y guardar la imagen de la ficha
     */
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