import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        AnalyseurLexical analyseurLexical = new AnalyseurLexical();

        analyseurLexical.INITIALISER();
        analyseurLexical.TEST();
        analyseurLexical.TERMINER();

        analyseurLexical.tab.afficher();
    }
}