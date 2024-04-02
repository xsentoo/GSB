package biodata.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class modelGrafik {

    private final StringProperty annee;
    private final IntegerProperty nombredenoms;


     public modelGrafik(){
         this.annee = new SimpleStringProperty();
         this.nombredenoms = new SimpleIntegerProperty();
     }

    public String getAnnee() {
        return annee.get();
    }

    public void setAnnee(String annee) {
        this.annee.set(annee);
    }

    public StringProperty anneeProperty() {
        return annee;
    }

    public Integer getNombredenoms() {
        return nombredenoms.get();
    }

    public void setNombredenoms(Integer nombredenoms) {
        this.nombredenoms.set(nombredenoms);
    }

    public IntegerProperty nombredenomsProperty() {
        return nombredenoms;
    }
}
