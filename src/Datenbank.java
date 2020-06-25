import java.io.*;
import java.util.ArrayList;

public class Datenbank {
    private static final int PERSONCODE = 10, PRODUCTCODE = 20, COMPANYCODE = 30, FRIENDSCODE = 40, BELONGSCODE = 50, PRODUCERCODE = 60,
                             NEW_ENTITY_ERROR=99, SYNTAX_ERROR=98,EXISTIERT=1, EXISTIERT_NICHT=0;
    public static ArrayList<Person> personenListe = new ArrayList<Person>();
    public static ArrayList<Firma> firmenListe = new ArrayList<Firma>();
    public static ArrayList<Produkt> produktListe = new ArrayList<Produkt>();

    public  void checkArgument(String arg) {
        String parameter;
        String auswahl;
        parameter = arg.substring(arg.indexOf("=") + 1);
        auswahl = arg.split("=")[0];
        auswahl = auswahl.substring(auswahl.indexOf("-") + 2);
        if(parameter.isBlank()){
            auswahl="kein Parameter";
        }
        switch (auswahl) {
            case "personensuche":
                System.out.println("Suche nach " + parameter + " ergab folgendes Ergebnis:");
                ausgabe(personenSuche(parameter));
                break;
            case "produktsuche":
                System.out.println("Suche nach " + parameter + " ergab folgendes Ergebnis:");
                ausgabe(produktSuche(parameter));
                break;
            case "produktnetzwerk":
                System.out.println("Suche nach Produktnetzwerk von " + parameter + " ergab folgendes Ergebnis:");
                ausgabe(produktnetzwerk(parameter));
                break;
            case "firmennetzwerk":
                firmennetzwerk(parameter);
                ausgabe(firmennetzwerk(parameter));
                break;
            case "kein Parameter":
                System.out.println("Bitte übergebe auch ein Parameter!");
            default:
                System.out.println("Eingegebes Argument Fehlerhaft! Mögliche Argumente:\n\b--personensuche=\"[name]\"\n\b--produktsuche=\"[produktname]\"\n\b--produktnetzwerk=\"[personen_id]\"\n\b--firmennetzwerk=\"[personen_id]\"\n");
                break;
        }
    }

    public void ausgabe(ArrayList<String> pListe){
        int i = 0;
        for(String s: pListe){
            if(i!= pListe.size()-1){
                System.out.println(s+";");
                i++;
            }
            else{
                System.out.println(s);
            }
        }
    }

    public ArrayList<String> personenSuche(String suche) {
        String produkte="";
        ArrayList<String>ergebniss= new ArrayList<>();
        for (Person p : personenListe) {
            if (p.getName().toLowerCase().contains(suche.toLowerCase())) {
                if(p.getGekaufteProdukte().isEmpty()){
                    produkte=" besitzt keine produkte";
                }
                else{
                    int j=0;
                    for(Produkt i:p.getGekaufteProdukte()){
                        produkte+=i.getName();
                        if(j!=p.getGekaufteProdukte().size()-1){
                            produkte+=", ";
                        }
                        j++;
                    }
                }
                ergebniss.add("ID: " + p.getiD() + ", Name: " + p.getName() + ", Geschlecht: " + p.getGeschlecht()+", Produkte: "+produkte);
                produkte="";
            }
        }
        if(ergebniss.isEmpty()){
            ergebniss.add("Keine Person gefunden!");
        }
        return ergebniss;
    }

    public ArrayList<String> produktSuche(String suche) {
        ArrayList<String>ergebniss = new ArrayList<>();
        for (Produkt p : produktListe) {
            if (p.getName().toLowerCase().contains(suche.toLowerCase())) {
                ergebniss.add("ID: " + p.getiD() + ", Name: " + p.getName()+ ", Firma: " +p.getFirma().getName());
            }
        }
        if(ergebniss.isEmpty()){
            ergebniss.add("Kein Produkt gefunden!");
        }
        return ergebniss;
    }


