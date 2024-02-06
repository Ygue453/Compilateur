public class MotReserve {
    private String mot;

    public MotReserve(String mot){
        if (mot.length() <= 9){
            this.mot = mot;
        }
        else{
            throw new IllegalArgumentException("La longueur du mot réservé doit être de 9 caractères.");
        }
    }

    public String getMot(){
        return mot;
    }
}
