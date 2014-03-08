package com.affy.greenbriar;

import java.util.Set;
import java.util.TreeSet;

public class District implements Comparable {

    private String name = null;

    @Override
    public String toString() {
        return "District{" + "name=" + getName() + ", blocks=" + blocks + '}';
    }
    Set<Block> blocks = new TreeSet<Block>();

    public District(final String name) {
        this.name = name;
    }

    public boolean is(final String name) {
        return this.getName().equals(name);
    }
    
    public Block addBlock(final String blockName) {
        for (Block block : blocks) {
            if (block.is(blockName)) {
                return block;
            }
        }
        Block block = new Block(blockName);
        blocks.add(block);
        return block;
    }

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
        if ((this.getName() == null) ? (other.getName() != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
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
    
}
