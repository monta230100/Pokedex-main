package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    protected int contador = 1;
    protected String count;
    public ArrayList<String> tipos= new ArrayList<String>();

    public static ImageView [] imgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = "1";
        fetchData process = new fetchData(count);
        process.execute();

        act = this;
        imgType = new ImageView[2];

        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTxtSearch();
            }
        });

        ImageButton btnTypes = findViewById(R.id.btnTypes);
        btnTypes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fetchTypes process = new fetchTypes(act);
                process.execute();

            }
        });

        final Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                contador -=1;
                if(contador <1) {
                    contador = 1;
                }
                count = Integer.toString(contador);
                fetchData process = new fetchData(count);
                process.execute();
            }
        });
        final Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador += 1;
                if(contador >898){
                    contador = 898;
                }
                count = Integer.toString(contador);
                fetchData process = new fetchData(count);
                process.execute();
            }
        });
    }

    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString();
                fetchData process = new fetchData(pokSearch);
                process.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void TypeSearch(){
        Log.i("logtest", "-------->" + tipos.size());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige un tipo:")
                .setItems(changeArrayListToArray(tipos), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int type = which + 1;
                        Log.i("logtest","" + (which+1));

                    }
                });
        builder.show();
    }


    public String[] changeArrayListToArray (ArrayList<String> types){
        String [] tipos = new String[types.size()];

        for(int i=0; i<types.size();i++){
            tipos[i] = types.get(i);
        }

        return tipos;
    }


}