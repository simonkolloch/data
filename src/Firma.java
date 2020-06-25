import java.util.ArrayList;

public class Firma {
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

    public ArrayList<Produkt> getProdukte() {
        return produkte;
    }
}
