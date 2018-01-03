/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Blob;

/**
 *
 * @author Bugy
 */
public class User extends Person{

    private String Login;
    private String Hash;
    private int IdFunction;
    private Blob Foto;

    public User(String Rc, String Name, String LastName, String login, String hash, int idFunction, Blob foto) {
        super(Rc, Name, LastName);
        Login = login;
        Hash = hash;
        IdFunction = idFunction;
        Foto = foto;
    }

    public String getLogin() {
        return Login;
    }

    public String getHash() {
        return Hash;
    }

    public int getIdFunction() {
        return IdFunction;
    }

    public Blob getFoto() {
        return Foto;
    }

    public String getRc() {
        return super.getRc();
    }

    public String getName() {
        return super.getName();
    }

    public String getLastName() {
        return super.getLastName();
    }
}
