package biodata.controller;

import biodata.implement.implGrafik;
import biodata.interfaces.interGrafik;
import biodata.model.modelGrafik;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class grafikController implements Initializable {
    @FXML
    private TableView<modelGrafik> tableDetail;
    @FXML
    private TableColumn<modelGrafik, String> colDetailAnnee;
    @FXML
    private TableColumn<modelGrafik, String> colDetailNombre;
    @FXML
    private StackedBarChart bar;
    @FXML
    private NumberAxis barY;
    @FXML
    private CategoryAxis barX;
    @FXML
    private Button btnRafraichir;
    ObservableList<Object> dataGrafik = FXCollections.observableArrayList();
    ObservableList<modelGrafik> dataDetail = FXCollections.observableArrayList();
    interGrafik crudGrafik = new implGrafik();

    @Override
    public void initialize(URL url, ResourceBundle rb){
       colDetailNombre.setCellValueFactory((TableColumn.CellDataFeatures<modelGrafik, String> cellData) -> cellData.getValue() .nombredenomsProperty().asString());
       colDetailAnnee.setCellValueFactory((TableColumn.CellDataFeatures<modelGrafik, String> cellData) -> cellData.getValue() .anneeProperty());
        AwesomeDude.setIcon(btnRafraichir, AwesomeIcon.CHAIN_BROKEN, "15px");
        affichage();
    }
    private void affichage(){
        dataDetail = crudGrafik.getAnneeDeNaissance();
        dataGrafik = crudGrafik.anneedenaissanceToGrafik();
        bar.setData(dataGrafik);
        tableDetail.setItems(dataDetail);
    }

    @FXML
    private void rafraichir(ActionEvent e){
        bar.setAnimated(true);
        barY.setAnimated(true);
        barX.setAnimated(false);
        affichage();
    }
}
