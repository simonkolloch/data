import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class Person {
    private int OK=100;
    private int DOPPELT=95;
    private static int KEIN_OBJEKT_GEFUNDEN=900;
    private int KEINE_FREUNDSCHAFT=97;
    public int iD;
    public String name,geschlecht;
    public ArrayList<Person> freunde = new ArrayList<Person>();
    public ArrayList<Produkt> gekaufteProdukte = new ArrayList<Produkt>();

    public Person(int pID, String pName, String pGeschlecht){
        this.iD=pID;
        this.name=pName;
        this.geschlecht=pGeschlecht;
    }

    public void kaufeProdukt(Produkt pProdukt){
        if(!gekaufteProdukte.contains(pProdukt)){
            gekaufteProdukte.add(pProdukt);
        }
    }

    public void schliesseFreundschaft(Person pPerson){
        if(!freunde.contains(pPerson)){
           // System.out.println(this.getName()+" und "+pPerson.getName()+" sind bereits befreundet");
            freunde.add(pPerson);
        }
    }

    public ArrayList <String> produktNetzwerk() {
        ArrayList<Produkt> produkte;
        ArrayList<String>ergebnis = new ArrayList<>();
        produkte = netzwerk();
        if (produkte.size() != 0) {
            for(Produkt p: produkte){
                ergebnis.add("ID: "+p.getiD()+", Name: "+p.getName()+", Firma: "+p.getFirma().getName());
            }
        }
        else {
            ergebnis.add("Keine Produkte im Produktnetzwerk!");
        }
        return ergebnis;
    }

    public ArrayList<String> firmenNetzwerk(){
        ArrayList<Produkt> produkte;
        ArrayList<Firma> firmen = new ArrayList<Firma>();
        ArrayList<String>ergebnis = new ArrayList<>();
        produkte=netzwerk();
        for(Produkt x: produkte){
                if(!firmen.contains(x.getFirma())){
                    firmen.add(x.getFirma());
                }
        }
        Collections.sort(firmen, new Comparator<Firma>() {
            @Override
            public int compare(Firma p1, Firma p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        if(firmen.size()!=0){
            for(Firma p :firmen){
                ergebnis.add("ID: "+p.getId()+", Name: "+p.getName());
            }
        }
        else {
            ergebnis.add("Keine Firmen im Firmennetzwerk!");
        }
        return ergebnis;
    }

    private ArrayList<Produkt> netzwerk(){
        ArrayList<Produkt> produkte = new ArrayList<Produkt>();
        for(Person f: freunde){
            for(Produkt x: f.getGekaufteProdukte()){
                if(!produkte.contains(x)){
                    produkte.add(x);
                }
            }
        }
        produkte.removeAll((new HashSet(this.getGekaufteProdukte())));
        Collections.sort(produkte, new Comparator<Produkt>() {
            @Override
            public int compare(Produkt p1, Produkt p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        return produkte;
    }

    public void checkFreundschaft(){
        for(Person p:this.freunde){
            if(!p.freunde.contains(this)){
                System.out.println("FAKE FREUNDE GEFUNDEN");
                this.freunde.remove(p);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getiD() {
        return iD;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public ArrayList<Produkt> getGekaufteProdukte() {
        return gekaufteProdukte;
    }
}
