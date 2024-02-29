import java.util.HashMap;
import java.util.Map;

public class T_TAB {
    private static HashMap<String, T_IDENT> hashMap = new HashMap<>();

    public int CHERCHER(String name){
        if (hashMap.containsKey(name)){
            int index = 0;
            for (String string : hashMap.keySet()){
                if (string.compareTo(name) == 0){
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public int inserer(String name, T_IDENT ident){
        hashMap.put(name, ident);
        return CHERCHER(name);
    }

    public void AFFICHE_TABLE_IDENT(){
        for (Map.Entry entry : hashMap.entrySet()){
            System.out.println(entry.getKey() + " ident : " + entry.getValue());
        }
    }
}
