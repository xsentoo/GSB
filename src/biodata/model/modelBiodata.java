package biodata.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class modelBiodata {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty adresse = new SimpleStringProperty();
    private final ObjectProperty<Date> DateDeNaissance = new SimpleObjectProperty<>();
    private String formatdate;

    public modelBiodata(){
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public StringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public Date getDateDeNaissance() {
        return DateDeNaissance.get();
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.DateDeNaissance.set(dateDeNaissance);
    }

    public ObjectProperty DateDeNaissanceProperty() {
        return DateDeNaissance;
    }

    public String getFormatDate() {
        Date date = getDateDeNaissance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String format = df.format(date);
        return format;
    }

    public void setFormatdate(String formatdate) {
        this.formatdate = formatdate;
    }
}
