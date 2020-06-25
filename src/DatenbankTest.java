import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatenbankTest {

    int OK=100;
    int NICHT_GEFUNDEN=910;
    int DOPPELT=95;
    Datenbank db;
    public DatenbankTest(){
        db = new Datenbank();

    }


    @Test
    public void testProduktnetzwerk(){
        db.personenListe.add(new Person(15,"Simon Kolloch", "Male"));
        assertEquals(NICHT_GEFUNDEN,db.produktnetzwerk("12"));
        assertEquals(900,db.produktnetzwerk("15"));
    }

    @Test
    public void testPersonenSuche(){
        db.personenListe.add(new Person(15,"Simon Kolloch","Male"));
        assertEquals(OK,db.personenSuche("Simon Kolloch"));
        assertEquals(NICHT_GEFUNDEN,db.personenSuche("11"));
    }

    @Test
    public void testProduktSuche(){
        db.produktListe.add(new Produkt(15,"Samsung Galaxy S20"));
        assertEquals(OK,db.produktSuche("Samsung Galaxy S20"));
        assertEquals(NICHT_GEFUNDEN,db.produktSuche("Samsung Galaxy S100"));
    }

    @Test
    public void testCheckArgument(){
        assertEquals(1,db.checkArgument("--personensuche"));
        assertEquals(2,db.checkArgument("--produktsuche"));
        assertEquals(3,db.checkArgument("--produktnetzwerk"));
        assertEquals(4,db.checkArgument("--firmennetzwerk"));
        assertEquals(5,db.checkArgument("--firmennetzwerk="));
        assertEquals(0,db.checkArgument("ich bin eine Fehlerhafte Eingabe"));
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
        assertEquals(NICHT_GEFUNDEN,db.addFriend(1,0));
        assertEquals(OK,db.addFriend(1,2));
        assertEquals(DOPPELT,db.addFriend(1,2));

    }

    @Test
    public void testImport() throws FileNotFoundException {
        String file = "./src/files/test.db";
        db.importFile(new FileReader(file));

        Person testPerson= new Person(0,"Simon Kolloch", "Male");
        assertEquals(OK, db.personenSuche("simon"));

    }
}
