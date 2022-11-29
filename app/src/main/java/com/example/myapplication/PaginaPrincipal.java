package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private int imagen = R.drawable.usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        TextView Nombre;

        Nombre = findViewById(R.id.NombreUsr);

        listView = (ListView) findViewById(R.id.listViewId1);
        list = new ArrayList<MyData>();
        Intent intent = getIntent();

        int numArchivo = getIntent().getExtras().getInt("numArchivo");

        try{
            BufferedReader fileU = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + numArchivo + ".txt")));
            String lineaTextoU = fileU.readLine();
            String completoTextoU = "";
            while(lineaTextoU != null){
                completoTextoU = completoTextoU + lineaTextoU;
                lineaTextoU = fileU.readLine();
            }
            fileU.close();

            MyInfo datos = json.leerJson(completoTextoU);
            MyData myData = new MyData();
            myData.setName(datos.getNombre());
            myData.setContra(datos.getContraseña());
            myData.setImage(imagen);

            list.add(myData);

        } catch(Exception e){

        }
/*
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
*/

        try{
            int y = getIntent().getExtras().getInt("numArchivo");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean flag = false;
        MenuInflater menuInflater = null;
        flag = super.onCreateOptionsMenu(menu);
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return flag;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String seleccion = null;
        switch(item.getItemId()){
            case R.id.MenuNuevoId:
                Intent intent = new Intent(PaginaPrincipal.this, registro.class);
                startActivity( intent );
            case R.id.MenuCerrarSesionrId:
                Intent intent1 = new Intent (PaginaPrincipal.this, Login.class);
                startActivity( intent1 );
                break;
            case R.id.MenuEditar:
                int numArchivo = getIntent().getExtras().getInt("numArchivo");
                Intent intent2 = new Intent (PaginaPrincipal.this, EditarLista.class);
                intent2.putExtra("numArchivo", numArchivo);
                intent2.putExtra("numContext", 1);
                startActivity( intent2 );
                break;
            default:
                seleccion = "sin opcion %s";
                Toast.makeText(getBaseContext(), seleccion, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}