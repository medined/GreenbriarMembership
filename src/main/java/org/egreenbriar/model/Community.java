package org.egreenbriar.model;

import java.util.Set;
import java.util.TreeSet;

public class Community {

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

    public int getPercentMembership(String year) {
        int numHouses = 0;
        int numMembers = 0;
        for (District district : districts) {
            for (Block block : district.getBlocks()) {
                numHouses += block.getHouses().size();
                for (House house : block.getHouses()) {
                    if (house.memberInYear(year)) {
                        numMembers++;
                    }
                }
            }
        }
        return (int) (((float) numMembers / (float) numHouses) * 100);
    }

    @Override
    public String toString() {
        return "Community{" + "districts=" + getDistricts() + '}';
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
