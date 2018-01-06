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
public class GroupOfWagon {
   private String idWagon;
   private int weightOfWagon;
   private String idTypeOfWagon;
   private String companyOfWagon;

    public String getIdWagon() {
        return idWagon;
    }

    public GroupOfWagon(String idWagon, int weightOfWagon, String idTypeOfWagon, String companyOfWagon) {
        this.idWagon = idWagon;
        this.weightOfWagon = weightOfWagon;
        this.idTypeOfWagon = idTypeOfWagon;
        this.companyOfWagon = companyOfWagon;
    }

    public int getWeightOfWagon() {
        return weightOfWagon;
    }

    public String getIdTypeOfWagon() {
        return idTypeOfWagon;
    }

    public String getCompanyOfWagon() {
        return companyOfWagon;
    }

    public void setIdWagon(String idWagon) {
        this.idWagon = idWagon;
    }

    public void setWeightOfWagon(int weightOfWagon) {
        this.weightOfWagon = weightOfWagon;
    }

    public void setIdTypeOfWagon(String idTypeOfWagon) {
        this.idTypeOfWagon = idTypeOfWagon;
    }

    public void setCompanyOfWagon(String companyOfWagon) {
        this.companyOfWagon = companyOfWagon;
    }
   
   
}


