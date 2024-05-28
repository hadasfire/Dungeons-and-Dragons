package game.tiles.actions;

import game.UnitDB;
import view.input.InputProvider;
import java.util.Scanner;

public class InputHelper {
    private static String regexUserInput="["+ InputProvider.CastSpecialAbility.getRegex()+"]";
    private static String regexMenu="[1-"+ UnitDB.playerPool.size()+"]";
    public static InputProvider playerGameInput(){
        return InputProvider.findByKey(inputCatch(regexUserInput)+"");
    }
    public static char menuInput(){
        return inputCatch(regexMenu);
    }
    private static char inputCatch(String regex){
        Scanner obj=new Scanner(System.in);
        String input="";
        do{
            input=obj.nextLine();
        }
        while(!validateWithRegex(input, regex));
        return input.charAt(0);
    }
    private static boolean validateWithRegex(String s, String regex){
        if(s.length()==1){
            if(s.matches(regex))
                return true;
        }
        return false;
    }
}
