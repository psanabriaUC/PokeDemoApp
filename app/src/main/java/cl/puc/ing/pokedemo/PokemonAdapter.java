package cl.puc.ing.pokedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cl.puc.ing.pokedemo.model.Pokemon;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {
    public PokemonAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pokemon_item_layout, parent, false);
        }

        ImageView pokemonSpriteImageView = view.findViewById(R.id.pokemon_sprite_image_view);
        TextView pokemonNameImageView = view.findViewById(R.id.pokemon_name_text_view);
        Pokemon pokemon = getItem(position);

        pokemonNameImageView.setText(pokemon.getName());
        Glide.with(getContext()).load(pokemon.getSprite().getFrontDefault()).into(pokemonSpriteImageView);

        return view;
    }
}
