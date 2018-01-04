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
public class Record {
    private String IdRecord;
    private Date Date;
    private String  Description;
    private String LogIn;

    public Record(String IdRecord, Date Date, String Description, String LogIn) {
        this.IdRecord = IdRecord;
        this.Date = Date;
        this.Description = Description;
        this.LogIn = LogIn;
    }

    public String getIdRecord() {
        return IdRecord;
    }

    public Date getDate() {
        return Date;
    }

    public String getDescription() {
        return Description;
    }

    public String getLogIn() {
        return LogIn;
    }

    public void setIdRecord(String IdRecord) {
        this.IdRecord = IdRecord;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setLogIn(String LogIn) {
        this.LogIn = LogIn;
    }
    
}
