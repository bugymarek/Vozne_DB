/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Michal
 */
public class HistoricalWagonLocation {
     private String IDWagon;
     private String stanica;
     private double Latitude;
     private double Longitude;
     private Date DateFrom;
     private Date DateTo;
     
    public HistoricalWagonLocation(String stanica, double Latitude, double Longitude, Date DateFrom, Date DateTo) {
        this.stanica = stanica;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.DateFrom = DateFrom;
        this.DateTo = DateTo;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

    public void setDateFrom(Date DateFrom) {
        this.DateFrom = DateFrom;
    }

    public void setDateTo(Date DateTo) {
        this.DateTo = DateTo;
    }
     

    

    public String getIDWagon() {
        return IDWagon;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public String getStanica() {
        return stanica;
    }

    public void setIDWagon(String IDWagon) {
        this.IDWagon = IDWagon;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public void setStanica(String stanica) {
        this.stanica = stanica;
    }
    
    
    
}
