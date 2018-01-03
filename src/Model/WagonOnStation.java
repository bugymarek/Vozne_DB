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
public class WagonOnStation {
    private String IdWagon;
    private String InService;
    private String Type;
    private String Company;

    public WagonOnStation(String IdWagon, String InService, String Type, String Company) {
        this.IdWagon = IdWagon;
        this.InService = InService;
        this.Type = Type;
        this.Company = Company;
    }

    public String getIdWagon() {
        return IdWagon;
    }

    public String getInService() {
        return InService;
    }

    public String getType() {
        return Type;
    }

    public String getCompany() {
        return Company;
    }

    public void setIdWagon(String IdWagon) {
        this.IdWagon = IdWagon;
    }

    public void setInService(String InService) {
        this.InService = InService;
    }

    public void setType(String Typ) {
        this.Type = Type;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }
    
    
}


