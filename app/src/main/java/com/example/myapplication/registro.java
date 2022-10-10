package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class registro extends AppCompatActivity {

    EditText usuario;
    EditText nombre;
    EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button boton = findViewById(R.id.button2);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(registro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        setContentView(R.layout.activity_registro);
        Button boton2 = findViewById(R.id.BotonEnviar);
        boton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                usuario = findViewById(R.id.TextUsuario);
                nombre = findViewById(R.id.TextNombre);
                contraseña = findViewById(R.id.TextPassword);
                String VUsuario = String.valueOf(usuario.getText());
                String VNombre = String.valueOf(nombre.getText());
                String VContraseña = String.valueOf(contraseña.getText());
                MyInfo myInfo = null;
                Gson gson = null;
                String json = null;
                String mensaje = null;

                myInfo = new MyInfo();
                myInfo.setUsuario(VUsuario);
                myInfo.setNombre(VNombre);
                myInfo.setContraseña(VContraseña);
                Log.d(TAG, "TEST");
                gson = new Gson();
                json = gson.toJson(myInfo);
                if(json != null){
                    Log.d(TAG, json);
                    mensaje = "OK";
                } else {
                    mensaje = "ERROR";
                }
                Toast.makeText( getApplicationContext(), mensaje, Toast.LENGTH_LONG );
                Intent intent = new Intent(registro.this, Login.class);
                startActivity(intent);
            }
        });
    }
}