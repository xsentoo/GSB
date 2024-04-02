package biodata.implement;

import biodata.connexion.Connect;
import biodata.interfaces.interGrafik;
import biodata.model.modelGrafik;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 *  Création de ma classe implBiodata qui implémente l'interface interGrafik permettant de redéfinir les méthodes déclarées dans interGrafik.
 *
 */

public class implGrafik implements interGrafik {
    Connect conn;

    @Override
    public ObservableList<modelGrafik> getAnneeDeNaissance(){
        conn = new Connect();
        ObservableList<modelGrafik> listData = FXCollections.observableArrayList();
        try{
            String sql = "select distinct(extract(year from datedenaissance)) as annee, "
                    + "count(nom) as nombredepersonnes "
                    + "from tablebiodata "
                    + "group by annee "
                    + "order by annee";
            ResultSet rs = conn.connect().createStatement().executeQuery(sql);
            while (rs.next()){
                modelGrafik m = new modelGrafik();
                m.setAnnee(rs.getString("annee"));
                m.setNombredenoms(rs.getInt("nombredepersonnes"));
                listData.add(m);
            }
        }catch (Exception e){
        }
        return listData;
    }
    @Override
    public ObservableList<Object> anneedenaissanceToGrafik(){
        ObservableList<Object> barCar = FXCollections.observableArrayList();
        try{
            conn = new Connect();
            String sql = "select distinct(extract(year from datedenaissance)) as annee, "
                    + "count(nom) as nombredepersonnes "
                    + "from tablebiodata "
                    + "group by annee "
                    + "order by annee";
            ResultSet rs =conn.connect().createStatement().executeQuery(sql);
            while (rs.next()){
                XYChart.Series<String, Integer> aSeries = new XYChart.Series<>();
                aSeries.getData().add(new XYChart.Data(rs.getString("annee"), rs.getInt("nombredepersonnes")));
                barCar.add(aSeries);
            }
        }catch (Exception e){

        }
        return barCar;
    }
}
