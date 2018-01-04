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
public class ActualyWagonLocation {
    private double IDWagon;
    private double zemDlzka;
     private double zemSirka;
     
    public ActualyWagonLocation(double IDWagon, double zemDlzka, double zemSirka) {
        this.IDWagon = IDWagon;
        this.zemDlzka = zemDlzka;
        this.zemSirka = zemSirka;
    }

    public double getIDWagon() {
        return IDWagon;
    }

    public void setIDWagon(double IDWagon) {
        this.IDWagon = IDWagon;
    }
     

    public double getZemDlzka() {
        return zemDlzka;
    }

    public double getZemSirka() {
        return zemSirka;
    }

    public void setZemDlzka(double zemDlzka) {
        this.zemDlzka = zemDlzka;
    }

    public void setZemSirka(double zemSirka) {
        this.zemSirka = zemSirka;
    }
     
     
}
