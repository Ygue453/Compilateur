import java.io.*;

public class AnalyseurLexical {

    public enum T_UNILEX {
        motcle, ident, ent, ch, virg, ptvirg, point, deuxpts, parouv, parfer, inf, sup, eg, plus, moins, mult, divi, infe, supe, diff, aff
    }

    public final int LONG_MAX_IDENT = 20;
    public final int LONG_MAX_CHAINE = 50;
    public final int NB_MOTS_RESERVES = 7;

    public File SOURCE;
    public char CARLU;
    public int NOMBRE;
    public String CHAINE;
    public int NUM_LIGNE;
    public MotReserve[] TABLE_MOTS_RESERVES = new MotReserve[NB_MOTS_RESERVES];

    File file = new File("./programme.txt");
    BufferedReader br;

    {
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        }
    }

    /*public static void main(String[] args) throws IOException {
        if (args.length == 1){
            INITIALISER();

            File file = new File(args[0]);
            SOURCE = file;
            BufferedReader br = new BufferedReader(new FileReader(file));
            int c;
            while ((c = br.read()) != -1) {
                CARLU = (char) c;
                System.out.print(CARLU);
                if (Character.isDigit(CARLU)){
                    b = true;
                }
                else if (b){
                    System.out.print("[nombre]");
                    b = false;
                }
            }
            System.out.println("");
        }
        else{
            System.out.println(Integer.MAX_VALUE);
        }
    }*/

    //Recense tout les messages d'erreurs et les associes à des numéros
    public void ERREUR(int nb_err){


    }

    //Lit un caractère est l'affecte à CARLU, incrémente NUM_LIGNE a chaque saut de ligne
    //Appel l'erreur 1 à la fin du fichier
    public void LIRE_CAR(){

    }

    //Lit des caractères jusqu'à ce que le commentaire, les espaces, les sauts de lignes ou les tabulations soit passés
    public void SAUTER_SEPARATEURS(){

    }

    //Reconnait les nombres entiers
    public void RECO_ENTIER(){

    }

    //Reconnait les chaines de caractères
    public void RECO_CHAINE(){

    }

    //Reconnait un identificateur ou un mot réservé et l'affecte à CARLU
    //Renvoie l'unité lexical correspondante
    public T_UNILEX RECO_IDENT_OU_MOT_RESERVE(){

    }

    //Reconnait un symbole simple ou composé
    //Renvoie l'unité lexical correspondante
    public T_UNILEX RECO_SYMB(){

    }

    //Reconnait l'unité lexical dans le fichier source
    public T_UNILEX ANALEX(){

    }

    //Initialise les variable globals

    public void INITIALISER(){
        TABLE_MOTS_RESERVES[0] = new MotReserve("CONST");
        TABLE_MOTS_RESERVES[1] = new MotReserve("DEBUT");
        TABLE_MOTS_RESERVES[2] = new MotReserve("ECRIRE");
        TABLE_MOTS_RESERVES[3] = new MotReserve("FIN");
        TABLE_MOTS_RESERVES[4] = new MotReserve("LIRE");
        TABLE_MOTS_RESERVES[5] = new MotReserve("PROGRAMME");
        TABLE_MOTS_RESERVES[6] = new MotReserve("VAR");
    }

    //Effectue les instructions de fin de programme
    public void TERMINER(){
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
