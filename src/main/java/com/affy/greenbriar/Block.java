package com.affy.greenbriar;

import java.util.Set;
import java.util.TreeSet;

public class Block implements Comparable {

    private String name = null;
    private Person captain = null;
    private Set<House> houses = new TreeSet<House>();
    
    public Block(final String name) {
        this.name = name;
    }

    public boolean is(final String name) {
        return this.getName().equals(name);
    }
    
    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            Block that = (Block)o;
            if (this.equals(that)) {
                rv = 0;
            } else {
                rv = this.getName().compareTo(that.getName());
            }
        }
        return rv;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Block{" + "name=" + getName() + ", captain=" + getCaptain() + ", houses=" + getHouses() + '}';
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
        if ((this.getName() == null) ? (other.getName() != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    void addHouse(House _house) {
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

    void addCaptain(Person person) {
        setCaptain(person);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @param houses the houses to set
     */
    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }
    
}
