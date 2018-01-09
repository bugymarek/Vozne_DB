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
public class AutomaticWagonEvidence {
  private String idWagon;
  private int hmotnost;
  private String nameOfTypeWagon;
  private String nameOfCompany;
  private String nameOfTrain;
  private String typeOfTrain;

    public AutomaticWagonEvidence(String idWagon, int hmotnost, String nameOfTypeWagon, String nameOfCompany, String nameOfTrain, String typeOfTrain) {
        this.idWagon = idWagon;
        this.hmotnost = hmotnost;
        this.nameOfTypeWagon = nameOfTypeWagon;
        this.nameOfCompany = nameOfCompany;
        this.nameOfTrain = nameOfTrain;
        this.typeOfTrain = typeOfTrain;
    }

    public void setIdWagon(String idWagon) {
        this.idWagon = idWagon;
    }

    public void setHmotnost(int hmotnost) {
        this.hmotnost = hmotnost;
    }

    public void setNameOfTypeWagon(String nameOfTypeWagon) {
        this.nameOfTypeWagon = nameOfTypeWagon;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public void setNameOfTrain(String nameOfTrain) {
        this.nameOfTrain = nameOfTrain;
    }

    public void setTypeOfTrain(String typeOfTrain) {
        this.typeOfTrain = typeOfTrain;
    }

    
    
    public String getIdWagon() {
        return idWagon;
    }

    public int getHmotnost() {
        return hmotnost;
    }

    public String getNameOfTypeWagon() {
        return nameOfTypeWagon;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public String getNameOfTrain() {
        return nameOfTrain;
    }

    public String getTypeOfTrain() {
        return typeOfTrain;
    }
  
  
}
