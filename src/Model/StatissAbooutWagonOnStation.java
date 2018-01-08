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
public class StatissAbooutWagonOnStation {
   private String nazov;
   private int pocet;

    public StatissAbooutWagonOnStation(String nazov, int pocet) {
        this.nazov = nazov;
        this.pocet = pocet;
    }

    public String getNazov() {
        return nazov;
    }

    public int getPocet() {
        return pocet;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

   
}
