/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Michal
 */
public class UserModication {
   private String meno;
   private String funkcia;
   private int pocet;
   private double percento;

    public UserModication(String meno, String funkcia, int pocet, double percento) {
        this.meno = meno;
        this.funkcia = funkcia;
        this.pocet = pocet;
        this.percento = percento;
    }

    public String getMeno() {
        return meno;
    }

    public String getFunkcia() {
        return funkcia;
    }

    public int getPocet() {
        return pocet;
    }

    public double getPercento() {
        return percento;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public void setFunkcia(String funkcia) {
        this.funkcia = funkcia;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public void setPercento(double percento) {
        this.percento = percento;
    }

   

}
