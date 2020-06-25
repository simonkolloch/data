import java.util.ArrayList;

public class Firma {
    private int DOPPELT=95;
    private int OK=100;
    int id;
    String name;
    public ArrayList<Produkt> produkte = new ArrayList<Produkt>();

    public Firma(int pID, String pName){
        this.id =pID;
        this.name=pName;
    }

    public int stelleHer(Produkt pProdukt){
        if(produkte.contains(pProdukt)){
            //System.out.println("Produkt wird bereits hergestellt");
            return DOPPELT;
        }
        else{
            //System.out.println(this.getName()+" stellt "+pProdukt.getName()+" her");
            produkte.add(pProdukt);
            return OK;
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
