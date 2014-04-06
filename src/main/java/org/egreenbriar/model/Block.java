package org.egreenbriar.model;

public class Block implements Comparable {

    private String districtName = null;
    private String blockName = null;
    
    public Block(final String name) {
        this.blockName = name;
    }

    public Block() {
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
        return "Block{" + "name=" + getBlockName() + ", district=" + getDistrictName() + "}";
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

    public String getDistrictName() {
        return districtName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
}
