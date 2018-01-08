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
import Model.Scanner;
import Model.Scanning;
import Model.WagonInTrain;
import Model.WagonOnStation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                + " and cas_do is null"
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

    public boolean setWagonService(String id, boolean value) {
        String set = addApostrofs("N");
        if (value) {
            set = addApostrofs("Y");
        }
        id = addApostrofs(id);
        return DbManager.updateSql("Update Vozen set"
                + " v_prevadzke = " + set
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

    public boolean endScannWagon(String id) {
        id = addApostrofs(id);
        return DbManager.updateSql("Update snimanie set"
                + " cas_do = sysdate"
                + " where cas_do is null"
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

    public ArrayList<WagonInTrain> getLastScannedWagonsInTrains(String contition) {
        ArrayList<WagonInTrain> result = new ArrayList<>();
        String query = "select"
                + " sn.id_vlaku as idVlaku,"
                + " sv.id_vozna as idVozna,"
                + " max(sn.cas_od) as casOd"
                + " from Vozen v"
                + " join Sprava_voznov sv on (v.id_vozna = sv.id_vozna)"
                + " join Vlak vk on (vk.id_vlaku = sv.id_vlaku)"
                + " join Snimanie sn on(sn.id_vlaku = vk.ID_VLAKU)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " join Stanica using(id_stanice)"
                + " where sv.datum_do is null" + contition
                + " group by sn.id_vlaku, sv.id_vozna";
        ResultSet rs = DbManager.querySQL(query);
        try {
            if (rs != null) {
                while (rs.next()) {
                    int idTrain = rs.getInt("idVlaku");
                    String idWagon = rs.getString("idVozna");
                    Timestamp dateFrom = rs.getTimestamp("casOd");
                    WagonInTrain wagonInTrain = new WagonInTrain(idWagon, "" + idTrain, null, null, new Date(dateFrom.getTime()), null, null, null);
                    result.add(wagonInTrain);
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

    public int getCurrentTrainId(String wagonId) {
        int result = -1;
        String query = "SELECT"
                + " id_vlaku"
                + " FROM"
                + " Sprava_voznov"
                + " WHERE id_vozna like " + addApostrofs(wagonId)
                + " and datum_do is null";

        ResultSet rs = DbManager.querySQL(query);
        try {
            if (rs != null) {

                while (rs.next()) {
                    //Retrieve by column name
                    result = rs.getInt("id_vlaku");
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

    public int getLastScannerIdOfTrain(int id) {
        DBManager dm = new DBManager();
        int result = -1;
        String query = "select"
                + " id_snimacu from snimanie"
                + " where id_vlaku = " + id
                + " and cas_do is null "
                + " or cas_do = "
                + " (select max(cas_do) from snimanie"
                + " where id_vlaku = " + id + ")";

        ResultSet rs = dm.querySQL(query);
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

    public Scanner getScannerLocation(int id) {
        DBManager dm = new DBManager();
        Scanner s = null;
        String query = "select"
                + " id_snimacu,"
                + " zem_sirka,"
                + " zem_vyska"
                + " from snimanie"
                + " where id_snimacu = " + id;

        ResultSet rs = dm.querySQL(query);
        try {
            if (rs != null) {
                while (rs.next()) {
                    //Retrieve by column name
                    s = new Scanner(rs.getInt("id_snimacu"), rs.getDouble("zem_sirka"), rs.getDouble("zem_vyska"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    public Scanning getScanning(Date dateFrom, int idTrain) {
        DBManager dm = new DBManager();
        Scanning s = null;
        String query = "select"
                + " cas_od,"
                + " id_rusna,"
                + " id_snimacu,"
                + " id_vozna,"
                + " cas_do,"
                + " id_vlaku"
                + " from snimanie"
                + " where TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS') = " + addApostrofs(Formater.format(dateFrom))
                + " and id_vlaku = " + idTrain;

        ResultSet rs = dm.querySQL(query);
        try {
            if (rs != null) {
                while (rs.next()) {
                    //Retrieve by column name
                    s = new Scanning(rs.getDate("cas_od"), "" + rs.getInt("id_rusna"), rs.getInt("id_snimacu"), rs.getString("id_vozna"), rs.getDate("cas_do"), "" + rs.getInt("id_vlaku"));
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    public List<String> getCurrentWagonOnStation(int idStation) {

        String wagonsOutServiceOnTrail = "SELECT"
                + " id_vozna"
                + " FROM Typ_vozna"
                + " join Vozen using(id_typu)"
                + " join Vozen_spolocnost using(id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Snimanie using(id_vozna)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " where id_stanice like " + idStation
                + " AND cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)";

        String wagonsOutServiceOnstation = "SELECT"
                + "                  id_vozna"
                + "                  FROM Typ_vozna"
                + "                  join Vozen using(id_typu)"
                + "                  join Vozen_spolocnost using(id_vozna)"
                + "                  join Spolocnost using(id_spolocnosti)"
                + "                  join Snimanie using(id_vozna)"
                + "                  join Snimac using(id_snimacu)"
                + "                  join Stanica using(id_snimacu)"
                + " where id_stanice like " + idStation
                + " AND cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)";

        List<String> result = new ArrayList<>();

        String[] selects = {wagonsOutServiceOnTrail, wagonsOutServiceOnstation};

        for (int i = 0; i < selects.length; i++) {
            ResultSet rs = DbManager.querySQL(selects[i]);
            try {
                if (rs != null) {
                    while (rs.next()) {
                        String idWagon = rs.getString("id_vozna");
                        result.add(idWagon);
                    }
                    rs.close();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<WagonOnStation> getWagonsOnStation(int idStation, String type, String company) {
        if (!isNullOrEmpty(type)) {
            type = " AND Typ_vozna.nazov like " + addApostrofs(type);
        }
        if (!isNullOrEmpty(company)) {
            company = " AND Spolocnost.nazov like " + addApostrofs(company);
        }

        String wagonsOutServiceOnstation = "SELECT"
                + " Stanica.nazov as stanica_nazov,"
                + " id_vozna,"
                + " Spolocnost.nazov as spolocnost_nazov,"
                + " Typ_vozna.nazov as typ_vozna,"
                + " id_snimacu"
                + "                  FROM Typ_vozna"
                + "                  join Vozen using(id_typu)"
                + "                  join Vozen_spolocnost using(id_vozna)"
                + "                  join Spolocnost using(id_spolocnosti)"
                + "                  join Snimanie using(id_vozna)"
                + "                  join Snimac using(id_snimacu)"
                + "                  join Stanica using(id_snimacu)"
                + " where id_stanice like " + idStation + type + company
                + " AND cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)";

        List<WagonOnStation> result = new ArrayList<>();

        String[] selects = {wagonsOutServiceOnstation};

        for (int i = 0; i < selects.length; i++) {
            ResultSet rs = DbManager.querySQL(selects[i]);
            try {
                if (rs != null) {
                    while (rs.next()) {
                        String idWagon = rs.getString("id_vozna");
                        String typeDB = rs.getString("typ_vozna");
                        String companyDB = rs.getString("spolocnost_nazov");
                        String stationDb = rs.getString("stanica_nazov");
                        int idScanner = rs.getInt("id_snimacu");
                        WagonOnStation wagonOnStation = new WagonOnStation(idWagon, typeDB, companyDB, idScanner, stationDb);
                        result.add(wagonOnStation);
                    }
                    rs.close();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public boolean insertWagonToWagonManager(String wagonId, int trainId, Date dateFrom, Date dateTo) {
        String query
                = "insert into Sprava_voznov values("
                + addApostrofs(wagonId) + ","
                + trainId + ","
                + "TO_DATE(" + addApostrofs(Formater.format(dateFrom)) + ", 'DD.MM.YYYY HH24:MI:SS')" + ","
                + dateTo + ")";

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

    public boolean scannInsertedWagon(InsertWagon wagon) {
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

    public boolean scannWagon(Scanning scanner) {
        String wagonId = (scanner.getWagonId() == null) ? null : addApostrofs(scanner.getWagonId());
        String query
                = "insert into Snimanie values("
                + "TO_DATE(" + addApostrofs(Formater.format(scanner.getDateFrom())) + ", 'DD.MM.YYYY HH24:MI:SS')" + ","
                + scanner.getLocomotiveId() + ","
                + scanner.getScannerId() + ","
                + wagonId + ","
                + scanner.getTrainId() + ","
                + scanner.getDateTo() + ")";

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

    public boolean uniqueTrainId(int id) {
        boolean result = false;

        ResultSet rs = DbManager.querySQL("SELECT"
                + " count(*) as count"
                + " FROM"
                + " Vlak"
                + " WHERE id_vlaku = " + id
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

    public boolean isNullOrEmpty(String term) {
        return term == null || term.equals("") || term.equals("null");
    }

    public List<WagonOnStation> getNearestWagonsOnRalsStation(int stationId, String wagonType, String company) {
        if (!isNullOrEmpty(wagonType)) {
            wagonType = " AND Typ_vozna.nazov like " + addApostrofs(wagonType);
        }
        if (!isNullOrEmpty(company)) {
            company = " AND Spolocnost.nazov like " + addApostrofs(company);
        }

        String wagonsOutServiceOnstation = "SELECT"
                + " Stanica.nazov as stanica_nazov,"
                + " id_vozna,"
                + " Spolocnost.nazov as spolocnost_nazov,"
                + " Typ_vozna.nazov as typ_vozna,"
                + " Kolajovy_usek.id_snimacu as ku_id_snimacu"
                + " FROM Typ_vozna"
                + " join Vozen using(id_typu)"
                + " join Vozen_spolocnost using(id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Snimanie using(id_vozna)"
                + " join Snimac on(Snimac.id_snimacu = Snimanie.id_snimacu)"
                + " join Kolajovy_usek on(Kolajovy_usek.id_snimacu = Snimac.id_snimacu)"
                + " join Stanica using(id_stanice)"
                + " where id_stanice = " + stationId + wagonType + company + " AND cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)"
                + " and dlzka = ("
                + " SELECT min(dlzka)"
                + " FROM Typ_vozna"
                + " join Vozen using(id_typu)"
                + " join Vozen_spolocnost using(id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Snimanie using(id_vozna)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " where id_stanice = " + stationId + wagonType + company + " AND cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)"
                + ")";

        List<WagonOnStation> result = new ArrayList<>();

        String[] selects = {wagonsOutServiceOnstation};

        for (int i = 0; i < selects.length; i++) {
            ResultSet rs = DbManager.querySQL(selects[i]);
            try {
                if (rs != null) {
                    while (rs.next()) {
                        String idWagon = rs.getString("id_vozna");
                        String typeDB = rs.getString("typ_vozna");
                        String companyDB = rs.getString("spolocnost_nazov");
                        String stationDb = rs.getString("stanica_nazov");
                        int idScanner = rs.getInt("ku_id_snimacu");
                        WagonOnStation wagonOnStation = new WagonOnStation(idWagon, typeDB, companyDB, idScanner, stationDb);
                        result.add(wagonOnStation);
                    }
                    rs.close();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
