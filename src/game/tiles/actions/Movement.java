package game.tiles.actions;

import game.Board;
import game.callBacks.MessageCallBack;
import game.utils.Position;
import view.input.InputProvider;
import game.tiles.*;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.*;
public class Movement {
    public static Board gameBoard;
    public static MessageCallBack messageCallBack;
    public static Tile movePlayer(InputProvider inputProvider, Player player){
        Position newPos=getNewPosition(inputProvider, player.getPosition());
        newPos.initialize();
        return gameBoard.getTile(newPos);
    }
    public static Tile moveEnemy(InputProvider inputProvider, Enemy enemy){
        Position newPos=getNewPosition(inputProvider, enemy.getPosition());
        newPos.initialize();
        return gameBoard.getTile(newPos);
    }
    public static Position getNewPosition(InputProvider inputProvider, Position position){
        Position pos=new Position(position.getX(), position.getY());
        pos.initialize();
        switch (inputProvider) {
            case Up -> pos=pos.getUp();
            case Down -> pos=pos.getDown();
            case Left -> pos=pos.getLeft();
            case Right -> pos=pos.getRight();
        }
        if(!validMove(pos)){
            return position;
        }
        return pos;
    }
    public static boolean validMove(Position position){
        if(position.getX()<0 | position.getX()> gameBoard.width | position.getY()<0 | position.getY()> gameBoard.height)
            return false;
        else if(gameBoard.walls.containsKey(position))
            return false;
        return true;
    }
}
