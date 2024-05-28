package game;

import game.tiles.units.*;
import java.util.Map;
import java.util.HashMap;
import game.tiles.units.players.*;
import game.tiles.units.enemies.*;

public class UnitDB {
    public static Map<String, Unit> playerPool = new HashMap<>();
    public static Map<String, Unit> enemyPool = new HashMap<>();
    public UnitDB() {
        Player JhonSnow=new Warrior("Jhon Snow",300, 30, 4, 3);
        playerPool.put("1", JhonSnow);
        Player TheHound=new Warrior("The Hound",40, 20, 6, 5);
        playerPool.put("2", TheHound);
        Player Melisandre=new Mage("Melisandre",100, 5, 1, 300, 30, 15, 5, 6);
        playerPool.put("3", Melisandre);
        Player ThorosOfMyr=new Mage("Thoros Of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
        playerPool.put("4", ThorosOfMyr);
        Player AryaStark=new Rogue("Arya Stark", 150, 40, 2, 20);
        playerPool.put("5", AryaStark);
        Player Bronn=new Rogue("Bronn", 250, 35, 3, 50);
        playerPool.put("6", Bronn);
        Enemy LannisterSolider=new Monster('s',"Lannister Solider", 80, 8, 3, 3, 25);
        enemyPool.put("s", LannisterSolider);
        Enemy LannisterKnight=new Monster('k', "Lannister Knight", 200, 14, 8, 4, 50);
        enemyPool.put("k", LannisterKnight);
        Enemy QueensGuard=new Monster('q'," Queen's Guard", 400, 20, 15, 5, 100);
        enemyPool.put("q", QueensGuard);
        Enemy Wright=new Monster('z', "Wright", 600, 30, 15, 3, 100);
        enemyPool.put("z", Wright);
        Enemy BearWright=new Monster('b', "Bear-Wright", 1000, 75, 30, 4, 250);
        enemyPool.put("b", BearWright);
        Enemy GiantWright=new Monster('g', "Giant-Wright", 1500, 100, 40, 5, 500);
        enemyPool.put("g", GiantWright);
        Enemy WhiteWalker=new Monster('w', "White Walker", 2000, 150, 50, 6, 1000);
        enemyPool.put("w", WhiteWalker);
        Enemy TheMountain=new Monster('M', "The Mountain", 1000, 60, 25, 6, 500);
        enemyPool.put("M", TheMountain);
        Enemy QueenCersei=new Monster('C', "Queen Cersei", 100, 10, 10, 1, 1000);
        enemyPool.put("C", QueenCersei);
        Enemy NightsKing=new Monster('K', "Night's King", 5000, 300, 150, 8, 5000);
        enemyPool.put("K", NightsKing);
        Enemy BonusTrap=new Trap('B', "Bonus Trap", 1, 1, 1, 1, 5, 250);
        enemyPool.put("B", BonusTrap);
        Enemy QueensTrap=new Trap('Q', "Queen's Trap", 250, 50, 10, 3, 7, 100);
        enemyPool.put("Q", QueensTrap);
        Enemy DeathTrap=new Trap('D', "Death Trap", 500, 100, 20, 1, 10, 250);
        enemyPool.put("D", DeathTrap);
    }
}
