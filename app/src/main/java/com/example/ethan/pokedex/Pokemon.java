package com.example.ethan.pokedex;

import java.util.ArrayList;

/**
 * Created by Ethan on 11/5/17.
 */

public class Pokemon {
    private int id;
    private String name;
    private String url;
    private int height;
    private int weight;
    private String type;
    private ArrayList<PokemonMove> moveList;
    //species
    //weight
    //height
    //abilities

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        String[] urlId = url.split("/");
        return Integer.parseInt(urlId[urlId.length-1]);
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<PokemonMove> getMoveList() {
        return moveList;
    }

    public void setMoveList(ArrayList<PokemonMove> moveList) {
        this.moveList = moveList;
    }

    public int getMoveListSize(){
        return moveList.size();
    }

}
