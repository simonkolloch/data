public class Produkt {
    public int iD;
    public String name;
    public Firma firma;

    public Produkt(int pID, String pName){
        this.iD=pID;
        this.name=pName;
    }

    public int getiD() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }
}
