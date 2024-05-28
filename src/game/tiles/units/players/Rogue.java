package game.tiles.units.players;

import game.tiles.actions.UnitsInRange;
import game.tiles.units.enemies.Enemy;
import game.utils.Resource;
import java.util.List;

public class Rogue extends Player {
    private int cost;
    private Resource currEnergy;
    private final int abilityRange=2;
    private final int maxEnergy=100;
    private final String specialAbility="Fan Of Knives";
    public Rogue(String name, int healthCapacity, int attack, int defense, int cost){
        super(name, healthCapacity, attack, defense);
        this.cost=cost;
        this.currEnergy=new Resource(100, 100);
    }
    protected void levelUp(){
        super.levelUp();
        this.currEnergy.restore();
        attack=attack+(3*level);
    }
    public void gameTick(Player player){
        currEnergy.increaseAmount(10);
        super.gameTick(player);
    }
    public void castSpecialAbility(){
        if(this.currEnergy.getAmount()<this.cost){
            messageCallBack.send("You do not have enough resources to cast this special ability");
        }
        else {
            messageCallBack.send(specialAbility+"\n");
            currEnergy.reduceAmount(cost);
            List<Enemy> enemyList = UnitsInRange.enemiesInRange(this, abilityRange);
            for (Enemy enemy : enemyList) {
                int defense = enemy.defend();
                int attack = this.attack;
                if ((attack - defense) > 0) {
                    enemy.getHealth().reduceAmount(attack - defense);
                } else
                    this.onKill(enemy);
            }
        }
    }
    public String describe() {
        return super.describe()+"\t\tEnergy: "+currEnergy.getAmount()+"/"+maxEnergy;
    }
    public Rogue copy(){
        return new Rogue(this.name, this.health.getPool(), this.attack, this.defense, this.cost);
    }
}
