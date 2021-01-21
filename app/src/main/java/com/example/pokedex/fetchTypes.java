package com.example.pokedex;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ahmadrosid.svgloader.SvgLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fetchTypes extends AsyncTask<Void, Void, Void> {
    protected String data = "";
    protected ArrayList<String> strTypes; // Create an ArrayList object

    public Activity act;

    public fetchTypes(Activity act) {
        strTypes = new ArrayList<String>();
        this.act = act;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/type");
            Log.i("logtest", "https://pokeapi.co/api/v2/type");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(data);

            // Get type/types
            JSONArray types = new JSONArray(jObject.getString("results"));

            for(int i=0; i<types.length(); i++){
                JSONObject type = new JSONObject(types.getString(i));
                //JSONObject type2  = new JSONObject(type.getString("type"));
                strTypes.add(type.getString("name"));
                Log.i("logtest", type.getString("name"));
            }

            Log.i("logtest", "" + strTypes.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (act != null) {
            if (act instanceof MainActivity) {
                ((MainActivity) act).tipos = strTypes;
                ((MainActivity) act).TypeSearch();
            }
        }
    }
}

