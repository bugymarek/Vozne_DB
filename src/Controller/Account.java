/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import service.DBManager;

/**
 *
 * @author Bugy
 */
public class Account {

    private DBManager DbManager;
    private User User;

    public Account(DBManager dbManager) {
        DbManager = dbManager;
    }

    public boolean logIn(String userName, String password) {
        boolean result = false;
        userName = addApostrofs(userName);
        String hashPassword = addApostrofs(hashPassword(password));
        ResultSet rs = DbManager.querySQL("SELECT"
                + "  meno,"
                + "  priezvisko,"
                + "  login,"
                + "  hash,"
                + "  rod_cislo,"
                + "  id_funkcie,"
                + "  foto"
                + " FROM"
                + " Uzivatel join Osoba using(rod_cislo) "
                + " WHERE login like " + userName + "AND hash like " + hashPassword
        );
        try {
            if (rs != null) {

                while (rs.next()) {
                    //Retrieve by column name
                    String name = rs.getString("meno");
                    String lastName = rs.getString("priezvisko");
                    String login = rs.getString("login");
                    String hash = rs.getString("hash");
                    String rod_cislo = rs.getString("rod_cislo");
                    int id_funkcie = rs.getInt("id_funkcie");
                    Blob foto = rs.getBlob("foto");
                    result = true;
                    this.User = new User(rod_cislo, name, lastName, login, hash, id_funkcie, foto);
                }
                rs.close();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String hashPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private String addApostrofs(String name) {
        return "'" + name + "'";
    }

    public User getUser() {
        return User;
    }
}
