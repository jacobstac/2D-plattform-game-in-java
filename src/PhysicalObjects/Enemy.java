package PhysicalObjects;


import MapStuff.TileMap;

/**
 * Created by Jacob on 2017-05-04.
 * Detta är Fiende-basklassen
 * Alla fiender kommer att härstamma från denna klass
 */
public abstract class Enemy extends MapObject {


    protected int health;
    protected int maxHealth;
    protected boolean dead;
    //Hur mycket skada de gör på kontakt. om man springer in i fiende
    protected int damage;

    protected boolean flinching;
    protected long flinchTimer;



    public Enemy(TileMap tm) {

        super(tm);

    }


    public boolean isDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

    // hur mycket fienden blir träffad av saker från spelaren
    public void hit(int damage) {
        if(dead || flinching) return;
        //annars: ta bort hälsa med damage =
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;

        //aannars:
        flinching = true;
        flinchTimer = System.nanoTime();



    }

    public void update() {

    }



}

//När denna klass är klar (nu) så kan vi lägga alla fienderna i en behållare i Level1State
