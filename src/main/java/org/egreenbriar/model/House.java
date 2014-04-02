package org.egreenbriar.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class House implements Comparable {

    private String uuid = UUID.randomUUID().toString();
    private String districtName = null;
    private String blockName = null;
    private String houseNumber = null;
    private String streetName = null;
    private final Set<Person> people = new TreeSet<>();
    private boolean member2012 = false;
    private boolean member2013 = false;
    private boolean member2014 = false;
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.getHouseNumber() != null ? this.getHouseNumber().hashCode() : 0);
        hash = 67 * hash + (this.getStreetName() != null ? this.getStreetName().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "House{" + "houseNumber=" + getHouseNumber() + ", streetName=" + getStreetName() + ", people=" + getPeople() + "}";
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
        return !((this.getStreetName() == null) ? (other.getStreetName() != null) : !this.streetName.equals(other.streetName));
    }

    @Override
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

    public String memberInYear2012Style() {
        return memberInYear("2012") ? "year_button" : "year_button negate";
    }
    
    public String memberInYear2013Style() {
        return memberInYear("2013") ? "year_button" : "year_button negate";
    }
    
    public String memberInYear2014Style() {
        return memberInYear("2014") ? "button" : "button negate";
    }
    
    public boolean memberInYear(String year) {
        boolean rv = false;
        if (year != null) {
            switch (year) {
                case "2012":
                    rv = isMember2012();
                    break;
                case "2013":
                    rv = isMember2013();
                    break;
                case "2014":
                    rv = isMember2014();
                    break;
            }
        }
        return rv;
    }

    public boolean notMemberInYear(String year) {
        return !memberInYear(year);
    }

    public String getUuid() {
        return uuid;
    }

    public void toggle2014Membership() {
        setMember2014(!isMember2014());
    }

    /**
     * @return the member2012
     */
    public boolean isMember2012() {
        return member2012;
    }

    /**
     * @param member2012 the member2012 to set
     */
    public void setMember2012(boolean member2012) {
        this.member2012 = member2012;
    }

    /**
     * @return the member2013
     */
    public boolean isMember2013() {
        return member2013;
    }

    /**
     * @param member2013 the member2013 to set
     */
    public void setMember2013(boolean member2013) {
        this.member2013 = member2013;
    }

    /**
     * @return the member2014
     */
    public boolean isMember2014() {
        return member2014;
    }

    /**
     * @param member2014 the member2014 to set
     */
    public void setMember2014(boolean member2014) {
        this.member2014 = member2014;
    }

    /**
     * @return the districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * @return the blockName
     */
    public String getBlockName() {
        return blockName;
    }

    /**
     * @param blockName the blockName to set
     */
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
