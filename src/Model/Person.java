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
public class Person {
    private String Rc;
        private String Name;
        private String LastName;

        public Person(String Rc, String Name, String LastName) {
            this.Rc = Rc;
            this.Name = Name;
            this.LastName = LastName;
        }

        public String getRc() {
            return Rc;
        }

        public String getName() {
            return Name;
        }

        public String getLastName() {
            return LastName;
        }
}
