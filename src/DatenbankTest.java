import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatenbankTest {

    Datenbank db;
    public DatenbankTest(){
        db = new Datenbank();

    }

    @Test
    public void testProduktnetzwerk(){


    }

    @Test
    public void testPersonenSuche(){
        ArrayList<String>ergebnisRichtig = new ArrayList<>();
        ergebnisRichtig.add("ID: 15, Name: Simon Kolloch, Geschlecht: Male");
        ArrayList<String>ergebnisFalsch = new ArrayList<>();
        ergebnisFalsch.add("Keine Person gefunden!");
        db.personenListe.add(new Person(15,"Simon Kolloch","Male"));
        assertEquals(ergebnisRichtig,db.personenSuche("Simon Kolloch"));
        db.ausgabe(ergebnisRichtig);
        assertEquals(ergebnisFalsch,db.personenSuche("Peter"));
        db.ausgabe(ergebnisFalsch);
    }

    @Test
    public void testProduktSuche(){
        ArrayList<String>ergebnisFalsch = new ArrayList<>();
        ergebnisFalsch.add("Kein Produkt gefunden!");
        ArrayList<String>ergebnisRichtig = new ArrayList<>();

        db.produktListe.add(new Produkt(15, "Samsung Galaxy S20"));
        db.produktListe.get(0).setFirma(new Firma(10, "Samsung"));
        ergebnisRichtig.add("ID: 15, Name: Samsung Galaxy S20, Firma: Samsung");

        assertEquals(ergebnisRichtig,db.produktSuche("Samsung Galaxy S20"));
        assertEquals(ergebnisFalsch,db.produktSuche("Samsung Galaxy S100"));
    }


    @Test
    public void testGetCode(){
        assertEquals(10,db.getCode("New_Entity: \"person_id\", \"person_name\", \"person_gender\""));
        assertEquals(20, db.getCode( "New_Entity: \"product_id\",\"product_name\""));
        assertEquals(30, db.getCode("New_Entity: \"company_id\",\"company_name\""));
        assertEquals(40,db.getCode("New_Entity: \"person1_id\",\"person2_id\""));
        assertEquals(50, db.getCode("New_Entity: \"person_id\",\"product_id\""));
        assertEquals(60, db.getCode("New_Entity: \"product_id\",\"company_id\""));
        assertEquals(98,db.getCode("New_Entity: \"product_name\",\"product_id\""), "Syntax Error");
    }

    @Test
    public void testImport() throws FileNotFoundException {
        String file = "./src/files/test.db";
        db.importFile(new FileReader(file));
        ArrayList<Person> vergleichPersonen = new ArrayList<>();
        ArrayList<Produkt>vergleichProdukt = new ArrayList<>();
        ArrayList<Firma>vergleichFima = new ArrayList<>();

        //Test, ob Personen richtig Importiert worden sind
        vergleichPersonen.add(new Person(0, "Simon Kolloch","Male"));
        vergleichPersonen.add(new Person(1,"Max Mustermann","Male"));
        vergleichPersonen.add(new Person(2,"Maxi Musterfrau","Female"));
        assertEquals(vergleichPersonen.get(0).getName(),db.personenListe.get(0).getName());
        assertEquals(vergleichPersonen.get(1).getName(),db.personenListe.get(1).getName());
        assertEquals(vergleichPersonen.get(2).getName(),db.personenListe.get(2).getName());

        //Test, ob Produkte richtig Importiert worden sind
        vergleichProdukt.add(new Produkt(211,"Google Nexus 5"));
        vergleichProdukt.add(new Produkt(212,"Google Nexus 7"));
        assertEquals(vergleichProdukt.get(0).getName(),db.produktListe.get(0).getName());
        assertEquals(vergleichProdukt.get(1).getName(),db.produktListe.get(1).getName());

        //Test, ob Firmen richtig importiert worden sidn
        vergleichFima.add(new Firma(200,"Apple"));
        vergleichFima.add(new Firma(201,"Google"));
        vergleichFima.add(new Firma(202, "Samsung"));
        assertEquals(vergleichFima.get(0).getName(),db.firmenListe.get(0).getName());
        assertEquals(vergleichFima.get(1).getName(),db.firmenListe.get(1).getName());
        assertEquals(vergleichFima.get(2).getName(),db.firmenListe.get(2).getName());

        //check, ob Freundschaft erstellt worden ist
        assertEquals(vergleichPersonen.get(0).getName(), db.personenListe.get(1).getFreunde().get(0).getName());
        assertEquals(vergleichPersonen.get(1).getName(), db.personenListe.get(0).getFreunde().get(0).getName());

        //check, ob Produkt gekauft worden ist
        assertEquals(vergleichProdukt.get(0).getName(),db.personenListe.get(0).getGekaufteProdukte().get(0).getName());
        assertEquals(vergleichProdukt.get(1).getName(),db.personenListe.get(1).getGekaufteProdukte().get(0).getName());

        //Check, ob Firma Produkt herstellt
        assertEquals(vergleichProdukt.get(0).getName(), db.firmenListe.get(1).getProdukte().get(0).getName());
        assertEquals(vergleichProdukt.get(1).getName(), db.firmenListe.get(1).getProdukte().get(1).getName());
        //Gegenseitigkeit: Produkt wird von.. hergestellt
        assertEquals(vergleichFima.get(1).getName(), db.produktListe.get(0).getFirma().getName());
        assertEquals(vergleichFima.get(1).getName(), db.produktListe.get(1).getFirma().getName());

    }
}
