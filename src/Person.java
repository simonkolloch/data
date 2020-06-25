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

    public int kaufeProdukt(Produkt pProdukt){
        if(gekaufteProdukte.contains(pProdukt)){
            //System.out.println("Produkt wurde bereits gekauft");
            return DOPPELT;
        }
        else{
            //System.out.println(this.getName()+"hat "+pProdukt.getName()+" gekauft");
            gekaufteProdukte.add(pProdukt);
            return OK;
        }
    }

    public int schliesseFreundschaft(Person pPerson){
        if(freunde.contains(pPerson)){
           // System.out.println(this.getName()+" und "+pPerson.getName()+" sind bereits befreundet");
            return DOPPELT;
        }
        else{
            //System.out.println(this.getName()+" und " +pPerson.getName()+" sind jetzt Freunde");
            freunde.add(pPerson);
            return OK;
        }
    }

    public int produktNetzwerk(){
        ArrayList<Produkt> produkte;
        produkte=netzwerk();
        if(produkte.size()!=0){
            for(int i =0; i<=produkte.size()-1;i++){
                System.out.print(produkte.get(i).getName());
                if(i!=produkte.size()-1){
                    System.out.print(", ");
                }
            }
            return OK;
        }
        else{
            System.out.println("Keine Produkte im Produktnetzwerk");
            return KEIN_OBJEKT_GEFUNDEN;
        }
    }

    public int firmenNetzwerk(){
        ArrayList<Produkt> produkte;
        ArrayList<Firma> firmen = new ArrayList<Firma>();
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
            for(int i=0;i<=firmen.size()-1;i++){
                System.out.print(firmen.get(i).getName());
                if(i!=firmen.size()-1){
                    System.out.print(", ");
                }
            }
            return OK;
        }
        else{
            System.out.println("Keine Firmen im Firmennetzwerk");
            return KEIN_OBJEKT_GEFUNDEN;
        }
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

    public int checkFreundschaft(){
        int ergebniss=KEINE_FREUNDSCHAFT;
        for(Person p:this.freunde){
            if(p.freunde.contains(this)){
                ergebniss=OK;
            }
            else{
                System.out.println("FAKE FREUNDE GEFUNDEN");
                this.freunde.remove(p);
            }
        }
        return ergebniss;
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
