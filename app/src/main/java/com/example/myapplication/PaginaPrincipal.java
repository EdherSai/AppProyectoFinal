package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PaginaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        TextView Nombre;

        Nombre = findViewById(R.id.NombreUsr);



        try{
            int y = getIntent().getExtras().getInt("archivo");
            json json = new json();
            BufferedReader archivito = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + y + ".txt")));
            String datos = archivito.readLine();
            MyInfo Data = json.leerJson(datos);
            archivito.close();

            Nombre.setText(Data.getNombre() + "!");
        } catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error :(", Toast.LENGTH_SHORT).show();
        }

        Button boton = findViewById(R.id.button2);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PaginaPrincipal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}