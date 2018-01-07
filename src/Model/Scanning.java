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
public class Scanning {
    private Date dateFrom;
    private String LocomotiveId;
    private int ScannerId;
    private String WagonId;
    private Date dateTo;
    private String TrainId;

    public Scanning(Date dateFrom, String LocomotiveId, int ScannerId, String WagonId, Date dateTo, String TrainId) {
        this.dateFrom = dateFrom;
        this.LocomotiveId = LocomotiveId;
        this.ScannerId = ScannerId;
        this.WagonId = WagonId;
        this.dateTo = dateTo;
        this.TrainId = TrainId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public String getLocomotiveId() {
        return LocomotiveId;
    }

    public int getScannerId() {
        return ScannerId;
    }

    public String getWagonId() {
        return WagonId;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getTrainId() {
        return TrainId;
    }
    
    
}
