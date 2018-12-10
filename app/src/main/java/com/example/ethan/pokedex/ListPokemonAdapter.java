package com.example.ethan.pokedex;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.os.Bundle;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ethan.pokedex.pokeapi.PokeapiService;

import java.util.ArrayList;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.os.Build.VERSION_CODES.O;

/**
 * Created by Ethan on 11/5/17.
 */


public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {


    private ArrayList<String> pokemonMoveList;
    private Retrofit retrofit;
    ArrayList<Pokemon> dataset = new ArrayList<>();
    private Context context;
    private static final String TAG = "TAGNAME";
    private ArrayMap<Integer, ImageView> imageMap = new ArrayMap<>();


    public ListPokemonAdapter(Context context){

        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create()).build();

        this.context = context;
        dataset = new ArrayList<>();}

    @Override
    public ListPokemonAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final ImageView imgView;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pokemon, parent, false);

        final TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
        imgView = (ImageView) view.findViewById(R.id.photoImageView);

        imgView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                // Create a new intent to open the {@link PhrasesActivity}


                Integer imageId = 0;
                pokemonMoveList = new ArrayList<String>();
                Log.d("PokemonName", "DATA: " + nameView.getText());
                for (Pokemon p: dataset){
                    //find the pokemon clicked on data
                    if(p.getName() != null && p.getName().contains(nameView.getText())){
                        imageId = p.getId();
                    }
//                    for(int i = 0; i < p.getMoveListSize(); i++){
//                        pokemonMoveList.add(p.getMoveList().get(i).getMove().getName());
//                        Log.d("PokemonMoves", "DATA: " + p.getMoveList().get(i).getMove().getName());
//                    }


                }
//                if(imageId != 0){
//                    getStat(imageId);
//                }
                Log.d("PokemonId", "DATA: " + imageId);
                ImageView pokeImage = imageMap.get(imageId);
                Intent pokeIntent = new Intent(context.getApplicationContext(), PokemonActivity.class);
                //pokeIntent.putExtra("name", nameView.getText());
                //my problem is here***************************************************

                //my problem is here***************************************************
                view.buildDrawingCache();
                Bitmap image = pokeImage.getDrawingCache();

                Bundle extras = new Bundle();
                Pokemon p = dataset.get(imageId-1);
                String name = p.getName();
                String type = p.getType();
                Integer height = p.getHeight();
                Integer weight = p.getWeight();
                extras.putString("id", String.valueOf(imageId));
                extras.putParcelable("imagebitmap", image);
                extras.putString("name", name);
                extras.putString("type", type);
                extras.putString("height","Height: " + height + " inches");
                extras.putString("weight","Weight: " + weight + "lbs");
                extras.putStringArrayList("moves",pokemonMoveList);
                pokeIntent.putExtras(extras);
              //  context.startActivity(pokeIntent);



                //pokeIntent.putExtra("image", view.getXXXXX());
                //*********************************************************************
                // Start the new activity
                Log.d(TAG, "Starting Pokemon Activity");
                context.startActivity(pokeIntent);
            }

          /*  public void onClick(View v) {

                //v.getId() will give you the image id
            } */
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListPokemonAdapter.ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nameTextView.setText(p.getName());



        Glide.with(context).load("http://pokeapi.co/media/sprites/pokemon/" + p.getId() + ".png")
            .centerCrop()
            .crossFade()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.photoImageView);

        imageMap.put(p.getId(), holder.photoImageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void searchPokemonList(ArrayList<Pokemon> pokemonList) {
        //dataset.clear();
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public void clearPokemonList(){
        dataset.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView photoImageView;
        private TextView nameTextView;

        public ViewHolder(View itemView){
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        }


    }

    public void setFilter(ArrayList<Pokemon> newList){
        dataset = new ArrayList<>();
        dataset.addAll(newList);
        notifyDataSetChanged();

    }

    //TODO: Is unused atm. Using the version in MainActivity. This Adds pokemon stats on click instead of in bulk.
    public void getStat(int id){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRequest> pokemonRequestCall = service.getPokemonStat(id);

        pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                if (response.isSuccessful()) {

                    PokemonRequest pokemonRequest = response.body();
                    Pokemon updatePokemon = dataset.get(pokemonRequest.getId()-1);


                    updatePokemon.setHeight(pokemonRequest.getHeight());
                    updatePokemon.setWeight(pokemonRequest.getWeight());
                    updatePokemon.setType(pokemonRequest.getTypes().get(0).getType().getName());
                    ArrayList<PokemonMove>pokemonMovesList = pokemonRequest.getMoves();
                    updatePokemon.setMoveList(pokemonMovesList);


                    dataset.set(pokemonRequest.getId()-1,updatePokemon);

                        Log.d("PokemonStats", "DATA: " + updatePokemon.getId());
                        Log.d("PokemonHeight", "Height: " + updatePokemon.getHeight());
                        Log.d("PokemonWeight", "Weight: " + updatePokemon.getWeight());
                        Log.d("PokemonType", "Type: " + updatePokemon.getType());
                        Log.d("PokemonMove", "First Move: " + updatePokemon.getMoveList().get(0).getMove().getName());



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
