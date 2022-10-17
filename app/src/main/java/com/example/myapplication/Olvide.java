package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Olvide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide);

        Button botonConsulta = findViewById(R.id.BotonC);
        botonConsulta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Módulo no funcional", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Olvide.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


        Button botonVolver = findViewById(R.id.BotonV);
        botonVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Olvide.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}