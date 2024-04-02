package biodata.implement;

import biodata.connexion.Connect;
import biodata.interfaces.interBiodata;
import biodata.model.modelBiodata;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Création de ma classe implBiodata qui implémente l'interface interBiodata permettant de redéfinir le CRUD (les méthodes déclarées dans l'interface)
 */

public class implBiodata implements interBiodata {
    Connect k;

    /**
     * @param m
     */
    @Override
    public void insert(modelBiodata m) {
        k = new Connect();
        PreparedStatement ps;
        try {
            ps = k.connect().prepareStatement("insert into tablebiodata values(?,?,?,?)");
            ps.setString(1, m.getId());
            ps.setString(2, m.getNom());
            ps.setString(3, m.getAdresse());
            ps.setDate(4, (Date) m.getDateDeNaissance());
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(implBiodata.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @param m
     */
    @Override
    public void delete(modelBiodata m) {
        k = new Connect();
        PreparedStatement ps;
        try {
            ps = k.connect().prepareStatement("delete from tablebiodata where id = ?");
            ps.setString(1, m.getId());
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(implBiodata.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void update(modelBiodata m) {
        k = new Connect();
        PreparedStatement ps;
        try {
            ps = k.connect().prepareStatement("update tablebiodata set nom=?, adresse=?, datedenaissance=? where id = ?");
            ps.setString(4, m.getId());
            ps.setString(1, m.getNom());
            ps.setString(2, m.getAdresse());
            ps.setDate(3, (Date) m.getDateDeNaissance());
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(implBiodata.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @return
     */
    @Override
    public ObservableList<modelBiodata> getAll() {
        k = new Connect();
        ObservableList<modelBiodata> listData = FXCollections.observableArrayList();
        try {
            String sql = "select * from tablebiodata";
            ResultSet rs = k.connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                modelBiodata m = new modelBiodata();
                m.setId(rs.getString(1));
                m.setNom(rs.getString(2));
                m.setAdresse(rs.getString(3));
                m.setDateDeNaissance(rs.getDate(4));
                listData.add(m);
            }
        } catch (Exception ex) {
            Logger.getLogger(implBiodata.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    /**
     * @param a
     * @return
     */
    @Override
    public ObservableList<modelBiodata> Rechercheparnom(String a) {
        k = new Connect();
        ObservableList<modelBiodata> listData = FXCollections.observableArrayList();
        try {
            String sql = "select * from tablebiodata where nom like '%" + a + "%'";
            ResultSet rs = k.connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                modelBiodata m = new modelBiodata();
                m.setId(rs.getString(1));
                m.setNom(rs.getString(2));
                m.setAdresse(rs.getString(3));
                m.setDateDeNaissance(rs.getDate(4));
                listData.add(m);
            }
        } catch (Exception ex) {
            Logger.getLogger(implBiodata.class.getName()).log(Level.SEVERE, null, ex);
        }

    return listData;
}
/**
 *
 *@param
 */
@Override
public void autoId(modelBiodata m){
    k =new Connect();
    try{
        ResultSet rs = k.connect().createStatement().executeQuery("select * from tablebiodata");
        while (rs.next()){
            String code = rs.getString(1).substring(2);
            String auto = ""+(Integer.parseInt(code)+1);
            String nol = "";
            if (auto.length()==1){
                nol = "00";
            }else if (auto.length()==2){
                nol = "0";
            }else if (auto.length()==3){
                nol = "";
            }
            m.setId("B."+nol+auto);
        }
        if (m.getId()==null){
            m.setId("B.001");
        }
    }catch (SQLException ex){
        Logger.getLogger(implBiodata.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}