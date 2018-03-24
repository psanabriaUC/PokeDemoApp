package cl.puc.ing.pokedemo.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private int id;
    private String name;
    private double height;
    private double weight;
    private Sprite sprite;

    public Pokemon() {
        id = 0;
        name = "";
        height = 0;
        weight = 0;
        sprite = new Sprite();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @NonNull
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(@NonNull Sprite sprite) {
        this.sprite = sprite;
    }
}
