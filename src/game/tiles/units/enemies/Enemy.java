package game.tiles.units.enemies;

import game.tiles.units.Unit;
import game.tiles.units.players.Player;

public abstract class Enemy extends Unit {
    private int experience;
    public Enemy(char enemyTile, String name, int healthCapacity, int attack, int defense, int experience){
        super(enemyTile, name, healthCapacity, attack, defense);
        this.experience=experience;
    }
    public int getExperience(){
        return experience;
    }
    public void onDeath(Unit u){
        this.isAlive=false;
        u.onKill(this);
    }
    public void onKill(Enemy enemy){
        //nothing happens
    }
    public void onKill(Player player){
        //nothing happens
    }
    public void battle(Unit u){
        super.battle(u);
    }
    public abstract void accept(Player p);
    public abstract void accept(Enemy e);
}
