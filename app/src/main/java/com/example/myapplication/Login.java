package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login extends AppCompatActivity {

    public final String LOG_TAG = this.getClass().getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usr;
        EditText pass;

        usr = findViewById(R.id.usernameId);
        pass = findViewById(R.id.password);

        Button botonL = findViewById(R.id.loginid);
        botonL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String mensaje = "";

                if("".equals(String.valueOf(usr.getText())) || "".equals(String.valueOf(pass.getText()))){
                    Toast.makeText(getApplicationContext(), "Llena todos los campos", Toast.LENGTH_LONG).show();
                } else{
                    try {

                        digest digest = new digest();
                        byte[] txtByte = digest.createSha1(usr.getText().toString() + pass.getText().toString());
                        String Sha1Password1 = digest.bytesToHex(txtByte);

                        json json = new json();

                        boolean BucleArchivo = true;
                        int x = 1;
                        while (BucleArchivo) {
                            File Cfile = new File(getApplicationContext().getFilesDir() + "/" + "Archivo" + x + ".txt");
                            if(Cfile.exists()) {
                                BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + x + ".txt")));
                                String lineaTexto = file.readLine();
                                file.close();

                                MyInfo datos = json.leerJson(lineaTexto);
                                String Sha1Password2 = datos.getContraseña();

                                if (Sha1Password1.equals(Sha1Password2)) {
                                    mensaje = "Usuario Encontrado";
                                    BucleArchivo = false;
                                } else {
                                    x = x + 1;
                                }
                            }else{
                                mensaje = "Usuario no Encontrado";
                                BucleArchivo = false;
                            }
                        }

                        if("Usuario Encontrado".equals(mensaje)){
                            Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, PaginaPrincipal.class);
                            startActivity(intent);
                        }

                        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        mensaje = "Error en el Archivo";
                    }
                }

            }
        });

        Button boton = findViewById(R.id.forget);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Login.this, Olvide.class);
                startActivity(intent);
                finish();
            }
        });

        Button boton2 = findViewById(R.id.button);
        boton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Login.this, registro.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
