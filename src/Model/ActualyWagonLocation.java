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
public class ActualyWagonLocation {
    private String IDWagon;
    private double Latitude;
    private double Longitude;
    private Date DateFrom;
    private Date DateTo;
    private String WagonType;
    private String InService;
    private String Company;
    private String StationName;
    
     
    public ActualyWagonLocation(String IDWagon, double longitude, double latitude, Date dateFrom, Date dateTo, String wagonType, String stationName, String inService, String company) {
        this.IDWagon = IDWagon;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.DateFrom = dateFrom;
        this.DateTo = dateTo;
        this.WagonType = wagonType;
        this.InService = inService;
        this.StationName = stationName;
        this.Company = company;
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

    public void setIDWagon(String IDWagon) {
        this.IDWagon = IDWagon;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

    public String getWagonType() {
        return WagonType;
    }

    public String getInService() {
        return InService;
    }

    public String getCompany() {
        return Company;
    }

    public String getStationName() {
        return StationName;
    }


    public void setDateFrom(Date DateFrom) {
        this.DateFrom = DateFrom;
    }

    public void setDateTo(Date DateTo) {
        this.DateTo = DateTo;
    }

    public void setWagonType(String WagonType) {
        this.WagonType = WagonType;
    }

    public void setInService(String InService) {
        this.InService = InService;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }
 
}
