package biodata.interfaces;

import biodata.model.modelGrafik;
import javafx.collections.ObservableList;

public interface interGrafik {
    ObservableList<modelGrafik> getAnneeDeNaissance();
    ObservableList<Object> anneedenaissanceToGrafik();
}

