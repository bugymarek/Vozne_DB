/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PersonNested;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.DBManager;

/**
 *
 * @author Bugy
 */
public class App {

    private DBManager DbManager;

    public App() {
        this.DbManager = new DBManager();
    }

    public List<PersonNested> getPersons() {
        ArrayList<PersonNested> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("select"
                + " o.meno as meno,"
                + " o.priezvisko as priezvisko,"
                + " o.rod_cislo as rod_cislo,"
                + " u.ulica as ulica,"
                + " u.mesto as mesto,"
                + " u.stat as stat,"
                + " u.skratka as skratka"
                + " from osoba_nested o , table(o.adresa) u"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    String name = rs.getString("meno");
                    String lastName = rs.getString("priezvisko");
                    String rc = rs.getString("rod_cislo");
                    String streat = rs.getString("ulica");
                    String city = rs.getString("mesto");
                    String country = rs.getString("stat");
                    String countryShortCut = rs.getString("skratka");
                    PersonNested userNested = new PersonNested(rc, name, lastName, streat, city, country, countryShortCut);
                    result.add(userNested);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public List<PersonNested> getPersonsSortedByFullName() {
        ArrayList<PersonNested> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("select"
                + " o.meno as meno,"
                + " o.priezvisko as priezvisko,"
                + " o.rod_cislo as rod_cislo,"
                + " u.ulica as ulica,"
                + " u.mesto as mesto,"
                + " u.stat as stat,"
                + " u.skratka as skratka"
                + " from osoba_nested o , table(o.adresa) u"
                + " order by value(o)"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    String name = rs.getString("meno");
                    String lastName = rs.getString("priezvisko");
                    String rc = rs.getString("rod_cislo");
                    String streat = rs.getString("ulica");
                    String city = rs.getString("mesto");
                    String country = rs.getString("stat");
                    String countryShortCut = rs.getString("skratka");
                    PersonNested userNested = new PersonNested(rc, name, lastName, streat, city, country, countryShortCut);
                    result.add(userNested);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public List<PersonNested> getPersonsSortedByStreat() {
        ArrayList<PersonNested> result = new ArrayList<>();
        ResultSet rs = DbManager.querySQL("select"
                + " o.meno as meno,"
                + " o.priezvisko as priezvisko,"
                + " o.rod_cislo as rod_cislo,"
                + " u.ulica as ulica,"
                + " u.mesto as mesto,"
                + " u.stat as stat,"
                + " u.skratka as skratka"
                + " from osoba_nested o , table(o.adresa) u"
                + " order by value(u)"
        );
        try {
            if (rs != null) {
                while (rs.next()) {
                    String name = rs.getString("meno");
                    String lastName = rs.getString("priezvisko");
                    String rc = rs.getString("rod_cislo");
                    String streat = rs.getString("ulica");
                    String city = rs.getString("mesto");
                    String country = rs.getString("stat");
                    String countryShortCut = rs.getString("skratka");
                    PersonNested userNested = new PersonNested(rc, name, lastName, streat, city, country, countryShortCut);
                    result.add(userNested);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
