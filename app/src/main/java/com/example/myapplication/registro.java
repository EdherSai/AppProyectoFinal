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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;

public class registro extends AppCompatActivity {

    EditText usuario;
    EditText nombre;
    EditText contraseña;
    EditText correo;
    EditText numero;
    EditText grado;
    EditText grupo;
    String VGenero = "";
    String VAlumno = "";
    String VRoblox = "";
    byte[] byt = null;
    String ContrasenaD;

    public void leerArchivo() {
        try{
            BufferedReader aux = new BufferedReader(new InputStreamReader(openFileInput("datos.txt")));

            String texto = aux.readLine();

            Toast.makeText( this, "¡Registro exitoso!", Toast.LENGTH_LONG).show();

        } catch(Exception e){
            Log.e("Archivo", "ERROR AL LEER");
        }
    }

    public void guardarArchivo( String json ) {
        try {
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





        Button boton = findViewById(R.id.BotonLogin);
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
                numero = findViewById(R.id.TextTelefono);
                grado = findViewById(R.id.TextGrado);
                numero = findViewById(R.id.TextGrupo);


                String cadena = usuario.getText().toString() + contraseña.getText().toString();

                digest digest =  new digest();
                byt = digest.createSha1(cadena);
                ContrasenaD = digest.bytesToHex(byt);

                if("".equals(String.valueOf(usuario.getText())) || "".equals(String.valueOf(nombre.getText())) ||
                        "".equals(String.valueOf(contraseña.getText())) || "".equals(String.valueOf(correo.getText()))) {
                    Toast.makeText( getApplicationContext(), "Llenar campos obligatorios", Toast.LENGTH_LONG ).show();
                } else{
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
                                    VGenero = "NoEsp";
                            }
                        }
                    }

                    if(estadoA == true){
                        VAlumno = "si";
                    } else{
                        VAlumno = "no";
                    }
                    CheckBox roblox = findViewById(R.id.RobloxBox);
                    boolean estadoR = roblox.isChecked();

                    if(estadoR == true){
                        VRoblox = "si";
                    } else{
                        VRoblox = "no";
                    }

                    String VUsuario = String.valueOf(usuario.getText());
                    String VNombre = String.valueOf(nombre.getText());
                    String VCorreo = String.valueOf(correo.getText());
                    String VGrado = String.valueOf(grado.getText());
                    String VGrupo = String.valueOf(grupo.getText());
                    MyInfo myInfo = null;
                    Gson gson = null;
                    String json = null;
                    String mensaje = null;

                    myInfo = new MyInfo();
                    myInfo.setUsuario(VUsuario);
                    myInfo.setNombre(VNombre);
                    myInfo.setContraseña(ContrasenaD);
                    myInfo.setCorreo(VCorreo);
                    myInfo.setGenero(VGenero);
                    myInfo.setAlumno(VAlumno);
                    myInfo.setRoblox(VRoblox);
                    myInfo.setGrado(VGrado);
                    myInfo.setGrupo(VGrupo);
                    Log.d(TAG, "TEST");
                    gson = new Gson();
                    json = gson.toJson(myInfo);
                    if(json != null){
                        Log.d(TAG, json);
                        mensaje = "OK";
                    } else {
                        mensaje = "ERROR";
                    }

                    try {
                        json json2 = new json();
                        boolean BucleArchivo = true;
                        int x = 1;
                        while (BucleArchivo) {
                            File Cfile = new File(getApplicationContext().getFilesDir() + "/" + "Archivo" + x + ".txt");
                            if (Cfile.exists()) {
                                BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + x + ".txt")));
                                String lineaTexto = file.readLine();
                                file.close();

                                MyInfo datos = json2.leerJson(lineaTexto);
                                String ValorUsr2 = datos.getUsuario();

                                if (VUsuario.equals(ValorUsr2)) {
                                    mensaje = "Usuario Ya Existente";
                                    BucleArchivo = false;
                                } else {
                                    x = x + 1;
                                }
                            } else {
                                BufferedWriter file = new BufferedWriter(new OutputStreamWriter(openFileOutput("Archivo" + x + ".txt", Context.MODE_PRIVATE)));
                                file.write(json);
                                file.close();
                                mensaje = "Usuario Registrado";
                                BucleArchivo = false;
                            }
                        }

                    } catch (Exception e) {
                        mensaje = "Error al Hacer Registro";
                    }

                    /*guardarArchivo(json);*/

                    /*
                    try {
                        String ruta = "/data/data/com.example.myapplication/files/Datos.txt";
                        File archivo = new File(ruta);

                        if (!archivo.exists()) {
                            archivo.createNewFile();
                            FileWriter fw = new FileWriter(archivo);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw);
                            out.println(json);
                        } else {
                            FileWriter fw = new FileWriter(archivo);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw);
                            out.println(json);
                        }
                    } catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Error :(", Toast.LENGTH_LONG);
                    }
                    */



                    Toast.makeText( getApplicationContext(), mensaje, Toast.LENGTH_LONG );
                    Intent intent = new Intent(registro.this, Login.class);
                    startActivity(intent);
                }

            }
        });
    }
}