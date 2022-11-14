package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PaginaPrincipal extends AppCompatActivity {

    private ListView listView;
    private List<MyData> list;
    private int []imagen = { R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        TextView Nombre;

        Nombre = findViewById(R.id.NombreUsr);

        listView = (ListView) findViewById(R.id.listViewId1);
        list = new ArrayList<MyData>();
        Intent intent = getIntent();

        for (int i = 0; i < 4; i++){
            MyData myData = new MyData();
            myData.setContra(String.format("Contraseña: %d",(int)(Math.random()*10000)));

            if (i == 0){
                myData.setName(String.format( "AMDGMAAF"));
                myData.setImage(imagen[0]);
            }
            if (i == 1){
                myData.setName(String.format( "IOADK"));
                myData.setImage(imagen[1]);
            }
            if (i == 2){
                myData.setName(String.format( "TDIH" ));
                myData.setImage(imagen[2]);
            }
            if (i == 3){
                myData.setName(String.format( "TGWCT" ));
                myData.setImage(imagen[3]);
            }
            list.add(myData);
        }


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

        MyAdapter myAdapter = new MyAdapter(list, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast( i );
            }
        });

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
    private void toast(int i){
        Toast.makeText(getBaseContext(), list.get(i).getContra(),Toast.LENGTH_SHORT).show();
    }
}