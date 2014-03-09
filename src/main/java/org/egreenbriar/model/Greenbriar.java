package org.egreenbriar.model;

import java.util.Set;
import java.util.TreeSet;

public class Greenbriar {
    
    private final Set<District> districts = new TreeSet<>();

    public District addDistrict(final String districtName) {
        for (District district : getDistricts()) {
            if (district.is(districtName)) {
                return district;
            }
        }
        District district = new District(districtName);
        getDistricts().add(district);
        return district;
    }

    @Override
    public String toString() {
        return "Greenbriar{" + "districts=" + getDistricts() + '}';
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public District getDistrict(String name) {
        District rv = null;
        for (District district : getDistricts()) {
            if (district.getName().equals(name)) {
                rv = district;
            }
        }
        return rv;
    }

    public Block getBlock(String name) {
        Block rv = null;
        for (District district : getDistricts()) {
            for (Block block : district.getBlocks()) {
                if (block.getBlockName().equals(name)) {
                    rv = block;
                }
            }
        }
        return rv;
    }
}
