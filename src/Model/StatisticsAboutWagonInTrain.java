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
public class StatisticsAboutWagonInTrain {
   private String nazov;
   private int pocet;
   private double percento;

    public StatisticsAboutWagonInTrain(String nazov, int pocet, double percento) {
        this.nazov = nazov;
        this.pocet = pocet;
        this.percento = percento;
    }

    public String getNazov() {
        return nazov;
    }

    public int getPocet() {
        return pocet;
    }

    public double getPercento() {
        return percento;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public void setPercento(double percento) {
        this.percento = percento;
    }
   
   
}
