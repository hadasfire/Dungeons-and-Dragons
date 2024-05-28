package game.tiles.units.enemies;

import game.tiles.units.Unit;
import game.tiles.units.players.Player;

public class Trap extends Enemy {
    public int visibilityTime;
    public int invisibilityTime;
    public int ticksCount;
    public boolean visible;
    public Trap(char enemyTile, String name, int healthCapacity, int attack, int defense, int visibilityTime, int invisibilityTime, int experience){
        super(enemyTile, name, healthCapacity, attack, defense, experience);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        this.ticksCount=0;
        this.visible=true;
    }
    public void gameTick(Player p){
        visible=(ticksCount<visibilityTime);
        if(ticksCount==visibilityTime+invisibilityTime)
            ticksCount=0;
        else{
            ticksCount=ticksCount+1;
        }
        if(p.getPosition().range(this.position)<2) {
            this.battle(p);
        }
    }
    public void accept(Player player){
        player.battle(this);
        if(!this.isAlive()){
            this.onDeath(player);
            player.initialize(this.getPosition());
            player.getPosition().initialize();
        }
    }
    public void accept(Enemy enemy){
        //nothing happens
    }
    public void onDeath(Unit u){
        super.onDeath(u);
    }
    public void onKill(Player player){
        super.onKill(player);
    }
    public void onKill(Enemy enemy){
        //nothing happens
    }
    public Trap copy(){
        return new Trap(this.tile, this.name, this.health.getPool(), this.attack, this.defense, this.visibilityTime, this.invisibilityTime, this.getExperience());
    }
}
