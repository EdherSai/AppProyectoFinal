package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;

public class registro extends AppCompatActivity {

    EditText usuario;
    EditText nombre;
    EditText contraseña;
    EditText correo;
    String VGenero = "";
    String VAlumno = "";
    String json = null;

    public void leerArchivo() {
        try{
            BufferedReader aux = new BufferedReader(new InputStreamReader(openFileInput("datos.txt")));

            String texto = aux.readLine();

            Toast.makeText( this, "TEXTO EN EL ARCHIVO: "+texto, Toast.LENGTH_LONG).show();

        } catch(Exception e){
            Log.e("Archivo", "ERROR AL LEER");
        }
    }

    public void guardarArchivo() {
        try{
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("datos.txt", Context.MODE_PRIVATE));

            archivo.write(json);
            archivo.close();

            leerArchivo();
        } catch(Exception e){
            Log.e("Archivo", "ERROR AL ESCRIBIR");
        }
    }

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
                correo = findViewById(R.id.TextCorreo);
                CheckBox alumno = findViewById(R.id.AlumnoBox);
                boolean estadoA = alumno.isChecked();
                RadioButton radioBoton = findViewById(R.id.radioMasculino);
                RadioButton radioBoton2 = findViewById(R.id.radioFemenino);
                RadioButton radioBoton3 = findViewById(R.id.radioOtro);
                RadioButton radioBoton4 = findViewById(R.id.radioNull);
                boolean estadoM = radioBoton.isChecked();
                boolean estadoF = radioBoton2.isChecked();
                boolean estadoO = radioBoton3.isChecked();
                boolean estadoN = radioBoton4.isChecked();

                if(estadoM == true){
                    VGenero = "Masculino";
                } else{
                    if(estadoF == true){
                        VGenero = "Femenino";
                    } else{
                        if(estadoO == true){
                            VGenero = "Otro";
                        } else{
                            if(estadoN == true){
                                VGenero = "NoEsp";
                            }
                        }
                    }
                }

                if(estadoA == true){
                    VAlumno = "si";
                } else{
                    VAlumno = "no";
                }
                String VUsuario = String.valueOf(usuario.getText());
                String VNombre = String.valueOf(nombre.getText());
                String VContraseña = String.valueOf(contraseña.getText());
                String VCorreo = String.valueOf(correo.getText());
                MyInfo myInfo = null;
                Gson gson = null;
                String json = null;
                String mensaje = null;

                myInfo = new MyInfo();
                myInfo.setUsuario(VUsuario);
                myInfo.setNombre(VNombre);
                myInfo.setContraseña(VContraseña);
                myInfo.setCorreo(VCorreo);
                myInfo.setGenero(VGenero);
                myInfo.setAlumno(VAlumno);
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