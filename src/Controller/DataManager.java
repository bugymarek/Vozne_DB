/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InsertWagon;
import Model.Record;
import Model.User;
import Model.PersonNested;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.DBManager;

/**
 *
 * @author Bugy
 */
public class DataManager {

    private DBManager DbManager;
    SimpleDateFormat Formater = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public DataManager(DBManager dbManager) {
        DbManager = dbManager;
    }

    public String getFunctionName(int id) {
        String result = null;
        ResultSet rs = DbManager.querySQL("SELECT"
                + "  popis"
                + " FROM"
                + " Funkcia"
                + " WHERE id_funkcie like " + id
        );
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                result = rs.getString("popis");
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getFunctionId(String name) {
        int result = -1;
        ResultSet rs = DbManager.querySQL("SELECT"
                + "  id_funkcie"
                + " FROM"
                + " Funkcia"
                + " WHERE popis like " + addApostrofs(name)
        );
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                result = rs.getInt("id_funkcie");
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean isWagonConnectToTrain(String id) {
        boolean result = true;
        id = addApostrofs(id);
        ResultSet rs = DbManager.querySQL("SELECT"
                + " *"
                + " FROM"
                + " Snimanie"
                + " WHERE id_vozna = " + id
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result = false;
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean setWagonOutOfService(String id) {
        id = addApostrofs(id);
        return DbManager.updateSql("Update Vozen set"
                + " v_prevadzke = 'N'"
                + " where id_vozna = " + id
        );
    }
    
    public boolean disconnectWagonInTrain(String id) {
        id = addApostrofs(id);
        return DbManager.updateSql("Update Sprava_voznov set"
                + " datum_do = sysdate"
                + " where datum_do is null" 
                + " and id_vozna = " + id
        );
    }

    public ArrayList<String> getStationNames() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " nazov"
                + " FROM"
                + " Stanica"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("nazov"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Integer> getScannersOfStation(String name) {
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("select"
                + " Stanica.id_snimacu as stanica_id_snimacu,"
                + " Kolajovy_usek.id_snimacu as kolajovy_usek_id_snimacu"
                + " from stanica"
                + " join kolajovy_usek using(id_stanice)"
                + " where nazov like " + addApostrofs(name)
                + " and kolajovy_usek.id_snimacu not in("
                + " select id_snimacu"
                + " from Kolajovy_usek"
                + " group by id_snimacu"
                + " having count(id_snimacu) > 1)"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getInt("kolajovy_usek_id_snimacu"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getWagonId() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " id_vozna"
                + " FROM"
                + " Vozen"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("id_vozna"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getFunctionNames() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " popis"
                + " FROM"
                + " Funkcia"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("popis"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getStationId(String name) {
        int result = -1;
        String query = "SELECT"
                + "  id_stanice"
                + " FROM"
                + " Stanica"
                + " WHERE nazov like " + addApostrofs(name);

        ResultSet rs = DbManager.querySQL(query);
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                result = rs.getInt("id_stanice");
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getStationScannerId(String name) {
        int result = -1;
        String query = "SELECT"
                + "  id_snimacu"
                + " FROM"
                + " Stanica"
                + " WHERE nazov like " + addApostrofs(name);

        ResultSet rs = DbManager.querySQL(query);
        try {
            if (rs != null) {

                while (rs.next()) {
                    //Retrieve by column name
                    result = rs.getInt("id_snimacu");
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getWagonTypeId(String name) {
        int result = -1;
        String query = "SELECT"
                + " id_typu"
                + " FROM"
                + " Typ_vozna"
                + " WHERE nazov like " + addApostrofs(name);

        ResultSet rs = DbManager.querySQL(query);
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                result = rs.getInt("id_typu");
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getCompanyId(String name) {
        DBManager dm = new DBManager();
        int result = -1;
        name = addApostrofs(name);
        String query = "SELECT"
                + " id_spolocnosti"
                + " FROM"
                + " Spolocnost"
                + " WHERE nazov like " + name;

        ResultSet rs = dm.querySQL(query);
        try {
            if (rs != null) {
                while (rs.next()) {
                    //Retrieve by column name
                    result = rs.getInt("id_spolocnosti");
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getStationName(int id) {
        String result = null;
        String query = "SELECT"
                + " nazov"
                + " FROM"
                + " Stanica"
                + " WHERE id_stanice like " + id;

        ResultSet rs = DbManager.querySQL(query);
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                result = rs.getString("nazov");
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getWagonTypes() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " nazov"
                + " FROM"
                + " Typ_vozna"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("nazov"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getCompanyNames() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " nazov"
                + " FROM"
                + " Spolocnost"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("nazov"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getTrainIds() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " id_vlaku,"
                + " nazov"
                + " FROM"
                + " Vlak"
                + " ORDER BY id_vlaku asc"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("id_vlaku") + "-" + rs.getString("nazov"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getTrainTypes() {
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " nazov"
                + " FROM"
                + " Typ_vlaku"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("nazov"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean insertUser(User user) {
        boolean result = false;
        String query
                = "INSERT INTO UZIVATEL VALUES (" + addApostrofs(user.getLogin()) + ","
                + addApostrofs(user.getHash()) + ","
                + addApostrofs(user.getRc()) + ","
                + user.getIdFunction();

        byte[] foto = null;
        try {
            foto = (user.getFoto() == null) ? null : user.getFoto().getBytes(1, (int) user.getFoto().length());
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (foto == null) {
            query += "," + null + ")";
            result = DbManager.insertSql(query);
        } else {
            query += ",?)";
            result = DbManager.insertWithBlobSql(query, foto);
        }
        return result;
    }

    public void insertPerson(User user) {
        String query
                = "INSERT INTO OSOBA VALUES (" + addApostrofs(user.getRc()) + ","
                + addApostrofs(user.getName()) + ","
                + addApostrofs(user.getLastName()) + ")";

        DbManager.insertSql(query);
    }

    public boolean insertWagon(InsertWagon wagon) {
        String query
                = "insert into Vozen values("
                + addApostrofs(wagon.getIdWagon()) + ","
                + wagon.getType() + ","
                + wagon.getWeight() + ","
                + "'N')";

        return DbManager.insertSql(query);
    }

    public boolean insertWagonIntoWagonCompany(InsertWagon wagon) {
        String query
                = "insert into Vozen_spolocnost values("
                + addApostrofs(wagon.getIdWagon()) + ","
                + wagon.getCompany() + ","
                + "TO_DATE(" + addApostrofs(Formater.format(wagon.getDate())) + ", 'DD.MM.YYYY HH24:MI:SS')" + ","
                + "null)";

        return DbManager.insertSql(query);
    }

    public boolean scannWagon(InsertWagon wagon) {
        String query
                = "insert into Snimanie values("
                + "TO_DATE(" + addApostrofs(Formater.format(wagon.getDate())) + ", 'DD.MM.YYYY HH24:MI:SS')" + ","
                + "null,"
                + wagon.getScannerId() + ","
                + addApostrofs(wagon.getIdWagon()) + ","
                + "null,"
                + "null)";

        return DbManager.insertSql(query);
    }

    public void insertPersonToNestedTable(PersonNested user) {
        String query
                = "INSERT INTO osoba_nested (rod_cislo, meno, priezvisko, adresa) values ("
                + addApostrofs(user.getRc()) + ","
                + addApostrofs(user.getName()) + ","
                + addApostrofs(user.getLastName()) + ","
                + " adresa_tab(t_adresa("
                + addApostrofs(user.getStreat()) + ","
                + addApostrofs(user.getCity()) + ","
                + addApostrofs(user.getCountry()) + ","
                + addApostrofs(user.getCountryShortCut()) + ")))";

        DbManager.insertSql(query);
    }

    public void insertRecord(Record record) {
        String query
                = "INSERT INTO Zaznamy VALUES (" + null + ","
                + "TO_DATE(" + addApostrofs(Formater.format(record.getDate())) + ", 'DD.MM.YYYY HH24:MI:SS')" + ","
                + addApostrofs(record.getDescription()) + ","
                + addApostrofs(record.getLogIn()) + ")";

        DbManager.insertSql(query);
    }

    public boolean uniqueLogIn(String login) {
        login = addApostrofs(login);
        boolean result = false;
        ResultSet rs = DbManager.querySQL("SELECT"
                + " count(*) as count"
                + " FROM"
                + " Uzivatel"
                + " WHERE login like " + login
        );
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                int count = rs.getInt("count");
                if (count == 0) {
                    result = true;
                }
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean uniqueRC(String rc) {
        rc = addApostrofs(rc);
        boolean result = false;
        ResultSet rs = DbManager.querySQL("SELECT"
                + " count(*) as count"
                + " FROM"
                + " Osoba"
                + " WHERE rod_cislo = " + rc
        );
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                int count = rs.getInt("count");
                if (count == 0) {
                    result = true;
                }
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean uniqueWagonId(String id) {
        id = addApostrofs(id);
        boolean result = false;

        ResultSet rs = DbManager.querySQL("SELECT"
                + " count(*) as count"
                + " FROM"
                + " Vozen"
                + " WHERE id_vozna = " + id
        );
        try {
            if (rs != null) {

                rs.next();
                //Retrieve by column name
                int count = rs.getInt("count");
                if (count == 0) {
                    result = true;
                }
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String addApostrofs(String name) {
        return "'" + name + "'";
    }
}
