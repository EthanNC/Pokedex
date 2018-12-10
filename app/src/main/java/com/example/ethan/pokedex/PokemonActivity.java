package com.example.ethan.pokedex;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by christianokoli on 11/21/17.
 */


public class PokemonActivity extends AppCompatActivity {
    private static final String TAG = "TAGN";
    private TextView textView;
    private String genus;
    private int num;
    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        Log.d(TAG,"Activity Opened");


        //String nameP = getIntent().getExtras().getString("name");

        PokemonFrag fragment = new PokemonFrag();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        final String nameP = extras.getString("name");
        final String idP = extras.getString("id");
        final String typeP = extras.getString("type");
        final String heightP = extras.getString("height");
        final String weightP = extras.getString("weight");
        //ArrayList<String> movesP = extras.getStringArrayList("moves");

        //ListView moveList = (ListView) findViewById(R.id.moveView);
        ImageView image = (ImageView) findViewById(R.id.imgView);
        image.setImageBitmap(bmp);
        Log.d("PokemonType", "DATA: " + typeP);
        //TODO: ArrayList of pokemon moves into listview
        final TextView name = (TextView) findViewById(R.id.nameView);
        name.setText(nameP);
        TextView type = (TextView) findViewById(R.id.typeView);
        type.setText(typeP + " Type");
        TextView height = (TextView) findViewById(R.id.heightView);
        height.setText(heightP);
        TextView weight = (TextView) findViewById(R.id.weightView);
        weight.setText(weightP);
        ft.commit();

        //add to team
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PokeDbHelper db = new PokeDbHelper(getApplicationContext());
                db.insertPokemon(nameP,idP,typeP, heightP, weightP);
                Toast.makeText(getApplicationContext(), "You added " + nameP + " to your team!", Toast.LENGTH_SHORT).show();
            }
        });

    }




}
