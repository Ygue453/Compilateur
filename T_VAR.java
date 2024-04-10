public class T_VAR extends T_IDENT {
    private int adrv;

    public T_VAR(String name, int adrv){
        super(name, "variable", 0);
        this.adrv = adrv;
    }

    public String toString(){
        return "name:" + this.getName() + " typ:" + this.getTyp() + " typc:" + this.getTypc() + " adrv:" + this.adrv;
    }
}
