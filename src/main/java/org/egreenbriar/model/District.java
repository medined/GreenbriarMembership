package org.egreenbriar.model;

import java.util.Set;
import java.util.TreeSet;

public class District implements Comparable {

    private String name = null;
    private String representative = null;
    private final Set<Block> blocks = new TreeSet<>();

    @Override
    public String toString() {
        return "District{" + "name=" + getName() + ", blocks=" + getBlocks() + '}';
    }

    public District(final String name) {
        this.name = name;
    }

    public boolean is(final String name) {
        return this.getName().equals(name);
    }
    
    public Block addBlock(final String districtName, final String blockName) {
        for (Block block : getBlocks()) {
            if (block.is(blockName)) {
                return block;
            }
        }
        Block block = new Block(blockName);
        block.setDistrictName(districtName);
        getBlocks().add(block);
        return block;
    }

    @Override
    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            District that = (District)o;
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final District other = (District) obj;
        return !((this.getName() == null) ? (other.getName() != null) : !this.name.equals(other.name));
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
     * @return the blocks
     */
    public Set<Block> getBlocks() {
        return blocks;
    }

    /**
     * @return the representative
     */
    public String getRepresentative() {
        return representative;
    }

    /**
     * @param representative the representative to set
     */
    public void setRepresentative(String representative) {
        this.representative = representative;
    }
    
}
