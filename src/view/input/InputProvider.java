package view.input;

public enum InputProvider {
    Up("w"),
    Down("s"),
    Left("a"),
    Right("d"),
    CastSpecialAbility("e"),
    Nothing("q");
    private String key;
   InputProvider(String key){
       this.key=key;
   }
   public String getKey(){
       return this.key;
   }
   public String getRegex(){
       String regex="";
       for(InputProvider in:InputProvider.values()){
           regex=regex+in.getKey();
       }
       return regex;
   }
   public static InputProvider findByKey(String key){
       for(InputProvider v: values()){
           if(v.key.equals(key)){
               return v;
           }
       }
       return null;
   }
}
