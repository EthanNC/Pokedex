package com.example.ethan.pokedex;

import android.provider.BaseColumns;

/**
 * Created by rob on 11/23/17.
 */

public final class PokemonTable {
    private PokemonTable () {}

    public static final class PokemonTableEntry implements BaseColumns {
        public final static String TABLE_NAME = "pokemon";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_API_ID = "api_id";
        public final static String COLUMN_URL = "url";
        public final static String COLUMN_TYPE = "type";
        public final static String COLUMN_HEIGHT = "height";
        public final static String COLUMN_WEIGHT = "weight";
    }
}