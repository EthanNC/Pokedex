package com.example.ethan.pokedex;

import java.util.ArrayList;

/**
 * Created by Ethan on 11/5/17.
 */

public class PokemonRequest {
    private int id;
    private ArrayList<Pokemon> results;
    private int height;
    private int weight;
    private ArrayList<PokemonType> types;
    private ArrayList<PokemonMove> moves;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ArrayList<PokemonType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<PokemonType> types) {
        this.types = types;
    }

    public ArrayList<PokemonMove> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<PokemonMove> moves) {
        this.moves = moves;
    }




}
