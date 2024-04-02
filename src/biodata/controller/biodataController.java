package biodata.controller;

import biodata.implement.implBiodata;
import biodata.interfaces.interBiodata;
import biodata.model.modelBiodata;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;


public class biodataController implements Initializable {
    @FXML
    private Tab tabGrafiknaissance, tabBiodata;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNom;
    @FXML
    private TextArea txtAdresse;
    @FXML
    private DatePicker dateNaissance;
    @FXML
    private Button btnEnregistrer;
    @FXML
    private TableView<modelBiodata> tableData;
    @FXML
    private TableColumn<modelBiodata, String> colId;
    @FXML
    private TableColumn<modelBiodata, String> colNom;
    @FXML
    private TableColumn<modelBiodata, String> colAdresse;
    @FXML
    private TableColumn<modelBiodata, String> colAnnee;
    @FXML
    private TableColumn colAction;
    @FXML
    private TextField txtChercher;
    @FXML
    private Button btnRafraichir;
    @FXML
    private AnchorPane paneLoadGrafik;
    interBiodata crudData = new implBiodata();
    ObservableList<modelBiodata> listData;
    private String StatusCode,statusclic;
    ObservableList<modelBiodata> listDelete;

    @Override
    /**
     * @param URL le lien url
     * @param Ressource Bundle
     */
    public void initialize(URL url, ResourceBundle rb){
        colId.setCellValueFactory((TableColumn.CellDataFeatures<modelBiodata, String>cellData) -> cellData.getValue().idProperty());
        colNom.setCellValueFactory((TableColumn.CellDataFeatures<modelBiodata, String> cellData) -> cellData.getValue().nomProperty());
        colAdresse.setCellValueFactory((TableColumn.CellDataFeatures<modelBiodata, String> cellData) -> cellData.getValue().adresseProperty());
        colAnnee.setCellValueFactory(new PropertyValueFactory("formatDate"));
        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean>
            call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() !=null);
            }
        });

        colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>(){
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p){
                return new ButtonCell(tableData);
            }
        });
        listData = FXCollections.observableArrayList();
        AwesomeDude.setIcon(btnEnregistrer, AwesomeIcon.CHECK_SQUARE,"15px");
        AwesomeDude.setIcon(btnRafraichir, AwesomeIcon.CHECK_SQUARE,"15px");
        dateNaissance.setValue(LocalDate.of(1990,01,01));
        StatusCode = "0";
        statusclic = "0";
        affichageDonnees();
        autoId();
        tableData.getSelectionModel().clearSelection();
        loadGrafik();
    }

    //load view grafik.fxml
    private void loadGrafik(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent p = fxmlLoader.load(getClass().getResourceAsStream("/biodata/view/grafik.fxml"));
            paneLoadGrafik.getChildren().add(p);
        }catch (IOException e){
        }
    }

    /**
     *
     * @param alertType
     * @param s
     */
    private void dialog(Alert.AlertType alertType,String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }

    private void clear(){
        txtId.clear();
        txtNom.clear();
        txtAdresse.clear();
        txtChercher.clear();
        dateNaissance.setValue(LocalDate.of(1990, 01, 01));
        StatusCode = "0";
    }

    private void affichageDonnees(){
        listData = crudData.getAll();
        tableData.setItems(listData);
    }

    /**
     *fixation de l'ID
     */
    private void autoId(){
        modelBiodata m = new modelBiodata();
        crudData.autoId(m);
        txtId.setText(m.getId());
    }

    @FXML
    private void enregistrer(ActionEvent event){
        modelBiodata m = new modelBiodata();
        m.setId(txtId.getText());
        m.setNom(txtNom.getText());
        m.setAdresse((txtAdresse.getText()));
        m.setDateDeNaissance(Date.valueOf(dateNaissance.getValue()));
        if (StatusCode.equals("0")){
            crudData.insert(m);
        }else{
            crudData.update(m);
        }
        dialog(Alert.AlertType.INFORMATION, "Données sauvegardées");
        affichageDonnees();
        clear();
        autoId();
    }
    @FXML
    /**
     *
     * méthode de gestion d'évènement clic
     */
    private void klikTableData(MouseEvent event){
        if (statusclic.equals("1")){
            StatusCode = "1";
            try{
                modelBiodata clic = tableData.getSelectionModel().getSelectedItems().get(0);
                txtId.setText(clic.getId());
                txtNom.setText(clic.getNom());
                txtAdresse.setText(clic.getAdresse());
                dateNaissance.setValue(LocalDate.parse(clic.getDateDeNaissance().toString()));
            }catch (Exception e){

            }
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void rechercher(KeyEvent event){
        listData = crudData.Rechercheparnom(txtChercher.getText());
        tableData.setItems(listData);
    }
    /**
     *
     * @param event
     */
    @FXML
    private void actualiser(ActionEvent event){
        clear();
        affichageDonnees();
        autoId();
    }

    private class ButtonCell extends TableCell<Object, Boolean>{
        final Hyperlink cellButtonDelete = new Hyperlink("Supprimer");
        final Hyperlink cellButtonEdit = new Hyperlink("Editer");
        final HBox hb = new HBox(cellButtonDelete,cellButtonEdit);
        ButtonCell (final TableView tblView){
            hb.setSpacing(4);

            //cell delete
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                statusclic = "1";
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                klikTableData(null);
                modelBiodata m = new modelBiodata();
                m.setId(txtId.getText());
                crudData.delete(m);
                affichageDonnees();
                clear();
                autoId();
                dialog(Alert.AlertType.INFORMATION, "Les données ont bien été supprimées");
                statusclic = "0";
                StatusCode = "0";
            });

            //cell edit
            cellButtonEdit.setOnAction((ActionEvent event) -> {
                statusclic = "1";
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                klikTableData(null);
                statusclic = "0";
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty){
            super.updateItem(t, empty);
            if (!empty){
                setGraphic(hb);
            }else{
                setGraphic(null);
            }
        }
    }
}