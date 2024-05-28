package game.tiles.actions;

import game.*;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import java.util.ArrayList;
import java.util.List;

public class UnitsInRange {
    public static Board gameBoard;
    public static List<Enemy> enemiesInRange(Player player, int range){
        List<Enemy> output=new ArrayList<>();
        for(Enemy enemy:gameBoard.getEnemies()){
            if(player.getPosition().inRange(enemy.getPosition(), range))
                output.add(enemy);
        }
        return output;
    }
}
