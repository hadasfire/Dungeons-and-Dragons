package game.tiles.units;

import game.LevelManager;
import game.tiles.Tile;
import game.tiles.units.players.Player;
import game.utils.Position;
import game.callBacks.MessageCallBack;
import game.tiles.units.enemies.Enemy;
import game.tiles.Empty;
import game.utils.Resource;
import java.util.Random;

public abstract class Unit extends Tile {
    public static LevelManager manager;
    protected static Random r=new Random();
    public static MessageCallBack messageCallBack;
    protected String name;
    protected Resource health;
    protected int attack;
    protected int defense;
    public boolean isAlive;
    public String getName(){
        return name;
    }
    public Resource getHealth(){
        return health;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }
    public static void setMessageCallBack(MessageCallBack m){
        messageCallBack=m;
    }
    public boolean isAlive(){
        return this.isAlive;
    }
    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name=name;
        this.health=new Resource(healthCapacity, healthCapacity);
        this.attack=attack;
        this.defense=defense;
        this.isAlive=true;
    }
    public void initialize(Position position, MessageCallBack messageCallback){
        super.initialize(position);
        this.messageCallBack=messageCallback;
    }
    protected int attack(){
		int result=r.nextInt(attack);
        messageCallBack.send(String.format("%s rolled %d attack points.", getName(), result));
        return result;
    }
    public int defend(){
        int result=r.nextInt(defense);
        messageCallBack.send(String.format("%s rolled %d defend points.", getName(), result));
        return result;
    }
    public abstract void onDeath(Unit u);
    public abstract void accept(Player p);
    public abstract void accept(Enemy e);
    public abstract void onKill(Enemy e);
    public abstract void onKill(Player p);
    public abstract void gameTick(Player player);
    public abstract Unit copy();
    protected void battle(Unit u){
        //u- defender, this-attacker
        messageCallBack.send(String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().getAmount(), getAttack(), getDefense()));
        int damageDone=Math.max(attack()-u.defend(), 0);
        boolean ifSurvived=u.getHealth().reduceAmount(damageDone);
        if(!ifSurvived) {
            u.onDeath(this);
        }
        messageCallBack.send(String.format("%s dealt %d damage to %s.", getName(), damageDone, u.getName()));
    }
    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().getAmount(), getAttack(), getDefense());
    }
}
