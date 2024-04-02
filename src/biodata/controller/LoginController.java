package biodata.controller;

import biodata.connexion.Connect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /**
     * variable label lblerrors
     */

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     *
     * @param event
     */
    @FXML
    public void handleButtonAction(MouseEvent event){
        if(event.getSource() == btnSignin){
            //login here
            if (logIn().equals("Succès")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/biodata.fxml")));
                    stage.setScene(scene);
                    stage.show();
                }
            catch(IOException ex){
                    System.err.println(ex.getMessage());
            }

            }
        }
    }
/**
 *
 * @param url
 * @param rb
 */
@Override
    public void initialize(URL url, ResourceBundle rb){
    //TODO
    if (con == null){
        lblErrors.setTextFill(Color.TOMATO);
        lblErrors.setText("Erreur serveur: vérification");
    }
    else{
        lblErrors.setTextFill(Color.GREEN);
        lblErrors.setText("Le serveur est en place: prêt");
    }
}

/**
 *récupérer la connection
 */
public LoginController(){
    con = Connect.connect();
}

//we gonna use string to check for status

    /**
     *
     * @return on va utiliser une chaîne "Succès" ou "Exception" pour vérifier le statut
     */
    private String logIn() {
        String status = "Succès";
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "\n" + "Identifiants vides");
            status = "Erreur";
        } else {
            //query
            String sql = "SELECT * FROM admins WHERE email = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Entrer Email/Password Correct");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "\n" + "Connexion réussi..Redirection..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }
            /**
             *
             * @param color
             * @param text
             */
            private void setLblError (Color color, String text){
                lblErrors.setTextFill(color);
                lblErrors.setText(text);
                System.out.println(text);
            }

        }
