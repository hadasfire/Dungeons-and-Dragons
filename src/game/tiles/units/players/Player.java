package game.tiles.units.players;

import game.tiles.Tile;
import game.tiles.actions.InputHelper;
import game.tiles.actions.Movement;
import game.tiles.units.Unit;
import view.input.InputProvider;
import game.tiles.units.enemies.Enemy;

public abstract class Player extends Unit {
    public static final char playerTile='@';
    protected int experience;
    protected static final int expReq=50;
    protected static final int attackBonus=4;
    protected static final int defenseBonus=1;
    protected static final int healthBonus=10;
    protected int level;
    protected Player(String name, int healthCapacity, int attack, int defense){
        super(playerTile, name, healthCapacity, attack, defense);
        this.level=1;
        this.experience=0;
        this.isAlive=true;
    }
    public void accept(Enemy e){
        e.battle(this);
        if (!this.isAlive()){
            this.onDeath(e);
        }
    }
    public void accept(Player player) {
        //only one player!
    }
    public void gameTick(Player player){
        InputProvider input= InputHelper.playerGameInput();
        if(input==InputProvider.CastSpecialAbility){
            this.castSpecialAbility();
        }
        else{
            Tile newPos=Movement.movePlayer(input, this);
            newPos.accept(this);
        }
    }
    public void battle(Unit u){
        super.battle(u);
    }
    public void onDeath(Unit e){
        e.onKill(this);
        super.isAlive=false;
        toString();
    }
    public void onKill(Enemy e){
        int experienceGained=e.getExperience();
        messageCallBack.send(String.format("%s died. %s gained %d experience.", e.getName(), getName(), experienceGained));
        addExperience(experienceGained);
    }
    public void onKill(Player p){
        //does not happen
    }
    protected void addExperience(int experienceGained){
        this.experience=this.experience+experienceGained;
        int nextLevelReq=levelUpRequirement();
        while (experience>=nextLevelReq){
            levelUp();
            experience=experience-nextLevelReq;
            nextLevelReq=levelUpRequirement();
        }
    }
    protected void levelUp(){
        level=level+1;
        int healthGained=gainHealth();
        int attackGained=gainAttack();
        int defenseGained=gainDefense();
        health.addCapacity(healthGained);
        health.restore();
        attack=attack+attackGained;
        defense=defense+defenseGained;
        messageCallBack.send(String.format("%s reached level %d: +%d health, +%d attack, +%d defense", getName(), getLevel(), healthGained, attackGained, defenseGained));
    }
    public String toString(){
        return isAlive() ? super.toString() : "X";
    }
    protected int gainHealth(){
        return level*healthBonus;
    }
    protected int gainAttack(){
        return level*attackBonus;
    }
    protected int gainDefense(){
        return level*defenseBonus;
    }
    private int levelUpRequirement(){
        return expReq*level;
    }
    public int getLevel(){
        return level;
    }
    public int getExperience(){
        return experience;
    }
    public String describe(){
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), getLevel(), getExperience(), levelUpRequirement());
    }
    public abstract void castSpecialAbility();
}
