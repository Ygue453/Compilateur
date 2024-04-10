public class T_CONST extends T_IDENT {
    private Object val;
    
    public T_CONST(String name, int typc, Object val){
        super(name, "constante", typc);
        this.val = val;
    }

    public String toString(){
        return "name:" + this.getName() + " typ:" + this.getTyp() + " typc:" + this.getTypc() + " val:" + this.val;
    }
}
