package org.egreenbriar.model;

import java.util.Set;
import java.util.TreeSet;

public class Block implements Comparable {

    private String districtName = null;
    private String blockName = null;
    private Person captain = null;
    private final Set<House> houses = new TreeSet<>();
    private final Set<Person> people = new TreeSet<>();
    
    public Block(final String name) {
        this.blockName = name;
    }

    public boolean is(final String name) {
        return this.getBlockName().equals(name);
    }
    
    @Override
    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            Block that = (Block)o;
            if (this.equals(that)) {
                rv = 0;
            } else {
                rv = this.getBlockName().compareTo(that.getBlockName());
            }
        }
        return rv;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.getBlockName() != null ? this.getBlockName().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Block{" + "name=" + getBlockName() + ", captain=" + getCaptain() + ", houses=" + getHouses() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        return !((this.getBlockName() == null) ? (other.getBlockName() != null) : !this.blockName.equals(other.blockName));
    }

    public void addHouse(House _house) {
        for (House house : getHouses()) {
            if (house.equals(_house)) {
                return;
            }
        }
        getHouses().add(_house);
    }

    public House addHouse(final String houseNumber, final String streetName) {
        for (House house : getHouses()) {
            if (house.isAt(houseNumber, streetName)) {
                return house;
            }
        }
        House house = new House(houseNumber, streetName);
        getHouses().add(house);
        return house;
    }

    public void addCaptain(Person person) {
        setCaptain(person);
    }

    /**
     * @return the captain
     */
    public Person getCaptain() {
        return captain;
    }

    /**
     * @param captain the captain to set
     */
    public void setCaptain(Person captain) {
        this.captain = captain;
    }

    /**
     * @return the houses
     */
    public Set<House> getHouses() {
        return houses;
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
    
    public Set<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }
}
