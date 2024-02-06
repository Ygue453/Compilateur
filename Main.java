import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main{

    public enum T_UNILEX {
        motcle, ident, ent, ch, virg, ptvirg, point, deuxpts, parouv, parfer, inf, sup, eg, plus, moins, mult, divi, infe, supe, diff, aff
    }

    public static final int LONG_MAX_IDENT = 20;
    public static final int LONG_MAX_CHAINE = 50;
    public static final int NB_MOTS_RESERVES = 7;

    public static File SOURCE;
    public static char CARLU;
    public static int NOMBRE;
    public static String CHAINE;
    public static int NUM_LIGNE;
    public static MotReserve[] TABLE_MOTS_RESERVES = new MotReserve[NB_MOTS_RESERVES];

    public static boolean b = false;

    public static void main(String[] args) throws IOException {
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
    }

    public void ERREUR(int nb_err){

    }

    public static void INITIALISER(){
        TABLE_MOTS_RESERVES[0] = new MotReserve("CONST");
        TABLE_MOTS_RESERVES[1] = new MotReserve("DEBUT");
        TABLE_MOTS_RESERVES[2] = new MotReserve("ECRIRE");
        TABLE_MOTS_RESERVES[3] = new MotReserve("FIN");
        TABLE_MOTS_RESERVES[4] = new MotReserve("LIRE");
        TABLE_MOTS_RESERVES[5] = new MotReserve("PROGRAMME");
        TABLE_MOTS_RESERVES[6] = new MotReserve("VAR");
    }
}