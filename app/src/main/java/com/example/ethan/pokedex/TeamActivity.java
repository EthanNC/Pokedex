package com.example.ethan.pokedex;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class TeamActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        populateListView();
    }

    private void populateListView(){
        PokeDbHelper db = new PokeDbHelper(this);
        Cursor cursor = db.getAllRows();
        Log.d("Added Pokemon", "DATA"+ DatabaseUtils.dumpCursorToString(cursor));
        String [] fromFieldNames = new String[] {PokemonTable.PokemonTableEntry.COLUMN_NAME, PokemonTable.PokemonTableEntry.COLUMN_TYPE,
                PokemonTable.PokemonTableEntry.COLUMN_WEIGHT, PokemonTable.PokemonTableEntry.COLUMN_HEIGHT};
        int[] toViewIDs = new int[] {R.id.nameTextView,R.id.typeTextView, R.id.heightTextView, R.id.weightTextView};
        SimpleCursorAdapter cursorAdapter;
        cursorAdapter = new SimpleCursorAdapter(getApplicationContext() ,R.layout.item_pokemonw,cursor,fromFieldNames,toViewIDs,0);
        ListView myList = (ListView) findViewById(R.id.listPokemon);
        myList.setAdapter(cursorAdapter);
    }
}
