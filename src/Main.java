import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    static Datenbank db;
    private static String FILE="./src/files/movieproject2020.db";

    public static void main(String[] args) throws FileNotFoundException {
        db = new Datenbank();
        db.importFile(new FileReader(FILE));
        db.checkFreundschaft();
        if (args.length-1 >= 0) {
            db.checkArgument(args[0]);
        } else {
            System.out.println("Eingegebes Argument Fehlerhaft! Mögliche Argumente:\n\b--personensuche=\"[name]\"\n\b--produktsuche=\"[produktname]\"\n\b--produktnetzwerk=\"[personen_id]\"\n\b--firmennetzwerk=\"[personen_id]\"\n");
        }
    }

}
