import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        AnalyseurLexical analyseurLexical = new AnalyseurLexical("./programme2.txt");
        AnalyseurSynthaxique analyseurSynthaxique = new AnalyseurSynthaxique(analyseurLexical);

        analyseurLexical.INITIALISER();
        analyseurSynthaxique.ANASYNT();
        //analyseurLexical.TEST();
        analyseurLexical.TERMINER();

        //analyseurLexical.tab.AFFICHE_TABLE_IDENT();
    }
}