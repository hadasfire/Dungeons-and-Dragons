package game.tiles.units.players;

import game.tiles.actions.UnitsInRange;
import game.tiles.units.enemies.Enemy;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private int abCoolDown;
    private int remCoolDown;
    private final int abilityRange=3;
    public final String specialAbility="Avenger's Shield";
    public Warrior(String name, int healthCapacity, int attack, int defense, int abCoolDown){
        super(name, healthCapacity, attack, defense);
        this.abCoolDown=abCoolDown;
        this.remCoolDown=0;
    }
    protected void levelUp(){
        super.levelUp();
        remCoolDown=0;
        health.increaseAmount(5*level);
        attack=attack+(2*level);
        defense=defense+level;
    }
    public void gameTick(Player player){
        remCoolDown=remCoolDown-1;
        super.gameTick(player);
    }
    public void castSpecialAbility(){
        if(remCoolDown>0)
            messageCallBack.send("You do not have enough resources to cast this special ability");
        else{
            messageCallBack.send(specialAbility+"\n");
            remCoolDown=abCoolDown;
            health.increaseAmount(10*defense);
            List<Enemy> enemyList= UnitsInRange.enemiesInRange(this, abilityRange);
            if(enemyList.size()>0) {
                Random r = new Random();
                int index = r.nextInt(enemyList.size());
                Enemy battleEnemy = enemyList.get(index);
                int defense = battleEnemy.defend();
                int attack = this.getHealth().getPool() / 10;
                if ((attack - defense) > 0) {
                    battleEnemy.getHealth().reduceAmount(attack - defense);
                } else
                    this.onKill(battleEnemy);
            }
        }
    }
    public String describe() {
        return super.describe() + "\t\tCooldown: " + remCoolDown + "/" + abCoolDown;
    }
    public Warrior copy(){
        return new Warrior(this.name, this.health.getPool(), this.attack, this.defense, this.abCoolDown);
    }
}
