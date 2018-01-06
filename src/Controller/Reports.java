/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ActualyWagonLocation;
import Model.WagonInTrain;
import Model.ActualyWagonLocation;
import Model.GroupOfWagon;
import Model.HistoricalWagonLocation;
import Model.WagonOnStation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import service.DBManager;

/**
 *
 * @author Bugy
 */
public class Reports {

    private DBManager DbManager;
    SimpleDateFormat Formater = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Reports(DBManager dbManager) {
        DbManager = dbManager;
    }

    public List<WagonOnStation> getWagonsOnStation(int idStation, String wagonType, String inService, Date dateFrom, Date datesTo, String company) {
        String datFrom = Formater.format(dateFrom);
        String datTO = Formater.format(datesTo);

        if (!isNullOrEmpty(wagonType)) {
            wagonType = " AND Typ_vozna.nazov like " + addApostrofs(wagonType);
        }
        if (!isNullOrEmpty(inService)) {
            inService = " AND v_prevadzke like " + addApostrofs(inService);
        }
        if (!isNullOrEmpty(company)) {
            company = " AND Spolocnost.nazov like " + addApostrofs(company);
        }

        String wagonsOutService = "SELECT"
                + " id_vozna,"
                + " Spolocnost.nazov as spolocnostNazov,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " v_prevadzke"
                + " FROM Typ_vozna"
                + " join Vozen using(id_typu)"
                + " join Vozen_spolocnost using(id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Snimanie using(id_vozna)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " where id_stanice like " + idStation
                + " AND TO_DATE (TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') BETWEEN to_date('" + datFrom + "','DD.MM.YYYY HH24:MI:SS') AND to_date('" + datTO + "','DD.MM.YYYY HH24:MI:SS')"
                + wagonType + inService + company;

        String wagonsInService = "SELECT"
                + " Vozen.id_vozna,"
                + " Spolocnost.nazov as spolocnostNazov,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " v_prevadzke"
                + " FROM  Typ_vozna"
                + " join Vozen  using(id_typu)"
                + " join Vozen_spolocnost on(Vozen_spolocnost.id_vozna = Vozen.id_vozna )"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Sprava_voznov on(Sprava_voznov.id_vozna = Vozen.id_vozna )"
                + " join Vlak using(id_vlaku)"
                + " join Snimanie using(id_vlaku)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " join Stanica using (id_stanice)"
                + " where id_stanice like " + idStation
                + " AND TO_DATE (TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') BETWEEN to_date('" + datFrom + "','DD.MM.YYYY HH24:MI:SS') AND to_date('" + datTO + "','DD.MM.YYYY HH24:MI:SS')"
                + wagonType + inService + company;
        List<WagonOnStation> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL(wagonsOutService + " UNION " + wagonsInService);

        try {
            if (rs != null) {
                while (rs.next()) {
                    String idWagon = rs.getString("id_vozna");
                    String spolocnostNazov = rs.getString("spolocnostNazov");
                    String inServiceDb = rs.getString("v_prevadzke");
                    String wagonTypeDb = rs.getString("typ_vozna_nazov");
                    WagonOnStation wagonOnStation = new WagonOnStation(idWagon, inServiceDb, wagonTypeDb, spolocnostNazov);
                    result.add(wagonOnStation);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<ActualyWagonLocation> getWagonLocation(String IdWagon) {

        String wagonsUnConnect = "SELECT"
                + " ZEM_DLZKA,ZEM_SIRKA"
                + " from Snimanie"
                + " join Snimac using(id_snimacu)"
                + " where cas_do is null"
                + " and  id_vozna like " + addApostrofs(IdWagon);
        String wagonsInTrainWhitchStay = "SELECT"
                + " ZEM_DLZKA,ZEM_SIRKA"
                + " from Sprava_voznov  sv"
                + " join Vlak v on(sv.id_vlaku = v.id_vlaku ) "
                + " join Snimanie sn on(sn.id_vlaku = v.id_vlaku )"
                + " join Snimac s using(id_snimacu)"
                + " where cas_do is null"
                + " and  sv.id_vozna like " + addApostrofs(IdWagon);
        String wagonsInTrainWhitchTransport = "SELECT "
                + " ZEM_DLZKA,ZEM_SIRKA"
                + " from Snimac join Snimanie sn1 using(id_snimacu)"
                + " where cas_do = (select max(cas_do) from Snimanie sn"
                + " join Vlak using(id_vlaku)"
                + " join Sprava_Voznov sv using(id_vlaku)"
                + " where sv.id_vozna like " + addApostrofs(IdWagon) + ")";
        List<ActualyWagonLocation> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL(wagonsUnConnect + " UNION " + wagonsInTrainWhitchStay + " UNION " + wagonsInTrainWhitchTransport);
        try {
            if (rs != null) {
                while (rs.next()) {
                    double longitud = rs.getDouble("ZEM_DLZKA");
                    double latitud = rs.getDouble("ZEM_SIRKA");
                    ActualyWagonLocation actualWagon = new ActualyWagonLocation(longitud, latitud);
                    result.add(actualWagon);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<ActualyWagonLocation> getCurrentWagonsLocation(String idStation, String wagonType, String inService, Date date, String company) {
        String dateString = addApostrofs(Formater.format(date));

        if (!isNullOrEmpty(wagonType)) {
            wagonType = " AND Typ_vozna.nazov like " + addApostrofs(wagonType);
        }
        if (!isNullOrEmpty(inService)) {
            inService = " AND v_prevadzke like " + addApostrofs(inService);
        }
        if (!isNullOrEmpty(company)) {
            company = " AND Spolocnost.nazov like " + addApostrofs(company);
        }
        if (!isNullOrEmpty(idStation)) {
            idStation = " AND id_stanice like " + Integer.parseInt(idStation);
        }

        String wagonsOutService = "select distinct "
                + "id_vozna as vozenVlaku,"
                + " ZEM_DLZKA,"
                + " ZEM_SIRKA,"
                + " cas_od,"
                + " cas_do,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " Spolocnost.nazov as spolocnos_nazov,"
                + " Stanica.nazov as stanica_nazov,"
                + " v_prevadzke"
                + " from Typ_vozna join Vozen  using(id_typu)"
                + " join Vozen_spolocnost using(id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Snimanie using(id_vozna)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " join Stanica using(id_stanice)"
                + " where cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)"
                + " and TO_DATE (TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') > to_date('05.01.1990 16:29:39','DD.MM.YYYY HH24:MI:SS')"
                + wagonType + inService + company + idStation;

        String wagonsInService = "select distinct"
                + " Vozen.id_vozna as vozenVlaku,"
                + " ZEM_DLZKA,"
                + " ZEM_SIRKA,"
                + " cas_od,"
                + " cas_do,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " Spolocnost.nazov as spolocnos_nazov,"
                + " Stanica.nazov as stanica_nazov,"
                + " v_prevadzke"
                + " from Spolocnost"
                + " join Vozen_spolocnost using(id_spolocnosti)"
                + " join Vozen on(Vozen_spolocnost.id_vozna = Vozen.id_vozna )"
                + " join TYP_VOZNA using(id_typu)"
                + " join Sprava_voznov on(Sprava_voznov.id_vozna = Vozen.id_vozna )"
                + " join Vlak using(id_vlaku)"
                + " join Snimanie using(id_vlaku)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " join Stanica using(id_stanice)"
                + " where cas_do is null"
                + " and (Vozen_spolocnost.DATUM_DO is null or Vozen_spolocnost.DATUM_DO >= sysdate)"
                + " and TO_DATE (TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') > to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS')"
                + " and cas_do is not null"
                + wagonType + inService + company + idStation;

        String wagonsLastScaned = "select distinct"
                + " Vozen.id_vozna as vozenVlaku,"
                + "                ZEM_DLZKA,"
                + "                ZEM_SIRKA,"
                + "                sn.cas_od,"
                + "                sn.cas_do,"
                + "                Typ_vozna.nazov as typ_vozna_nazov,"
                + "                Spolocnost.nazov as spolocnos_nazov,"
                + "                Stanica.nazov as stanica_nazov,"
                + "                v_prevadzke"
                + " from Stanica "
                + " join Kolajovy_Usek using(id_stanice)"
                + " join Snimac on( Kolajovy_usek.id_snimacu = Snimac.id_snimacu) "
                + " join Snimanie sn using(id_snimacu)"
                + " join Vlak v on(sn.id_vlaku = v.id_vlaku)"
                + " join Sprava_voznov sv on(sv.id_vlaku = v.id_vlaku )"
                + " join Vozen on(sv.id_vozna = Vozen.id_vozna )"
                + " join Typ_vozna using(id_typu)"
                + " join Vozen_spolocnost vsp on(vsp.id_vozna= Vozen.id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " where TO_DATE (TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') > to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS')"
                + " and sn.cas_do = (select max(snim.cas_do) from Snimanie snim"
                + "                 join Vlak using(id_vlaku)"
                + "                 join Sprava_Voznov using(id_vlaku)"
                + "                 where TO_DATE (TO_CHAR (snim.cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') > to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS'))";
        List<ActualyWagonLocation> result = new ArrayList<>();
        DBManager db = new DBManager();
        ResultSet rs = db.querySQL(wagonsOutService + " UNION " + wagonsInService + " UNION " + wagonsLastScaned);

        try {
            if (rs != null) {
                while (rs.next()) {
                    String idWagon = rs.getString("vozenVlaku");
                    String stationName = rs.getString("stanica_nazov");
                    Double latitude = rs.getDouble("ZEM_SIRKA");
                    Double longitude = rs.getDouble("ZEM_DLZKA");
                    Date dateFrom = rs.getDate("cas_od");
                    Date dateTo = rs.getDate("cas_do");
                    String companyName = rs.getString("spolocnos_nazov");
                    String inServiceDb = rs.getString("v_prevadzke");
                    String wagonTypeDb = rs.getString("typ_vozna_nazov");

                    ActualyWagonLocation actualyWagonLocation = new ActualyWagonLocation(idWagon, latitude, longitude, dateFrom, dateTo, wagonTypeDb, stationName, inServiceDb, companyName);
                    result.add(actualyWagonLocation);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<WagonInTrain> getWagonsInTrain(int idTrain, String trainType, String wagonType, Date date, String company) {
        String dateString = addApostrofs(Formater.format(date));
        if (!isNullOrEmpty(wagonType)) {
            wagonType = " AND Typ_vozna.nazov like " + addApostrofs(wagonType);
        }
        if (!isNullOrEmpty(trainType)) {
            trainType = " AND Typ_vlaku.nazov like " + addApostrofs(trainType);
        }
        if (!isNullOrEmpty(company)) {
            company = " AND Spolocnost.nazov like " + addApostrofs(company);
        }
        List<WagonInTrain> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("SELECT"
                + " id_vlaku,"
                + " Vlak.nazov as vlak_nazov,"
                + " id_vozna,"
                + " Spolocnost.nazov as spolocnostNazov,"
                + " Typ_vlaku.nazov as typ_vlaku_nazov,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " Sprava_voznov.datum_od as datumOd,"
                + " Sprava_voznov.datum_do as datumDo"
                + " FROM typ_vozna"
                + " join Vozen using(id_typu)"
                + " join Vozen_spolocnost using(id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Sprava_voznov using(id_vozna)"
                + " join Vlak using(id_vlaku)"
                + " join Typ_vlaku using(id_typ)"
                + " where id_vlaku like " + idTrain
                + " and Sprava_voznov.datum_od <= to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS')"
                + " and (Sprava_voznov.datum_do >= to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS') or Sprava_voznov.datum_do is null)"
                + wagonType + trainType + company
        );

        try {
            if (rs != null) {
                while (rs.next()) {
                    String idWagon = rs.getString("id_vozna");
                    String idTrainDb = rs.getString("id_vlaku");
                    String trainName = rs.getString("vlak_nazov");
                    String spolocnostNazov = rs.getString("spolocnostNazov");
                    String wagonTypeDb = rs.getString("typ_vozna_nazov");
                    String trainTypeDb = rs.getString("typ_vlaku_nazov");
                    Date dateFrom = rs.getDate("datumOd");
                    Date dateTo = rs.getDate("datumDo");
                    WagonInTrain wagonInTrain = new WagonInTrain(idWagon, idTrainDb, trainName, trainTypeDb, dateFrom, dateTo, spolocnostNazov, wagonTypeDb);
                    result.add(wagonInTrain);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public List<GroupOfWagon> getGroupOfWagon(String wagonType, Date date, String company){
      String dateString = addApostrofs(Formater.format(date));
        if (!isNullOrEmpty(wagonType)) {
            wagonType = " AND Typ_vozna.nazov like " + addApostrofs(wagonType);
        }
        if (!isNullOrEmpty(company)) {
            company = " AND Spolocnost.nazov like " + addApostrofs(company);
        }
        
        String wagonsOutService = "SELECT"
                + " Vozen.id_vozna as id_vozna,"
                + " Vozen.hmotnost as hmotnost,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " Spolocnost.nazov as spolocnostNazov"
                + " from Typ_vozna"
                + " join Vozen  using(id_typu)"
                + " join Sprava_voznov on(Sprava_voznov.id_vozna = Vozen.id_vozna) "
                + " join Vozen_spolocnost on(Vozen_spolocnost.id_vozna = Vozen.id_vozna)"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Snimanie on(Vozen.id_vozna = Snimanie.id_vozna) "
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " join Stanica using (id_stanice)"
                + " where Sprava_voznov.datum_od <= to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS')"
                + " and (Sprava_voznov.datum_do >= to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS') or Sprava_voznov.datum_do is null)"
                + wagonType + company;
       
        String wagonsInService = "SELECT"
                + " Vozen.id_vozna as id_vozna,"
                + " Vozen.HMOTNOST as hmotnost,"
                + " Typ_vozna.nazov as typ_vozna_nazov,"
                + " Spolocnost.nazov as spolocnostNazov"
                + " from Typ_vozna"
                + " join Vozen  using(id_typu)"
                + " join Vozen_spolocnost on(Vozen_spolocnost.id_vozna = Vozen.id_vozna )"
                + " join Spolocnost using(id_spolocnosti)"
                + " join Sprava_voznov on(Sprava_voznov.id_vozna = Vozen.id_vozna )"
                + " join Vlak using(id_vlaku)"
                + " join Snimanie using(id_vlaku)"
                + " join Snimac using(id_snimacu)"
                + " join Kolajovy_usek using(id_snimacu)"
                + " join Stanica using (id_stanice)"
                + " where Sprava_voznov.datum_od <= to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS')"
                + " and (Sprava_voznov.datum_do >= to_date(" + dateString + ",'DD.MM.YYYY HH24:MI:SS') or Sprava_voznov.datum_do is null)"
                + wagonType + company;
        
        List<GroupOfWagon> result = new ArrayList<>();
        DBManager db = new DBManager();
        ResultSet rs = db.querySQL(wagonsOutService + " UNION " + wagonsInService);

        try {
            if (rs != null) {
                while (rs.next()) {
                    String idWagon = rs.getString("id_vozna");
                    int WeightOfWag = rs.getInt("hmotnost");
                    String wagonTypeDb = rs.getString("typ_vozna_nazov");
                    String spolocnostNazov = rs.getString("spolocnostNazov");
                    GroupOfWagon groupWagon = new GroupOfWagon(idWagon,WeightOfWag , wagonTypeDb, spolocnostNazov);
                    result.add(groupWagon);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;  
    
    
    }
    
    public List<HistoricalWagonLocation> getHistoricalLocationData(String idWagon,Date dateFrom, Date datesTo) {
        String datFrom = Formater.format(dateFrom);
        String datTO = Formater.format(datesTo);
        String wagonsOutService = "SELECT"
                + " nazov,ZEM_DLZKA,ZEM_SIRKA,cas_od,cas_do"
                + " from Vozen "
                + " join Snimanie on(Vozen.id_vozna = Snimanie.id_vozna)"
                + " join Snimac using(id_snimacu)"
                + " left join Stanica using (id_snimacu)"
                + " where Vozen.id_vozna like " +addApostrofs(idWagon)
                + " AND TO_DATE (TO_CHAR (cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') >= to_date('" + datFrom + "','DD.MM.YYYY HH24:MI:SS') "
                + " AND (TO_DATE (TO_CHAR (cas_do, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') <= to_date('" + datTO + "','DD.MM.YYYY HH24:MI:SS') or cas_do is null)";
               

        String wagonsInService = "SELECT"
                + " st.nazov as nazov,"
                + " s.ZEM_DLZKA as ZEM_DLZKA,"
                + " s.ZEM_SIRKA as ZEM_SIRKA,"
                + " sn.cas_od as cas_od,"
                + " sn.cas_do as cas_do"
                + " from Vozen vz"
                + " join Sprava_voznov sv on(sv.id_vozna = vz.id_vozna ) "
                + " join Vlak v on(sv.id_vlaku = v.id_vlaku )"
                + " join Snimanie sn on(sn.id_vlaku = v.id_vlaku) "
                + " join Snimac s using(id_snimacu)"
                + " left join Stanica st using (id_snimacu)"
                + " where vz.id_vozna like " +addApostrofs(idWagon)
                + " AND TO_DATE (TO_CHAR (sn.cas_od, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') >= to_date('" + datFrom + "','DD.MM.YYYY HH24:MI:SS') "
                + " AND (TO_DATE (TO_CHAR (sn.cas_do, 'DD.MM.YYYY HH24:MI:SS'), 'DD.MM.YYYY HH24:MI:SS') <= to_date('" + datTO + "','DD.MM.YYYY HH24:MI:SS') or sn.cas_do is null)";
        List<HistoricalWagonLocation> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL(wagonsOutService + " UNION " + wagonsInService);

        try {
            if (rs != null) {
                while (rs.next()) {
                    String nazov = rs.getString("nazov");
                    double longitud = rs.getDouble("ZEM_DLZKA");
                    double latitud = rs.getDouble("ZEM_SIRKA");
                    Date timeFrom = rs.getDate("cas_od");
                    Date timeTo = rs.getDate("cas_do");
                    HistoricalWagonLocation historyLocation = new HistoricalWagonLocation(nazov, longitud, latitud, timeFrom,timeTo);
                    result.add(historyLocation);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
    return result;
    }

    public boolean isNullOrEmpty(String term) {
        return term == null || term.equals("") || term.equals("null");
    }

    private String addApostrofs(String name) {
        return "'" + name + "'";
    }
}
