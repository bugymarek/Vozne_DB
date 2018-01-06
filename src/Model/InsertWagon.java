/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Bugy
 */
public class InsertWagon {
    private String IdWagon;
    private int Weight;
    private String InService;
    private int Type;
    private int CompanyId;
    private String StationName;
    private int ScannerId;
    private Date Date;

    public InsertWagon(String IdWagon, int Weight, String InService, int Type, int CompanyId, String StationName, int ScannerId, Date Date) {
        this.IdWagon = IdWagon;
        this.Weight = Weight;
        this.InService = InService;
        this.Type = Type;
        this.CompanyId = CompanyId;
        this.StationName = StationName;
        this.ScannerId = ScannerId;
        this.Date = Date;
    }

    public String getIdWagon() {
        return IdWagon;
    }

    public int getWeight() {
        return Weight;
    }

    public String getInService() {
        return InService;
    }

    public int getType() {
        return Type;
    }

    public int getCompany() {
        return CompanyId;
    }

    public String getStationName() {
        return StationName;
    }

    public int getScannerId() {
        return ScannerId;
    }

    public Date getDate() {
        return Date;
    }
    
    
}
