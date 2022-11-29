package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EditarLista extends AppCompatActivity {

    private EditText Nombre;
    private EditText Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista);
        Nombre = (EditText) findViewById(R.id.editTextNombre);
        Pass = (EditText) findViewById(R.id.editTextContra);

        int numArchivo = getIntent().getExtras().getInt("numArchivo");
        int numContext = getIntent().getExtras().getInt("numContext");

        try {
            if (numContext == 2) {
                int numArchivoCuenta = getIntent().getExtras().getInt("numArchivoCuenta");
                BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + numArchivo + "." + numArchivoCuenta + ".txt")));
                String lineaTexto = file.readLine();
                String completoTexto = "";
                while (lineaTexto != null) {
                    completoTexto = completoTexto + lineaTexto;
                    lineaTexto = file.readLine();
                }
                file.close();

                json json =  new json();
                MyInfo datitos = json.leerJson(completoTexto);
                String nombrecito = datitos.getNombre();
                String contraseni = datitos.getContraseña();

                Nombre.setText(nombrecito);
                Pass.setText(contraseni);
            }
        } catch(Exception e){

        }
        


        }

        public void Enviar(View v){
            int numArchivo = getIntent().getExtras().getInt("numArchivo");
            int numContext = getIntent().getExtras().getInt("numContext");
            if("".equals(String.valueOf(Nombre.getText())) || "".equals(String.valueOf(Pass.getText()))){
                Toast.makeText(EditarLista.this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
            } else{
                if(numContext == 1){
                    try{
                        String valorNombre = Nombre.getText().toString();
                        String valorPassword = Pass.getText().toString();

                        MyData myData = new MyData();
                        myData.setName(valorNombre);
                        myData.setContra(valorPassword);

                        Gson gson = new Gson();
                        String json = gson.toJson(myData);

                        boolean BucleArchivo = true;
                        int x = 1;
                        while (BucleArchivo) {
                            File Cfile = new File(getApplicationContext().getFilesDir() + "/" + "Archivo" + numArchivo + "." + x + ".txt");
                            if (Cfile.exists()) {x = x + 1;}
                            else {
                                BufferedWriter fileC = new BufferedWriter(new OutputStreamWriter(openFileOutput("Archivo" + numArchivo + "." + x + ".txt", Context.MODE_PRIVATE)));
                                fileC.write(json);
                                fileC.close();

                                BucleArchivo = false;
                            }
                        }


                    } catch (Exception e){

                    }

                }
                if(numContext == 2){
                    try{
                        int numArchivoCuenta = getIntent().getExtras().getInt("numArchivoCuenta");

                        String valorNombre = Nombre.getText().toString();
                        String valorPassword = Pass.getText().toString();

                        MyData myData = new MyData();
                        myData.setName(valorNombre);
                        myData.setContra(valorPassword);

                        

                        Gson gson = new Gson();
                        String json = gson.toJson(myData);

                        BufferedWriter fileC = new BufferedWriter(new OutputStreamWriter(openFileOutput("Archivo" + numArchivo + "." + numArchivoCuenta + ".txt", Context.MODE_PRIVATE)));
                        fileC.write(json);
                        fileC.close();
                    } catch(Exception e){

                    }

                }
                Intent intent = new Intent(EditarLista.this, PaginaPrincipal.class);
                intent.putExtra("numArchivo", numArchivo);
                startActivity(intent);
            }
        }

    public void Volver (View v){
        int numArchivo = getIntent().getExtras().getInt("numArchivo");
        Intent intent = new Intent (EditarLista.this, PaginaPrincipal.class);
        intent.putExtra("numArchivo", numArchivo);
        startActivity( intent );
    }
}