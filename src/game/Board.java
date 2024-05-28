package game;

import java.util.List;
import java.util.ArrayList;
import game.tiles.Empty;
import game.tiles.units.players.Player;
import game.utils.Position;
import game.tiles.Tile;
import game.tiles.Wall;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;

public class Board {
    public int height;
    public int width;
    private Player player;
    private List<Enemy> enemies;
    private String[][] board;
    public HashMap<Position, Wall> walls;
    public Board(){
        this.enemies=new ArrayList<Enemy>();
        this.walls=new HashMap<Position, Wall>();
    }
    public Player getPlayer(){
        return this.player;
    }
    public void setPlayer(Player player){
        this.player=player;
    }
    public String[][] getBoard(){
        return this.board;
    }
    public List<Enemy> getEnemies(){
        return this.enemies;
    }
    public Tile getTile(Position p){
        if(player.getPosition().compareTo(p)==0)
            return player;
        for (Enemy e:enemies) {
            if(e.getPosition().compareTo(p)==0)
                return e;
        }
        for (var w : walls.entrySet()) {
            if (w.getKey().compareTo(p)==0)
                return w.getValue();
        }
        Empty empty=new Empty();
        empty.initialize(p);
        return empty;
    }
    public void buildBoard(File file){
        walls.clear();
        try{
            Scanner mapReader=new Scanner(file);
            int y=0;
            int x=0;
            Position position=new Position(x,y);
            position.initialize();
            Unit enemy;
            String data;
            while(mapReader.hasNextLine()){
                data=mapReader.nextLine();
                x=0;
                for(char ch:data.toCharArray()){
                    if(ch=='@'){
                        position=new Position(x,y);
                        position.initialize();
                        player.initialize(position);
                    }
                    if(ch=='#'){
                        position=new Position(x,y);
                        position.initialize();
                        walls.put(position, new Wall(position));
                    }
                    if(UnitDB.enemyPool.containsKey(ch+"")){
                        enemy=UnitDB.enemyPool.get(ch+"").copy();
                        position=new Position(x,y);
                        position.initialize();
                        enemy.initialize(position);
                        enemies.add((Enemy) enemy);
                    }
                    x=x+1;
                }
                y=y+1;
            }
            this.width=x;
            this.height=y;
            this.board=new String[y][x];
        }
        catch (Exception e){
            throw new IllegalArgumentException("Failed to load board");
        }
    }
    public void buildArray() {
        for (int y = 0; y < height; y=y+1) {
            for (int x = 0; x < width; x = x + 1) {
                board[y][x] = ".";
            }
        }
        board[player.getPosition().getY()][player.getPosition().getX()] = player.toString();
        for (Unit enemy : this.enemies) {
            board[enemy.getPosition().getY()][enemy.getPosition().getX()] = enemy.toString();
        }
        for (Position wallPosition : this.walls.keySet()) {
            board[wallPosition.getY()][wallPosition.getX()] = this.walls.get(wallPosition).toString();
        }
    }
    public void remove(Enemy e) {
        enemies.remove(e);
    }
    public String arrayToString() {
        StringBuilder currBoard = new StringBuilder();
        for (String[] line : board) {
            for (String block : line) {
                currBoard.append(block);
            }
            currBoard.append("\n");
        }
        return currBoard.toString();
    }
    public String toString() {
        buildArray();
        return arrayToString();
    }
}