package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Olvide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide);

    }

    public void RecuperarContraseña (View v){
        EditText userName = (EditText) findViewById(R.id.EditUsr);
        EditText Mail = (EditText) findViewById(R.id.editCorreo);

        String mensaje = "";

        if("".equals(userName.getText().toString()) || "".equals(Mail.getText().toString()))
        {
            mensaje = "Por favor, llena todos los campos";
        }else{
            boolean TipoCorreo = false;
            String Correo = "";
            for(int x = 0 ; x < Mail.length(); x++){
                if(Mail.getText().charAt(x) == '@'){
                    for(int y = x ; y < Mail.length(); y++){
                        Correo = Correo + Mail.getText().charAt(y);
                    }
                    if("@gmail.com".equals(Correo) || "@hotmail.com".equals(Correo) || "@outlook.com".equals(Correo)){
                        TipoCorreo = true;
                    }
                    break;
                }
            }
            if(userName.length() > 20 || Mail.length() > 25 || TipoCorreo == false){
                mensaje = "Parametro Erroneo";
                if(userName.length() > 20){mensaje = "Nombre de Usuario Muy Largo";}
                if(Mail.length() > 25){mensaje = "Correo Muy Largo";}
                if(TipoCorreo == false){mensaje = "Correo Invalido";}
            }else {
                try {
                    json json = new json();
                    Des myDes = new Des();

                    String MailCorreo = "";
                    String HTMLCorreo = "";
                    String valorPass = "";

                    boolean BucleArchivo = true;
                    int x = 1;
                    int numArchivo = 0;
                    while (BucleArchivo) {
                        File Cfile = new File(getApplicationContext().getFilesDir() + "/" + "ArchivoMyPaginaWeb" + x + ".txt");
                        if(Cfile.exists()) {
                            BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("ArchivoMyPaginaWeb" + x + ".txt")));
                            String lineaTexto = file.readLine();
                            String completoTexto = "";
                            while(lineaTexto != null){
                                completoTexto = completoTexto + lineaTexto;
                                lineaTexto = file.readLine();
                            }
                            file.close();

                            MyInfo datos = json.leerJson(completoTexto);
                            String valorName = datos.getUsuario();
                            String valorMail = datos.getCorreo();

                            if (valorName.equals(userName.getText().toString()) & valorMail.equals(Mail.getText().toString())) {
                                mensaje = "Usuario Encontrado";
                                MailCorreo = valorMail;
                                valorPass = String.format(String.valueOf(Math.random() * 10000));

                                String TAG = "MyPaginaWeb";
                                Log.i( TAG , valorPass);

                                digest digest = new digest();
                                byte[] txtByte = digest.createSha1(valorName + valorPass);
                                String Sha1Password = digest.bytesToHex(txtByte);

                                MyInfo datitos = new MyInfo();
                                Gson gson = new Gson();
                                Des des = new Des();

                                datitos.setNombre(datos.getNombre());
                                datitos.setUsuario(datos.getUsuario());
                                datitos.setCorreo(datos.getCorreo());
                                datitos.setGenero(datos.getGenero());
                                datitos.setContraseña(datos.getContraseña());

                                String TxtJson = myDes.cifrar(gson.toJson(datitos));

                                BufferedWriter file2 = new BufferedWriter(new OutputStreamWriter(openFileOutput("ArchivoMyPaginaWeb" + x + ".txt", Context.MODE_PRIVATE)));
                                file2.write(TxtJson);
                                file2.close();

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
                        HTMLCorreo = "<html><body>Se ha solicitado el cambio de contraseña," +
                                " su nueva contraseña es: " + valorPass + ". Si no solicitado esta cambio, ya se cambió la contraseña /:</body></html>";
                        MailCorreo = myDes.cifrar(MailCorreo);
                        HTMLCorreo = myDes.cifrar(HTMLCorreo);

                        if( sendInfo( MailCorreo, HTMLCorreo ) ) {
                            mensaje = "Se envío el Correo";
                        }
                        else {
                            mensaje = "Error en el envío del Correo";
                        }
                    }

                } catch (Exception e) {
                    mensaje = "Error en el Archivo";
                }
            }
        }
        Toast.makeText(Olvide.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public boolean sendInfo( String Correo , String HTML )
    {
        String TAG = "App";
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;

        jsonObject = new JSONObject( );
        try {
            jsonObject.put("correo" , Correo);
            jsonObject.put("mensaje", HTML);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }

    public void Volver (View v){
        Intent intent = new Intent (Olvide.this, Login.class);
        startActivity( intent );
    }
}