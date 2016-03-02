package com.example.dam.keep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam.keep.adaptador.Adaptador;
import com.example.dam.keep.pojo.Keep;
import com.example.dam.keep.pojo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private Usuario usuario;
    private RecyclerView recView;
    private boolean online = false;
    private Adaptador adaptador;
    private String urlDestino = "http://192.168.208.36:8080/KeepH/go";
    private ArrayList<Keep> kList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        usuario = getIntent().getParcelableExtra("usuario");
        if(usuario.getEmail().compareTo("")==0){
            Toast.makeText(this, usuario.getEmail(), Toast.LENGTH_SHORT).show();
            online = true;
        }
        init();
    }

    public void init(){
        recView = (RecyclerView) findViewById(R.id.view);
        recView.setHasFixedSize(true);
        kList = new ArrayList<>();
        getNotas h2 = new getNotas();
        h2.execute(usuario.getEmail());
    }

    public void delete(View view) {
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.eliminarID);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.eliminar_id, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.eliminar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText id = (EditText) vista.findViewById(R.id.etID);
                        String str = id.getText().toString();
                        deleteNota h3 = new deleteNota();
                        h3.execute(str);
                        Toast.makeText(getApplicationContext(), R.string.eliminando, Toast.LENGTH_SHORT).show();
                    }
                });
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
    }

    public void create(View view) {
        Intent i = new Intent(this, NuevaNota.class);
        i.putExtra("usuario", usuario);
        startActivity(i);
    }

    private class getNotas extends AsyncTask<String, Void, Integer> {
        String todo = "";

        @Override
        protected Integer doInBackground(String... params) {
            URL url = null;
            BufferedReader in = null;
            String login = "";
            try {
                login = URLEncoder.encode(params[0], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String destino = urlDestino + "?tabla=keep&op=read&accion=&login=" + login + "&origen=android";

            Log.v("Destino", destino);

            try {
                url = new URL(destino);
                in = new BufferedReader(new InputStreamReader(url.openStream()));
                String lin;
                while ((lin = in.readLine()) != null) {
                    todo = lin;
                }
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            try {
                JSONObject js = new JSONObject(todo);
                JSONArray jArray = new JSONArray(js.getString("keeps"));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    long id = jObject.getLong("id");
                    String contenido = jObject.getString("contenido");
                    String my_new_str = contenido.replaceAll("--", " ");
                    Keep p = new Keep(id, my_new_str, null);
                    kList.add(p);
                }
                adaptador = new Adaptador(kList);
                recView.setAdapter(adaptador);
                recView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
    }

    private class deleteNota extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(urlDestino + "?tabla=keep&op=delete&accion=&id=" + params[0]);
                Log.v("URL", url.toString());
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
            Toast.makeText(getApplicationContext(), R.string.eliminado, Toast.LENGTH_SHORT).show();
        }
    }

    /*
    http://192.168.208.36:8080/KeepH/go?tabla=keep&op=create&accion=&origen=android&idAndroid=284&contenido=hoy&estado=estable&ruta=da&login=juan
    http://192.168.208.36:8080/KeepH/go?tabla=usuario&op=delete&accion=&login=juan&origen=android
    http://192.168.208.36:8080/KeepH/go?tabla=keep&op=delete&accion=&id=2
    */


}