    public ArrayList <String> produktnetzwerk(String suche){
        ArrayList <String> ergebnis = new ArrayList<>();
        System.out.println("Suche nach Produktnetzwerk von " + suche + " ergab folgendes Ergebnis:");
        if(existPerson(Integer.parseInt(suche))==1) {
            for (Person x : personenListe) {
                if (x.getiD() == Integer.parseInt(suche)) {
                    ergebnis = x.produktNetzwerk();
                }
            }
        }
        else{
            ergebnis.add("Die gesuchte Person existiert nicht!");
        }
        return ergebnis;
    }

    public ArrayList<String> firmennetzwerk(String suche){
       ArrayList<String>ergebnis = new ArrayList<>();
        System.out.println("Suche nach Firmennetzwerk von " + suche + " ergab folgendes Ergebnis:");
        if(existPerson(Integer.parseInt(suche))==1) {
            for (Person x : personenListe) {
                if (x.getiD() == Integer.parseInt(suche)) {
                    ergebnis=x.firmenNetzwerk();
                }
            }
        }
        else{
            ergebnis.add("Die gesuchte Person existiert nicht!");
        }
        return ergebnis;
    }

    public void importFile(FileReader file) {
        String line = null;
        int returnCode = NEW_ENTITY_ERROR;
        try (BufferedReader br = new BufferedReader(file)) {
            while ((line = br.readLine()) != null) {
                if (line.contains("New_Entity")) {
                    returnCode = getCode(line);
                } else {
                    datenEintragen(returnCode, line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getCode(String pLine) {
        pLine = pLine.substring(pLine.indexOf(":") + 1);
        if (pLine.equals(" \"person_id\", \"person_name\", \"person_gender\"")) {
            return PERSONCODE;
        } else if (pLine.equals( " \"product_id\",\"product_name\"")) {
            return PRODUCTCODE;
        } else if (pLine.equals(" \"company_id\",\"company_name\"")) {
            return COMPANYCODE;
        } else if (pLine.equals(" \"person1_id\",\"person2_id\"")) {
            return FRIENDSCODE;
        }else if (pLine.equals(" \"person_id\",\"product_id\"")) {
            return BELONGSCODE;
        } else if (pLine.equals(" \"product_id\",\"company_id\"")) {
            return PRODUCERCODE;
        } else {
            return SYNTAX_ERROR;
        }
    }


    public void datenEintragen(int code, String pLine) {
        String[] argumente;
        switch (code) {
            case NEW_ENTITY_ERROR:
                System.out.println("Es liegt ein fehler mit der zu importierenden Datei vor!\nDie Datei muss in der ersten Zeile mit 'New_Entity: [parameter],..' beginnen");
                break;
            case SYNTAX_ERROR:
                System.out.println("Bitte überprüfe die Syntax von der Datei! Die Syntax für neue Identitäten sieht wie folgt aus:");
                System.out.println("\bNeue Person: New_Entity: \"person_id\", \"person_name\", \"person_gender\"");
                System.out.println("\bNeues Produkt: New_Entity: \"product_id\",\"product_name\"");
                System.out.println("\bNeue Firma: New_Entity: \"company_id\",\"company_name\"");
                System.out.println("\bNeue Freundschaft: New_Entity: \"person1_id\",\"person2_id\"");
                System.out.println("\bNeuer Besitz: New_Entity: \"person_id\",\"product_id\"");
                System.out.println("\bNeue Produktion: New_Entity: \"product_id\",\"company_id\"");
            case PERSONCODE:
                argumente = argumenteVorbereiten(pLine);
                addToList(personenListe, new Person(Integer.parseInt(argumente[0]), argumente[1], argumente[2]));
                break;
            case PRODUCTCODE:
                argumente = argumenteVorbereiten(pLine);
                addToList(produktListe, new Produkt(Integer.parseInt(argumente[0]), argumente[1]));
                break;
            case COMPANYCODE:
                argumente = argumenteVorbereiten(pLine);
                addToList(firmenListe, new Firma(Integer.parseInt(argumente[0]), argumente[1]));
                break;
            case FRIENDSCODE:
                argumente = argumenteVorbereiten(pLine);
                addFriend(Integer.parseInt(argumente[0]), Integer.parseInt( argumente[1]));
                break;
            case BELONGSCODE:
                argumente = argumenteVorbereiten(pLine);
                addProdukt(Integer.parseInt(argumente[0]), Integer.parseInt( argumente[1]));
                break;
            case PRODUCERCODE:
                argumente = argumenteVorbereiten(pLine);
                addProduktion(Integer.parseInt(argumente[0]), Integer.parseInt( argumente[1]));
                break;
            default:

        }
    }

    private String[] argumenteVorbereiten(String pLine){
        String[] argumente;
        argumente = pLine.split(",");
        argumente[0] = argumente[0].replaceAll("^\"+|\"+$", "");
        argumente[1] = argumente[1].replaceAll("^\"+|\"+$", "");
        if(argumente.length==3){
            argumente[2] = argumente[2].replaceAll("^\"+|\"+$", "");
        }
        return argumente;
    }

        /*Diese Methode fuegt Die Objekte in die jeweilige Liste.
           Personen in die personenliste
           Firmen in die firmenliste
           und Produkte in die Produktliste
        */
    private void addToList(ArrayList list, Object p) {
        if (!list.contains(p)) {
            list.add(p);
        }
    }


    public void addFriend(int friend1, int friend2) {
        int index=0;
        if(existPerson(friend1)==1&&existPerson(friend2)==1) {
            for (Person p : personenListe) {
                if (p.getiD() == friend2) {
                    index = personenListe.indexOf(p);
                }
            }
            for (Person i : personenListe) {
                if (i.getiD() == friend1) {
                       i.schliesseFreundschaft(personenListe.get(index));
                }
            }
        }
    }


    public void addProdukt(int person, int produkt) {
        int index=0;
        if(existPerson(person)==EXISTIERT&&existProdukt(produkt)==EXISTIERT) {
            for (Produkt p : produktListe) {
                if (p.getiD() == produkt) {
                    index = produktListe.indexOf(p);
                }
            }
            for (Person i : personenListe) {
                if (i.getiD() == person) {
                    i.kaufeProdukt(produktListe.get(index));
                }
            }
        }
    }


    private void addProduktion(int produkt, int firma) {
        int index=0;
        if(existProdukt(produkt)==EXISTIERT&&existFirma(firma)==EXISTIERT) {
            for (Produkt p : produktListe) {
                if (p.getiD() == produkt) {
                    index = produktListe.indexOf(p);
                }
            }
            for (Firma i : firmenListe) {
                if (i.getId() == firma) {
                    i.stelleHer(produktListe.get(index));
                    produktListe.get(index).setFirma(i);
                }
            }
        }
    }
    /*
    Diese Methode iteriert durch alle Freundeslisten, der Personen die angelegt worden sind.

     */
    public void checkFreundschaft(){
        for(Person p: personenListe){
            p.checkFreundschaft();
        }
    }
    /*
    Die Folgenden drei Methoden ueberpruefen, ob eine Person, Firma oder Produkt existiert.
    Rueckgabewert ist entweder EXISTIERT_NICHT oder OK
     */
    private int existPerson(int pPerson){
        int ergebniss=EXISTIERT_NICHT;
        for(Person x:personenListe){
            if(x.getiD()==pPerson){
                ergebniss=EXISTIERT;
            }
        }
        return ergebniss;
    }
    private int existFirma(int pFirma){
        int ergebniss=EXISTIERT_NICHT;
        for(Firma x:firmenListe){
            if(x.getId()==pFirma){
                ergebniss=EXISTIERT;
            }
        }
        return ergebniss;
    }
    private int existProdukt(int pProdukt){
        int ergebniss=EXISTIERT_NICHT;
        for(Produkt x:produktListe){
            if(x.getiD()==pProdukt){
                ergebniss=EXISTIERT;
            }
        }
        return ergebniss;
    }
}
