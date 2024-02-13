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
        switch (nb_err) {
            case 1:
                System.err.println("Erreur 1 : fin de fichier atteinte");
                break;
        
            default:
                break;
        }
    }

    //Lit un caractère est l'affecte à CARLU, incrémente NUM_LIGNE a chaque saut de ligne
    //Appel l'erreur 1 à la fin du fichier
    public void LIRE_CAR() throws IOException{
        int read = br.read();
        if (read > 0){
            CARLU = (char) read;
            if (CARLU == '\n'){
                NUM_LIGNE++;
            }
        }
        else if(read == -1){
            ERREUR(1);
        }
        else {
            //TODO : erreur probleme de lecture n°?
        }
    }

    //Lit des caractères jusqu'à ce que le commentaire, les espaces, les sauts de lignes ou les tabulations soit passés
    public void SAUTER_SEPARATEURS() throws IOException{
        if (CARLU == '{'){
            //saute les commentaires
            while (CARLU != '}') {
                LIRE_CAR();
            }
        }
        else if (CARLU == ' ' || CARLU == '\t' || CARLU == '\n'){
            // saute les espaces, tabulations et fin de lignes
            LIRE_CAR();
            while (CARLU == ' ' || CARLU == '\t' || CARLU == '\n'){
                LIRE_CAR();
            }
        }
    }

    //Reconnait les nombres entiers
    public void RECO_ENTIER() throws IOException{
        int nb = Integer.parseInt(CARLU + "");
        LIRE_CAR();
        while (Character.isDigit(CARLU)) {
            nb = nb * 10 + Integer.parseInt(CARLU + "");
            LIRE_CAR();
        }
        if (nb > 32767){
            //TODO : erreur nombre trop grand n°?
            //ERREUR(?);
        }
    }

    //Reconnait les chaines de caractères
    public void RECO_CHAINE() throws IOException{
        String string = "";
        LIRE_CAR();
        while ((CARLU + "") != "'"){
            string = string + CARLU;
            LIRE_CAR();
        }
        if (string.length() > LONG_MAX_CHAINE){
            //TODO : erreur chaine de caractères trop grande n°?
        }
        CHAINE = string;
    }

    //Reconnait un identificateur ou un mot réservé et l'affecte à CARLU
    //Renvoie l'unité lexical correspondante
    public T_UNILEX RECO_IDENT_OU_MOT_RESERVE() throws IOException{
        String string = CARLU + "";
        LIRE_CAR();
        while (Character.isAlphabetic(CARLU) || Character.isDigit(CARLU) || CARLU == '_'){
            string = string + CARLU;
            LIRE_CAR();
        }

        CHAINE = string;
        if (EST_UN_MOT_RESERVE()){ return T_UNILEX.motcle; }
        else { return T_UNILEX.ident; }
    }

    private boolean EST_UN_MOT_RESERVE(){
        for (int i = 0; i < NB_MOTS_RESERVES; i++){
            if (CHAINE == TABLE_MOTS_RESERVES[i].getMot()){
                return true;
            }
        }
        return false;
    }

    //Reconnait un symbole simple ou composé
    //Renvoie l'unité lexical correspondante
    public T_UNILEX RECO_SYMB() throws IOException{
        switch (CARLU) {
            case ',':
                return T_UNILEX.virg;

            case ';':  
                return T_UNILEX.ptvirg;

            case '.':
                return T_UNILEX.point; 

            case ':':
                LIRE_CAR();
                if(CARLU == '='){
                    return T_UNILEX.aff;
                }
                return T_UNILEX.deuxpts; 

            case '(':
                return T_UNILEX.parouv; 

            case ')':
                return T_UNILEX.parfer; 

            case '<':
                LIRE_CAR();
                if ( CARLU == '='){
                    return T_UNILEX.infe;
                }
                if (CARLU == '>'){
                    return T_UNILEX.diff;
                }
                return T_UNILEX.inf; 

            case '>':
                LIRE_CAR();
                if ( CARLU == '='){
                    return T_UNILEX.supe;
                }
                return T_UNILEX.sup; 
            
            case '=':
                return T_UNILEX.eg; 

            case '+':
                return T_UNILEX.plus; 

            case '-':
                return T_UNILEX.moins; 

            case '*':
                return T_UNILEX.mult; 

            case '/':
                return T_UNILEX.divi;
        
            default:
                //TODO: erreur caractère non reconnue n°?;
        }
        return null;
    }

    //Reconnait l'unité lexical dans le fichier source
    public T_UNILEX ANALEX() throws IOException{
        SAUTER_SEPARATEURS();
        if (Character.isDigit(CARLU)){
            RECO_ENTIER();
            return T_UNILEX.ent;
        }
        else if((CARLU + "") == "'"){
            RECO_CHAINE();
            return T_UNILEX.ch;
        }
        else if (Character.isAlphabetic(CARLU)){
            return RECO_IDENT_OU_MOT_RESERVE();
        }
        else {
            return RECO_SYMB();
        }
    }

    //Initialise les variable globals

    public void INITIALISER() throws FileNotFoundException{
        TABLE_MOTS_RESERVES[0] = new MotReserve("CONST");
        TABLE_MOTS_RESERVES[1] = new MotReserve("DEBUT");
        TABLE_MOTS_RESERVES[2] = new MotReserve("ECRIRE");
        TABLE_MOTS_RESERVES[3] = new MotReserve("FIN");
        TABLE_MOTS_RESERVES[4] = new MotReserve("LIRE");
        TABLE_MOTS_RESERVES[5] = new MotReserve("PROGRAMME");
        TABLE_MOTS_RESERVES[6] = new MotReserve("VAR");

        br = new BufferedReader(new FileReader(file));
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