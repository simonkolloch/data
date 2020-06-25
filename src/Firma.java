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

    public void stelleHer(Produkt pProdukt){
        if(!produkte.contains(pProdukt)){
            produkte.add(pProdukt);

        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
