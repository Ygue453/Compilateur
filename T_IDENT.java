public class T_IDENT {
    private String name;
    private String typ;
    private int typc;

    public T_IDENT(String name, String typ, int typc){
        this.name = name;
        this.typ = typ;
        this.typc = typc;
    }

    public String getName(){
        return this.name;
    }

    public String getTyp(){
        return this.typ;
    }

    public int getTypc(){
        return this.typc;
    }

    public String toString(){
        return "name:" + this.name + " typ:" + this.typ + " typc:" + typc; 
    }
}
