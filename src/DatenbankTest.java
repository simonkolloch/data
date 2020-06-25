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
        db.personenListe.add(new Person(15,"Simon Kolloch", "Male"));

    }

    @Test
    public void testPersonenSuche(){
        db.personenListe.add(new Person(15,"Simon Kolloch","Male"));
        assertEquals("ID: 15, Name: Simon Kolloch, Geschlecht: Male",db.personenSuche("Simon Kolloch"));
        assertEquals("Keine Person gefunden!",db.personenSuche("Peter"));
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
    public void testAddFriend(){
        db.personenListe.add(new Person(1,"Simon kolloch","Male"));
        db.personenListe.add(new Person(2,"Max Mustermann","Male"));


    }

    @Test
    public void testImport() throws FileNotFoundException {
        String file = "./src/files/test.db";
        db.importFile(new FileReader(file));




    }
}
