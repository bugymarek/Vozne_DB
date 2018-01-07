/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Bugy
 */
public class Scanner {
    private int ScannerId;
    private double Latitude;
    private double Longitude;

    public Scanner(int ScannerId, double Latitude, double Longitude) {
        this.ScannerId = ScannerId;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public int getScannerId() {
        return ScannerId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }
    
}
