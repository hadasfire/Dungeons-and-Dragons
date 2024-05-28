package game;

import game.callBacks.MessageCallBack;
import game.tiles.actions.Movement;
import game.tiles.units.enemies.*;
import game.tiles.actions.*;
import game.tiles.units.players.*;
import java.io.File;
import java.util.*;
import game.tiles.units.*;
import java.io.FilenameFilter;

public class LevelManager {
    private Board board;
    private int countTicks;
    private MessageCallBack messageCallBack;
    private List<File> filesOfLevels;
    private List<Unit> unitList;
    public LevelManager(MessageCallBack m){
        this.board=new Board();
        Movement.gameBoard=this.board;
        this.countTicks=0;
        this.messageCallBack = m;
        UnitDB db=new UnitDB();
        UnitsInRange.gameBoard=this.board;
        Movement.gameBoard=this.board;
        Movement.messageCallBack = m;
        Unit.manager=this;
        Unit.setMessageCallBack(m);
        unitList=new ArrayList<>();
    }
    public Board getBoard(){
        return this.board;
    }
    public void start(String address) {
        instruction();
        getPlayerMenu();
        createListOfLevel(address);
        for (File level : filesOfLevels) {
            Player player = board.getPlayer();
            player.initialize(player.getPosition(), this.messageCallBack);
            if (!player.isAlive())
                break;
            loadLevel(level);
            startLevel();
        }
        if (!board.getPlayer().isAlive())
            this.messageCallBack.send("Game Over.");
        else
            this.messageCallBack.send("You Won.");
    }
    public void loadLevel(File file){
        board.buildBoard(file);
        unitList.clear();
        unitList.add(board.getPlayer());
        for(Unit enemy:board.getEnemies()){
            unitList.add(enemy);
        }
    }
    public void gameTick(){
        ListIterator<Unit> iter = unitList.listIterator();
        while(iter.hasNext() && board.getPlayer().isAlive()){
            Unit unit= iter.next();
            if(!unit.isAlive()){
                Enemy enemy = (Enemy) unit;
                iter.remove();
                board.remove(enemy);
                continue;
            }
            unit.gameTick(board.getPlayer());
        }
    }
    public void createListOfLevel(String address){
        File f = new File(address);
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return  name.endsWith("txt");
            }
        });
        assert matchingFiles != null;
        filesOfLevels= Arrays.asList(matchingFiles);
        filesOfLevels.sort((File f1,File f2)->f1.getName().compareTo(f2.getName()));
    }
    public void instruction(){
        messageCallBack.send("*** Game instructions:\n");
        messageCallBack.send("* Game Controls:\n");
        messageCallBack.send(
                "-Move up:\tW\n" +
                        "-Move down:\tS\n" +
                        "-Move right:\tD\n" +
                        "-Move left:\tA\n" +
                        "-Wait:\tQ\n" +
                        "-Attack: Steping on an enemy\n" +
                        "-Cast special Attack:\tE\n");
        messageCallBack.send("* Map description:\n");
        messageCallBack.send("-(.):\t Free space\n" +
                "-(#):\t Wall\n" +
                "-(@):\t Your player\n");
        messageCallBack.send("* Enemies list:\n");
        messageCallBack.send("^ Monsters:\n"+
                "-(s):\t Lannister Solider\n" +
                "-(k):\t Lannister Knight\n" +
                "-(q):\t Queen’s Guard\n" +
                "-(z):\t Wright\n" +
                "-(b):\t Bear-Wright\n" +
                "-(g):\t Giant-Wright\n" +
                "-(w):\t White Walker\n" +
                "-(M):\t The Mountain\n" +
                "-(C):\t Queen Cersei\n" +
                "-(K):\t Night’s King\n"+
                "^ Traps:\n" +
                "-(B):\t Bonus Trap\n" +
                "-(Q):\t Queen’s Trap\n" +
                "-(D):\t Death Trap\n");
    }
    private void startLevel() {
        countTicks = 0;
        while (board.getPlayer().isAlive() && board.getEnemies().size() != 0) {
            printLevel();
            countTicks =countTicks+ 1;
            gameTick();
        }
        if (!board.getPlayer().isAlive())
            messageCallBack.send("You Lost");
        printLevel();
    }
    private void getPlayerMenu(){
        printMenu();
        String choose= String.valueOf(InputHelper.menuInput());
        board.setPlayer((Player) UnitDB.playerPool.get(choose).copy());
        printChosenPlayer();
    }
    public void printLevel() {
        if (board.getEnemies().size() > 0)
            messageCallBack.send(board.toString());
        messageCallBack.send(board.getPlayer().describe());
    }
    public void printMenu(){
        messageCallBack.send("Select player:");
        for(Map.Entry<String, Unit> player : UnitDB.playerPool.entrySet())
            messageCallBack.send(player.getKey()+"."+player.getValue().describe());
    }
    public void printChosenPlayer(){
        Player player = board.getPlayer();
        messageCallBack.send(String.format("You have selected: %s", player.getName()));
    }
}
