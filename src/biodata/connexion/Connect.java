package biodata.connexion;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

    public class Connect{
        private static Connection con;
        public static Connection connect(){
            if(con == null){
                MysqlDataSource db = new MysqlDataSource();
                db.setDatabaseName("biodata");
                db.setUser("root");
                db.setPassword("");
                try{
                    con = db.getConnection();
                }
                catch(SQLException e){
                }
            }
            return con;
        }
    }
