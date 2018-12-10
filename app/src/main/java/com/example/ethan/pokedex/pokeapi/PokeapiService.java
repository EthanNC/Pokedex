package com.example.ethan.pokedex.pokeapi;

import com.example.ethan.pokedex.PokemonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ethan on 11/5/17.
 */

public interface PokeapiService {

    @GET("pokemon")
    //Call<PokemonRequest> getPokemonList(@Query("limit") int limit, @Query("offset") int  offset);
    Call<PokemonRequest> getPokemonList(@Query("limit") int limit);
    @GET("pokemon/{id}")
    Call<PokemonRequest> getPokemonStat(@Path("id") int id);

}
