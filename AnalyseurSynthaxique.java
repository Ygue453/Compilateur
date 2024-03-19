import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalyseurSynthaxique {
    private AnalyseurLexical.T_UNILEX UNILEX;
    private AnalyseurLexical al;

    /*private ArrayList<String> firstPROG = new ArrayList<>();
    private ArrayList<String> followsPROG = new ArrayList<>();

    private ArrayList<String> firstDECL_CONST = new ArrayList<>();
    private ArrayList<String> followsDECL_CONST = new ArrayList<>();

    private ArrayList<String> firstDECL_VAR = new ArrayList<>();
    private ArrayList<String> followsDECL_VAR = new ArrayList<>();

    private ArrayList<String> firstBLOC = new ArrayList<>();
    private ArrayList<String> followsBLOC = new ArrayList<>();

    private ArrayList<String> firstINSTRUCTION = new ArrayList<>();
    private ArrayList<String> followsINSTRUCTION = new ArrayList<>();

    private ArrayList<String> firstAFFECTATION = new ArrayList<>();
    private ArrayList<String> followsAFFECTATION = new ArrayList<>();
    
    private ArrayList<String> firstLECTURE = new ArrayList<>();
    private ArrayList<String> followsLECTURE = new ArrayList<>();

    private ArrayList<String> firstECRITURE = new ArrayList<>();
    private ArrayList<String> followsECRITURE = new ArrayList<>();

    private ArrayList<String> firstECR_EXP = new ArrayList<>();
    private ArrayList<String> followsECR_EXP = new ArrayList<>();

    private ArrayList<String> firstEXP = new ArrayList<>();
    private ArrayList<String> followsEXP = new ArrayList<>();

    private ArrayList<String> firstTERME = new ArrayList<>();
    private ArrayList<String> followsTERME = new ArrayList<>();

    private ArrayList<String> firstOP_BIN = new ArrayList<>();
    private ArrayList<String> followsOP_BIN = new ArrayList<>();*/

    public AnalyseurSynthaxique(AnalyseurLexical analyseurLexical){
        /*this.al = analyseurLexical;


        this.firstPROG.add("PROGRAMME");
        this.followsPROG.add(".");
        
        this.firstDECL_CONST.add("CONST");
        this.followsDECL_CONST.add(";");

        this.firstDECL_VAR.add("VAR");
        this.followsDECL_VAR.add(";");

        this.firstBLOC.add("DEBUT");
        this.followsBLOC.add("FIN");

        List<String> firstOfINSTRUCTION = List.of("IDENT", "LIRE", "ECRIRE", "DEBUT");
        this.firstINSTRUCTION.addAll(firstOfINSTRUCTION);
        List<String> followsOfINSTRUCTION = List.of("ENT", "IDENT", ")", "FIN");
        this.followsINSTRUCTION.addAll(followsOfINSTRUCTION);

        this.firstAFFECTATION.add("IDENT");
        List<String> followsOfAFFECTATION = List.of("ENT", "IDENT", ")");
        this.followsAFFECTATION.addAll(followsOfAFFECTATION);

        this.firstLECTURE.add("LIRE");
        this.followsLECTURE.add(")");

        this.firstECRITURE.add("ECRIRE");
        this.followsECRITURE.add(")");

        List<String> firstOfECR_EXP = List.of("ENT", "IDENT", "(", "-", "CH");
        this.firstECR_EXP.addAll(firstOfECR_EXP);
        List<String> followsOfECR_EXP = List.of("ENT", "IDENT", ")", "CH");
        this.followsECR_EXP.addAll(followsOfECR_EXP);*/
    }

    public void ANASYNT() throws IOException{
        UNILEX = al.ANALEX();
        if (PROG()){
            System.out.println("Le programme est juste synthaxicalement.");
        }
        else{
            System.out.println("erreur syntaxique");
        }
    }

    private boolean PROG() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("PROGRAMME") == 0){
            if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ident){
                if (al.tab.CHERCHER(al.CHAINE) == -1){
                    al.tab.INSERER(al.CHAINE, new T_IDENT(al.CHAINE));
                    if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ptvirg){
                        UNILEX = al.ANALEX();
                        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle){
                            while(al.CHAINE.compareTo("CONST") == 0){
                                if (!DECL_CONST()){ return false; }
                                UNILEX = al.ANALEX();
                            }
                            while(al.CHAINE.compareTo("VAR") == 0){
                                if (!DECL_VAR()){ return false; }
                                UNILEX = al.ANALEX();
                            }
                            if (BLOC()){
                                UNILEX = al.ANALEX();
                                if (UNILEX == AnalyseurLexical.T_UNILEX.motcle){ return true; }
                            }
                        }
                        System.out.println("Erreur synthaxique, ligne + " + al.NUM_LIGNE + ", CONST, VAR ou BLOC attendu");
                        return false;
                    }
                    System.out.println("Erreur syntaxique, ligne : " + al.NUM_LIGNE + ", PTVIRG attendu");
                    return false;
                }
                System.out.println("TODO : erreur identificateur déjà existant");
                return false;
            }
            System.out.println("Erreur syntaxique, ligne : " + al.NUM_LIGNE + ", IDENT attendu");
            return false;
        }
        System.out.println("Erreur syntaxique, ligne : " + al.NUM_LIGNE + ", MOTCLE attendu");
        return false;
    }

    private boolean DECL_CONST() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("CONST") == 0){

        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + "CONST attendu");
        return false;
    }
}
