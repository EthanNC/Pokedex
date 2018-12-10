package com.example.ethan.pokedex;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ethan.pokedex.pokeapi.PokeapiService;
import com.example.ethan.pokedex.ListPokemonAdapter;

import java.security.Policy;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG ="POKEDEX";

    public Retrofit mRetrofit;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private int offset;
    private boolean isScrolledCheck;
    private MenuItem menuItem;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManger = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        if(dy> 0) {
                            int visbleItemCount = layoutManger.getChildCount();
                            int totalItemCount = layoutManger.getItemCount();
                            int pastVisibleItems = layoutManger.findFirstVisibleItemPosition();


                            if (isScrolledCheck) {
                                if ((visbleItemCount + pastVisibleItems) >= totalItemCount){
                                    isScrolledCheck = false;
                                    //offset += 20;
                                    //offset= 0;
                                    //getData(offset);
                                }
                            }

                        }
                    }
                }
        );

        mRetrofit =new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create()).build();

        offset = 0;
        isScrolledCheck = true;
        getData(offset);
    }

    private void getData(int offset) {
        PokeapiService service = mRetrofit.create(PokeapiService.class);
        Call<PokemonRequest> pokemonRequestCall = service.getPokemonList(251);

        pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                isScrolledCheck = true;
                if(response.isSuccessful()){
                    PokemonRequest pokemonRequest = response.body();
                    ArrayList<Pokemon> pokemonList =  pokemonRequest.getResults();

                    listPokemonAdapter.searchPokemonList(pokemonList);
                    getStat();
                }
                else{
                    Log.e(TAG," onResponse:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                isScrolledCheck = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public void getStat() {
        PokeapiService service = mRetrofit.create(PokeapiService.class);
        ArrayList<Pokemon> pokemonArrayList = listPokemonAdapter.dataset;
        for (Pokemon p: pokemonArrayList) {
            Call<PokemonRequest> pokemonRequestCall = service.getPokemonStat(p.getId());

            pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
                @Override
                public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                    if (response.isSuccessful()) {

                        PokemonRequest pokemonRequest = response.body();
                        Pokemon updatePokemon = listPokemonAdapter.dataset.get(pokemonRequest.getId()-1);


                        updatePokemon.setHeight(pokemonRequest.getHeight());
                        updatePokemon.setWeight(pokemonRequest.getWeight());
                        updatePokemon.setType(pokemonRequest.getTypes().get(0).getType().getName());
//                        ArrayList<PokemonMove>pokemonMovesList = pokemonRequest.getMoves();
//                        updatePokemon.setMoveList(pokemonMovesList);


                        listPokemonAdapter.dataset.set(pokemonRequest.getId()-1,updatePokemon);

//                        Log.d("PokemonStats", "DATA: " + updatePokemon.getId());
//                        Log.d("PokemonHeight", "Height: " + updatePokemon.getHeight());
//                        Log.d("PokemonWeight", "Weight: " + updatePokemon.getWeight());
//                        Log.d("PokemonType", "Type: " + updatePokemon.getType());
//                        Log.d("PokemonMove", "First Move: " + updatePokemon.getMoveList().get(0).getMove().getName());



                    } else {
                        Log.e(TAG, " onResponse:" + response.errorBody());
                    }

                }

                @Override
                public void onFailure(Call<PokemonRequest> call, Throwable t) {


                }

            });
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_items,menu);
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Pokemon..");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_team:
                Intent intent = new Intent(this, TeamActivity.class);
                this.startActivity(intent);
                break;
            case R.id.action_destroy:
                PokeDbHelper db = new PokeDbHelper(this);
                db.deleteAll();
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onQueryTextChange(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Pokemon> newList = new ArrayList<>();
        for (Pokemon pokemon : listPokemonAdapter.dataset) {
            String name = pokemon.getName();
            if (name.contains(newText))
                newList.add(pokemon);
        }

        listPokemonAdapter.setFilter(newList);
        listPokemonAdapter.notifyDataSetChanged();
        return true;
    }
}