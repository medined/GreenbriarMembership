package com.affy.greenbriar;

import java.util.Set;
import java.util.TreeSet;

public class House implements Comparable {
    private String houseNumber = null;
    private String streetName = null;
    private Set<Person> people = new TreeSet<Person>();
    private Set<Membership> years = new TreeSet<Membership>();

    public House(final String houseNumber, final String streetName) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.getHouseNumber() != null ? this.getHouseNumber().hashCode() : 0);
        hash = 67 * hash + (this.getStreetName() != null ? this.getStreetName().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "House{" + "houseNumber=" + getHouseNumber() + ", streetName=" + getStreetName() + ", people=" + getPeople() + ", years=" + getYears() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final House other = (House) obj;
        if ((this.getHouseNumber() == null) ? (other.getHouseNumber() != null) : !this.houseNumber.equals(other.houseNumber)) {
            return false;
        }
        if ((this.getStreetName() == null) ? (other.getStreetName() != null) : !this.streetName.equals(other.streetName)) {
            return false;
        }
        return true;
    }

    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            House that = (House)o;
            if (this.equals(that)) {
                rv = 0;
            } else {
                rv = this.getHouseNumber().compareTo(that.getHouseNumber());
                if (rv == 0) {
                    rv = this.getStreetName().compareTo(that.getStreetName());
                }
            }
        }
        return rv;
    }

    public Person addPerson(final String last, final String first, final String phone, final String email, final String comment) {
        if (last.isEmpty() && first.isEmpty()) {
            return Person.EMPTY;
        }
        Person rv = null;
        for (Person person : getPeople()) {
            if (person.is(last, first)) {
                rv = person;
                break;
            }
        }
        if (rv == null) {
            rv = new Person(last, first, phone, email, comment);
            getPeople().add(rv);
        }
        return rv;
    }

    boolean isAt(String houseNumber, String streetName) {
        return this.getHouseNumber().equals(houseNumber) && this.getStreetName().equals(streetName);
    }

    void addYear(Membership year) {
        getYears().add(year);
    }

    /**
     * @return the houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber the houseNumber to set
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * @return the streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName the streetName to set
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * @return the people
     */
    public Set<Person> getPeople() {
        return people;
    }

    /**
     * @return the years
     */
    public Set<Membership> getYears() {
        return years;
    }

    public boolean wasMemberIn(Membership year) {
        return years.contains(year);
    }

}
