import java.io.IOException;
import java.util.ArrayList;

public class AnalyseurSynthaxique {
    private AnalyseurLexical.T_UNILEX UNILEX;
    private AnalyseurLexical al;

    private ArrayList<AnalyseurLexical.T_UNILEX> firstPROG = new ArrayList<>();

    public AnalyseurSynthaxique(AnalyseurLexical analyseurLexical){
        this.al = analyseurLexical;

        this.firstPROG.add(AnalyseurLexical.T_UNILEX.motcle);
        
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
                    }
                    System.out.println("Erreur syntaxique, ligne : " + al.NUM_LIGNE + ", PTVIRG expected");
                    return false;
                }
                System.out.println("TODO : erreur ident already exist");
                return false;
            }
            System.out.println("Erreur syntaxique, ligne : " + al.NUM_LIGNE + ", IDENT expected");
            return false;
        }
        System.out.println("Erreur syntaxique, ligne : " + al.NUM_LIGNE + ", MOTCLE expected");
        return false;
    }
}
