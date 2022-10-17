package com.example.myapplication;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import androidx.appcompat.app.AppCompatActivity;

public class archivo extends AppCompatActivity {


    public static String datitos;

    public void CrearArchivo(String json){
        try {
            String ruta = "/data/data/com.example.myapplication/files/Datos.txt";
            File archivo = new File(ruta);

            if (!archivo.exists()) {
                archivo.createNewFile();
                FileWriter fw = new FileWriter(archivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println(json);
            } else {
                FileWriter fw = new FileWriter(archivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println(json);
            }
        } catch(Exception e){
            Toast.makeText( getApplicationContext(), "Error :(", Toast.LENGTH_LONG);
        }
    }

    public void LeerArchivo( String archivo ) throws FileNotFoundException, IOException {
        String cadenita;
        /*String datitos = "";*/
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadenita = b.readLine()) != null){
            datitos = datitos + cadenita;
        }
    }
}
