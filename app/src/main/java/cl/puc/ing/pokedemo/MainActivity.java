package cl.puc.ing.pokedemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.puc.ing.pokedemo.model.Pokemon;
import cl.puc.ing.pokedemo.model.Sprite;

public class MainActivity extends AppCompatActivity {
    private PokemonAdapter adapter;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.pokemon_list_view);

        adapter = new PokemonAdapter(this);
        queue = Volley.newRequestQueue(this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToPokemon(adapter.getItem(position));
            }
        });
    }

    public void onRefresh(View view) {
        String url = "https://pokeapi.co/api/v2/pokemon";

        JsonObjectRequest listRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray pokemonListJson = response.getJSONArray("results");

                    adapter.clear();
                    for (int i = 0; i < pokemonListJson.length(); i++) {
                        retrievePokemon(pokemonListJson.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        queue.add(listRequest);
    }

    private void retrievePokemon(final JSONObject pokemonData) throws JSONException {
        String url = pokemonData.getString("url");

        JsonObjectRequest pokemonRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parsePokemon(response);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        queue.add(pokemonRequest);
    }

    private void parsePokemon(JSONObject response) throws JSONException {
        Pokemon pokemon = new Pokemon();

        pokemon.setId(response.getInt("id"));
        pokemon.setName(response.getString("name"));
        pokemon.setHeight(response.getDouble("height") / 10.0);
        pokemon.setWeight(response.getDouble("weight") / 10.0);

        JSONObject spriteJson = response.getJSONObject("sprites");
        Sprite sprite = pokemon.getSprite();
        sprite.setBackDefault(spriteJson.getString("back_default"));
        sprite.setBackFemale(spriteJson.getString("back_female"));
        sprite.setBackShiny(spriteJson.getString("back_shiny"));
        sprite.setBackShinyFemale(spriteJson.getString("back_shiny_female"));
        sprite.setFrontDefault(spriteJson.getString("front_default"));
        sprite.setFrontFemale(spriteJson.getString("front_female"));
        sprite.setFrontShiny(spriteJson.getString("front_shiny"));
        sprite.setFrontShinyFemale(spriteJson.getString("front_shiny_female"));

        adapter.add(pokemon);
    }

    private void goToPokemon(Pokemon pokemon) {
        Intent intent = PokemonDetailsActivity.getIntent(this, pokemon);
        startActivity(intent);
    }
}
