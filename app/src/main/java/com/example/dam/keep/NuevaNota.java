package com.example.dam.keep;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam.keep.pojo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class NuevaNota extends AppCompatActivity {

    private android.widget.TextView textView;
    private android.widget.EditText editText;
    private String urlDestino = "http://192.168.208.36:8080/KeepH/go";
    private Usuario usuario;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);
        this.editText = (EditText) findViewById(R.id.editText);
        this.textView = (TextView) findViewById(R.id.textView);
        usuario = getIntent().getParcelableExtra("usuario");
        random = new Random();
    }


    public void createN(View view) {
        createNota h3 = new createNota();
        h3.execute(editText.getText().toString());
    }

    private class createNota extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                String my_new_str = params[0].replaceAll(" ", "--");
                URL url = new URL(urlDestino + "?tabla=keep&op=create&accion=&origen=android&idAndroid="+random.nextInt()+"&contenido=" + my_new_str + "&estado=estable&ruta=no&login="+usuario.getEmail());
                Log.v("URL", url.toString()); // El idAndroid debe ser siempre distinto, error
                URLConnection conexion = url.openConnection();
                conexion.setDoOutput(false);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conexion.getInputStream()));
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Toast.makeText(getApplicationContext(), R.string.guardar, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Principal.class);
            i.putExtra("usuario", usuario);
            startActivity(i);
        }
    }

}
