package com.example.dam.keep.gestion;


import android.util.Log;

import com.example.dam.keep.pojo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GestionUsuario {

    private String urlDestino = "http://192.168.208.36:8080/KeepH/go";


    public boolean isValidUser(Usuario u) {

        URL url = null;
        BufferedReader in = null;
        String res = "";
        String login = "";
        String pass = "";
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
            pass = URLEncoder.encode(u.getPass(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String destino = urlDestino + "?tabla=usuario&op=login&accion=&login=" + login + "&pass=" + pass + "&origen=android";

        try {
            url = new URL(destino);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String lin;
            while ((lin = in.readLine()) != null) {
                Log.v("---------------", lin);
                res = lin;
            }
            in.close();
            JSONObject obj = new JSONObject(res);
            Log.v("BOOLEAN", String.valueOf(obj.getBoolean("r")));
            return obj.getBoolean("r");
        } catch (IOException | JSONException e) {
        }
        return false;
    }
}
