package com.example.ethan.pokedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ethan.pokedex.PokemonTable.PokemonTableEntry;
/**
 * Created by rob on 11/23/17.
 */

public class PokeDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PokeDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = PokemonTableEntry.TABLE_NAME;
    private static final int DATABASE_VERSION = 2;
    String DB_PATH = null;

    public PokeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_POKE_TABLE = "CREATE TABLE " + PokemonTableEntry.TABLE_NAME + "("
                + PokemonTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PokemonTableEntry.COLUMN_API_ID + " INTEGER,"
                + PokemonTableEntry.COLUMN_NAME + " TEXT,"
                + PokemonTableEntry.COLUMN_URL + " TEXT,"
                + PokemonTableEntry.COLUMN_TYPE + " TEXT,"
                + PokemonTableEntry.COLUMN_HEIGHT + " INTEGER,"
                + PokemonTableEntry.COLUMN_WEIGHT + " INTEGER"
                + ");";

        db.execSQL(SQL_CREATE_POKE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPokemon(String name, String id, String type, String height, String weight){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PokemonTable.PokemonTableEntry.COLUMN_NAME, name);
        values.put(PokemonTable.PokemonTableEntry.COLUMN_API_ID, id);
        values.put(PokemonTable.PokemonTableEntry.COLUMN_TYPE, type);
        values.put(PokemonTable.PokemonTableEntry.COLUMN_HEIGHT, height);
        values.put(PokemonTable.PokemonTableEntry.COLUMN_WEIGHT, weight);
        database.insert(PokemonTable.PokemonTableEntry.TABLE_NAME , null, values);

        Log.d("Added Pokemon", "DATA"+ name);
    }

    public Cursor getAllRows(){
        SQLiteDatabase database = this.getReadableDatabase();
        String [] rows = {PokemonTable.PokemonTableEntry._ID, PokemonTableEntry.COLUMN_NAME, PokemonTableEntry.COLUMN_TYPE,
        PokemonTableEntry.COLUMN_HEIGHT, PokemonTableEntry.COLUMN_WEIGHT};
        String where = null;
        Cursor c =  database.query(true, DATABASE_NAME, rows, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public boolean deleteRow(long rowID){
        SQLiteDatabase database = this.getWritableDatabase();
        String where = PokemonTableEntry._ID + "="+ rowID;
        return database.delete(DATABASE_NAME, where, null) != 0;
    }

    public void deleteAll(){
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(PokemonTableEntry._ID);
        if(c.moveToFirst()){
            do{
                deleteRow(c.getLong((int) rowId));

            }while (c.moveToNext());
        }
        c.close();
    }



}
