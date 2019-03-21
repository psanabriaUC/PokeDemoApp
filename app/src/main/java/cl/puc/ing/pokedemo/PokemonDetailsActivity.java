package cl.puc.ing.pokedemo;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cl.puc.ing.pokedemo.model.Pokemon;

public class PokemonDetailsActivity extends AppCompatActivity {
    public static final String POKEMON_DATA = "POKEMON_DATA";
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        ImageView pokemonSpriteImageView = findViewById(R.id.pokemon_sprite_image_view);
        TextView pokemonNameTextView = findViewById(R.id.pokemon_name_text_view);
        TextView pokemonWeightTextView = findViewById(R.id.pokemon_weight_view);
        TextView pokemonHeightTextView = findViewById(R.id.pokemon_height_view);

        if (savedInstanceState == null)
            pokemon = (Pokemon)getIntent().getSerializableExtra(POKEMON_DATA);
        else
            pokemon = (Pokemon)savedInstanceState.getSerializable(POKEMON_DATA);

        if (pokemon != null) {
            pokemonNameTextView.setText(pokemon.getName());
            pokemonHeightTextView.setText(getString(R.string.height, pokemon.getHeight()));
            pokemonWeightTextView.setText(getString(R.string.weight, pokemon.getWeight()));
            Glide.with(this).load(pokemon.getSprite().getFrontDefault()).into(pokemonSpriteImageView);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(POKEMON_DATA, pokemon);
        super.onSaveInstanceState(outState);
    }

    public static Intent getIntent(Context context, Pokemon pokemon) {
        Intent intent = new Intent(context, PokemonDetailsActivity.class);

        intent.putExtra(POKEMON_DATA, pokemon);
        return intent;
    }
}
