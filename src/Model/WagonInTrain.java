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
public class WagonInTrain {
    private String IdWagon;
    private String WagonType;
    private String IdTrain;
    private String TrainName;
    private String TrainType;
    private Date DateFrom;
    private Date DateTo;
    private String Company;

    public WagonInTrain(String IdWagon, String IdTrain, String TrainName, String TrainType, Date DateFrom, Date DateTo, String Company, String WagonType) {
        this.IdWagon = IdWagon;
        this.IdTrain = IdTrain;
        this.TrainName = TrainName;
        this.TrainType = TrainType;
        this.DateFrom = DateFrom;
        this.DateTo = DateTo;
        this.Company = Company;
        this.WagonType = WagonType;
    }

    public String getIdWagon() {
        return IdWagon;
    }

    public String getIdTrain() {
        return IdTrain;
    }

    public String getTrainName() {
        return TrainName;
    }

    public String getTrainType() {
        return TrainType;
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

    public String getCompany() {
        return Company;
    }

    public void setIdWagon(String IdWagon) {
        this.IdWagon = IdWagon;
    }

    public void setIdTrain(String IdTrain) {
        this.IdTrain = IdTrain;
    }

    public void setTrainName(String TrainName) {
        this.TrainName = TrainName;
    }

    public void setTrainType(String TrainType) {
        this.TrainType = TrainType;
    }

    public void setDateFrom(Date DateFrom) {
        this.DateFrom = DateFrom;
    }

    public void setDateTo(Date DateTo) {
        this.DateTo = DateTo;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public void setWagonType(String WagonType) {
        this.WagonType = WagonType;
    }
    
    
}
