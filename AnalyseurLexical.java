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
    public T_TAB tab;

    private BufferedReader br;
    
    public AnalyseurLexical(String filePath){
        SOURCE = new File(filePath);
        tab = new T_TAB();
    }

    public void TEST() throws IOException{
        LIRE_CAR();
        T_UNILEX tUnilex;
        while (CARLU != '.') {
            tUnilex = ANALEX();
            if (tUnilex == T_UNILEX.ident && tab.CHERCHER(CHAINE) == -1){
                tab.INSERER(CHAINE, new T_IDENT(CHAINE, "identificateur", 0));
            }
            else if (tab.CHERCHER(CHAINE) != -1){
                System.out.println("Erreur : déclaration ident impossible, ligne : " + this.NUM_LIGNE);
            }
            System.out.println(CHAINE + " -> " + tUnilex);
        }
        tUnilex = ANALEX();
        System.out.println(CHAINE + " -> " + tUnilex);
    }

    //Recense tout les messages d'erreurs et les associes à des numéros
    public void ERREUR(int nb_err){
        switch (nb_err) {
            case 1:
                System.err.println("Erreur 1 : fin de fichier atteinte");
                break;        
            default:
                break;
        }
        TERMINER();
    }

    //Lit un caractère est l'affecte à CARLU, incrémente NUM_LIGNE a chaque saut de ligne
    //Appel l'erreur 1 à la fin du fichier
    public void LIRE_CAR() throws IOException{
        int read = br.read();
        if (read > 0){
            CARLU = (char) read;
            if (CARLU == '\n' || CARLU == '\r'){
                NUM_LIGNE++;
            }
        }
        else if(read == -1){
            ERREUR(1);
        }
        else {
            System.out.println("TODO : erreur probleme de lecture n°?");
        }
    }
    
    //Lit des caractères jusqu'à ce que le commentaire, les espaces, les sauts de lignes ou les tabulations soit passés
    public void SAUTER_SEPARATEURS() throws IOException{
        if (CARLU == '{'){
            //saute les commentaires
            int counter = 1;
            //si les commentaire imbriqués sont autorisé
            while (counter > 0){
                LIRE_CAR();
                if (CARLU == '{'){
                    counter++;
                }
                if (CARLU == '}'){
                    counter--;
                }
            }
            LIRE_CAR();
        }
        else if (CARLU == ' ' || CARLU == '\t' || CARLU == '\n' || CARLU == '\r'){
            // saute les espaces, tabulations et fin de lignes
            LIRE_CAR();
            while (CARLU == ' ' || CARLU == '\t' || CARLU == '\n' || CARLU == '\r'){
                LIRE_CAR();
            }
        }
        if (CARLU == '{' || CARLU == ' ' || CARLU == '\t' || CARLU == '\n' || CARLU == '\r'){
            SAUTER_SEPARATEURS();
        }
    }

    //Reconnait les nombres entiers
    public T_UNILEX RECO_ENTIER() throws IOException{
        int nb = Integer.parseInt(CARLU + "");
        LIRE_CAR();
        while (Character.isDigit(CARLU)) {
            nb = nb * 10 + Integer.parseInt(CARLU + "");
            LIRE_CAR();
        }
        if (nb > 32767){
            System.out.println("TODO : erreur nombre trop grand n°?");
            //ERREUR(?);
        }
        else { NOMBRE = nb; }
        CHAINE = NOMBRE + "";
        return T_UNILEX.ent;
    }

    //Reconnait les chaines de caractères
    public T_UNILEX RECO_CHAINE() throws IOException{
        String string = "";
        LIRE_CAR();
        do {
            string = string + CARLU;
            LIRE_CAR();
            if (CARLU == '\''){
                LIRE_CAR();
                if (CARLU == '\''){
                    string = string + CARLU;
                    LIRE_CAR();
                }
                else {
                    break;
                }
            }
        }
        while (CARLU != '\'');

        if (string.length() > LONG_MAX_CHAINE){
            System.out.println("TODO : erreur chaine de caractères trop grande n°?");
        }
        CHAINE = string;
        return T_UNILEX.ch;
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
            if (CHAINE.compareTo(TABLE_MOTS_RESERVES[i].getMot()) == 0){
                return true;
            }
        }
        return false;
    }

    //Reconnait un symbole simple ou composé
    //Renvoie l'unité lexical correspondante
    public T_UNILEX RECO_SYMB() throws IOException{
        CHAINE = CARLU + "";
        switch (CARLU) {
            case ',':
                LIRE_CAR();
                return T_UNILEX.virg;

            case ';':  
                LIRE_CAR();
                return T_UNILEX.ptvirg;

            case '.':
                LIRE_CAR();
                return T_UNILEX.point; 

            case ':':
                LIRE_CAR();
                if(CARLU == '='){
                    CHAINE = CHAINE + CARLU;
                    LIRE_CAR();
                    return T_UNILEX.aff;
                }
                LIRE_CAR();
                return T_UNILEX.deuxpts; 

            case '(':
                LIRE_CAR();
                return T_UNILEX.parouv; 

            case ')':
                LIRE_CAR();
                return T_UNILEX.parfer; 

            case '<':
                LIRE_CAR();
                if ( CARLU == '='){
                    CHAINE = CHAINE + CARLU;
                    LIRE_CAR();
                    return T_UNILEX.infe;
                }
                if (CARLU == '>'){
                    CHAINE = CHAINE + CARLU;
                    LIRE_CAR();
                    return T_UNILEX.diff;
                }
                LIRE_CAR();
                return T_UNILEX.inf; 

            case '>':
                LIRE_CAR();
                if ( CARLU == '='){
                    CHAINE = CHAINE + CARLU;
                    LIRE_CAR();
                    return T_UNILEX.supe;
                }
                LIRE_CAR();
                return T_UNILEX.sup; 
            
            case '=':
                LIRE_CAR();
                return T_UNILEX.eg; 

            case '+':
                LIRE_CAR();
                return T_UNILEX.plus; 

            case '-':
                LIRE_CAR();
                return T_UNILEX.moins; 

            case '*':
                LIRE_CAR();
                return T_UNILEX.mult; 

            case '/':
                LIRE_CAR();
                return T_UNILEX.divi;
        
            default:
                System.out.println("TODO: erreur caractère non reconnue n°?;");
        }
        return null;
    }

    //Reconnait l'unité lexical dans le fichier source
    public T_UNILEX ANALEX() throws IOException{
        CHAINE = "";
        SAUTER_SEPARATEURS();
        if (Character.isDigit(CARLU)){
            return RECO_ENTIER();
        }
        else if(CARLU == '\''){
            return RECO_CHAINE();
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

        br = new BufferedReader(new FileReader(SOURCE));
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
