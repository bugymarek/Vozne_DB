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
    private double Latitude;
     private double Longitude;
     
    public ActualyWagonLocation(double IDWagon, double longitude, double latitude) {
        this.IDWagon = IDWagon;
        this.Longitude = longitude;
        this.Latitude = latitude;
    }

    public double getIDWagon() {
        return IDWagon;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setIDWagon(double IDWagon) {
        this.IDWagon = IDWagon;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }
     
    
}
