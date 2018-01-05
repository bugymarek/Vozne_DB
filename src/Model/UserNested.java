/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Blob;

/**
 *
 * @author Bugy
 */
public class UserNested extends Person{

    private String Streat;
    private String City;
    private String Country;
    private String CountryShortCut;

    public UserNested(String rc, String name, String lastNamem, String streat, String city, String country, String countryShortCut) {
        super(rc, name, lastNamem);
        Streat = streat;
        City = city;
        Country = country;
        CountryShortCut = countryShortCut;  
    }


    public String getStreat() {
        return Streat;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getCountryShortCut() {
        return CountryShortCut;
    }

    public void setStreat(String Streat) {
        this.Streat = Streat;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public void setCountryShortCut(String CountryShortCut) {
        this.CountryShortCut = CountryShortCut;
    }

    @Override
    public String getRc() {
        return super.getRc();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }
}
