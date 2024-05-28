import game.LevelManager;

public class Main {
    public static void main(String[] args) {
        LevelManager manager = new LevelManager(s -> System.out.println(s));
        manager.start(args[0]);
    }
}