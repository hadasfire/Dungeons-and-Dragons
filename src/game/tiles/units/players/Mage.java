package game.tiles.units.players;

import game.tiles.actions.UnitsInRange;
import game.utils.Resource;
import game.tiles.units.enemies.Enemy;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    private Resource mana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;
    public final String specialAbility="Blizzard";
    public Mage(String name, int healthCapacity, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange){
        super(name, healthCapacity, attack, defense);
        this.mana=new Resource(manaPool, manaPool/4);
        this.manaCost=manaCost;
        this.spellPower=spellPower;
        this.hitsCount=hitsCount;
        this.abilityRange=abilityRange;
    }
    protected void levelUp(){
        super.levelUp();
        mana.addCapacity(25*level);
        mana.increaseAmount(mana.getPool()/4);
        spellPower=spellPower+(10*level);
    }
    public void gameTick(Player player){
        mana.increaseAmount(level);
        super.gameTick(player);
    }
    public void castSpecialAbility(){
        if(this.mana.getAmount()<manaCost){
            messageCallBack.send("You do not have enough resources to cast this special ability");
        }
        else {
            messageCallBack.send(specialAbility+"\n");
            mana.reduceAmount(manaCost);
            int hits = 0;
            List<Enemy> enemyList = UnitsInRange.enemiesInRange(this, abilityRange);
            while (hits < hitsCount && enemyList.size() > 0) {
                Random r = new Random();
                int index = r.nextInt(enemyList.size());
                Enemy battleEnemy = enemyList.get(index);
                int defense = battleEnemy.defend();
                int attack = spellPower;
                if ((attack - defense) > 0) {
                    battleEnemy.getHealth().setAmount(spellPower);
                } else
                    this.onKill(battleEnemy);
                hits = hits + 1;
            }
        }
    }
    public String describe(){
        return super.describe() + "\t\tMana: " + this.mana.getAmount() + "/" + this.mana.getPool() + "\t\tspellPower: " + this.spellPower;
    }
    public Mage copy(){
        return new Mage(this.getName(), this.getHealth().getAmount(), this.getAttack(), this.getDefense(), this.mana.getPool(), this.manaCost, this.spellPower,this.hitsCount,this.abilityRange);
    }
}
