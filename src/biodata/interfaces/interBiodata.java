package biodata.interfaces;

import biodata.model.modelBiodata;
import javafx.collections.ObservableList;

public interface interBiodata {
    void insert (modelBiodata m);
    void delete (modelBiodata m);
    void update (modelBiodata m );
    ObservableList<modelBiodata> getAll();
    ObservableList<modelBiodata> Rechercheparnom(String a);
    void autoId(modelBiodata m);
}

