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
            System.out.println("erreur n°3: erreur syntaxique");
            //ERREUR(3);
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
                System.out.println("Erreur sémentique, ligne : " + al.NUM_LIGNE + ", identificateur déjà existant");
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
            UNILEX = al.ANALEX();
            if (!CONST()){
                return false;
            }
            while ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.virg) {
                if (!CONST()){
                    return false;
                }
            }
            if (UNILEX == AnalyseurLexical.T_UNILEX.ptvirg){
                return true;
            }
            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", ; attendu");
            return false;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", CONST attendu");
        return false;
    }

    private boolean CONST() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.ident){
            if (al.tab.CHERCHER(al.CHAINE) == -1){
                al.tab.INSERER(al.CHAINE, new T_CONST(al.CHAINE));
                if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.eg){
                    UNILEX = al.ANALEX();
                    if (UNILEX == AnalyseurLexical.T_UNILEX.ent || UNILEX == AnalyseurLexical.T_UNILEX.ch){
                        return true;
                    }
                    System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", ENT ou CH attendu");
                    return false;
                }
                System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", = attendu");
                return false;
            }
            System.out.println("Erreur sémentique, ligne : " + al.NUM_LIGNE + ", constante déjà éxistante");
            return false;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT attendu");
        return false;
    }

    private boolean DECL_VAR() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("VAR") == 0){
            if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ident){
                if (al.tab.CHERCHER(al.CHAINE) == -1){
                    al.tab.INSERER(al.CHAINE, new T_VAR(al.CHAINE));
                    while ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.virg){
                        if (!((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ident)){
                            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT attendu");
                            return false;
                        }
                    }
                    if (UNILEX == AnalyseurLexical.T_UNILEX.ptvirg){
                        return true;
                    }
                    System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", PTVIRG attendu");
                    return false;
                }
                System.out.println("Erreur sémantique, ligne : " + al.NUM_LIGNE + ", variable déjà éxistante");
                return false;
            }
            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT attendu");
            return false;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", VAR attendu");
        return false;
    }

    private boolean BLOC() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("DEBUT") == 0){
            UNILEX = al.ANALEX();
            if (!INSTRUCTION()){
                return false;
            }
            if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ptvirg){
                UNILEX = al.ANALEX();
                while (INSTRUCTION()) {
                    UNILEX = al.ANALEX();
                    if (!((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ptvirg)){
                        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", ; attendu");
                        return false;
                    }
                    UNILEX = al.ANALEX();
                }
            }
            if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("FIN") == 0){
                return true;
            }
            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", FIN attendu");
            return false;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", DEBUT attendu");
        return false;
    }

    private boolean INSTRUCTION() throws IOException{
        UNILEX = al.ANALEX();
        if (UNILEX == AnalyseurLexical.T_UNILEX.ident){
            if (AFFECTATION()){
                return true;
            }
            return false;
        }
        else if (UNILEX == AnalyseurLexical.T_UNILEX.motcle){
            if (al.CHAINE.compareTo("LIRE") == 0){
                return LECTURE();
                /*if (LECTURE()){
                    return true;
                }
                return false;*/
            }
            else if (al.CHAINE.compareTo("ECIRE") == 0){
                return ECRITURE();
                /*if (ECRITURE()){
                    return true;
                }
                return false;*/
            }
            else if (al.CHAINE.compareTo("DEBUT") == 0){
                return BLOC();
                /*if (BLOC()){
                    return true;
                }
                return false;*/
            }
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT, LIRE, ECRIRE ou DEBUT attendu");
        return false;
    }

    private boolean AFFECTATION() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.ident){
            if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.aff){
                UNILEX = al.ANALEX();
                return EXP();
                /*if (EXP()){
                    return true;
                }
                return false;*/
            }
            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", = attendu");
            return false;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT attendu");
        return false;
    }

    private boolean EXP() throws IOException{
        if (TERME()){
            UNILEX = al.ANALEX();
            if (SUITE_TERME()){
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean TERME() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.ent || UNILEX == AnalyseurLexical.T_UNILEX.ident){
            return true;
        }
        else if (UNILEX == AnalyseurLexical.T_UNILEX.parouv){
            UNILEX = al.ANALEX();
            if (EXP()){
                UNILEX = al.ANALEX();
                if (UNILEX == AnalyseurLexical.T_UNILEX.parfer){
                    return true;
                }
                System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", ) attendu");
                return false;
            }
            return false;
        }
        else if (UNILEX == AnalyseurLexical.T_UNILEX.moins){
            UNILEX = al.ANALEX();
            return TERME();
            /*if (TERME()){
                return true;
            }
            return false;*/
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", ENT, IDENT, ( ou - attendu");
        return false;
    }

    private boolean SUITE_TERME() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.plus || UNILEX == AnalyseurLexical.T_UNILEX.moins || UNILEX == AnalyseurLexical.T_UNILEX.mult || UNILEX == AnalyseurLexical.T_UNILEX.divi){
            UNILEX = al.ANALEX();
            return EXP();
        }
        else if (UNILEX == AnalyseurLexical.T_UNILEX.ptvirg || UNILEX == AnalyseurLexical.T_UNILEX.parfer || UNILEX == AnalyseurLexical.T_UNILEX.virg){
            return true;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", +, -, *, /, ;, ) ou , attendu");
        return false;
    }

    private boolean LECTURE() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("LIRE") == 0){
            if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.parouv){
                if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ident){
                    while ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.virg){
                        if (!((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.ident)){
                            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + "IDENT attendu");
                            return false;
                        }
                    }
                    if (UNILEX == AnalyseurLexical.T_UNILEX.parfer){
                        return true;
                    }
                    System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT ou ) attendu");
                }
                System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", IDENT attendu");
                return false;
            }
            System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + "( attendu");
            return false;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + "LIRE attendu");
        return false;
    }

    private boolean ECRITURE() throws IOException{
        if (UNILEX == AnalyseurLexical.T_UNILEX.motcle && al.CHAINE.compareTo("ECRIRE") == 0){
            if ((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.parouv){
                UNILEX = al.ANALEX();
                while (ECR_EXP()){
                    if (!((UNILEX = al.ANALEX()) == AnalyseurLexical.T_UNILEX.virg)){
                        break;
                    }
                    UNILEX = al.ANALEX();
                }
                if (UNILEX == AnalyseurLexical.T_UNILEX.parfer){
                    return true;
                }
                System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", ) attendu");
                return false;
            }
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + "ECRIRE attendu");
        return false;
    }

    private boolean ECR_EXP() throws IOException{
        if (EXP()){
            return true;
        }
        else if (UNILEX == AnalyseurLexical.T_UNILEX.ch){
            return true;
        }
        System.out.println("Erreur synthaxique, ligne : " + al.NUM_LIGNE + ", EXP ou CHAINE attendu");
        return false;
    }
}
