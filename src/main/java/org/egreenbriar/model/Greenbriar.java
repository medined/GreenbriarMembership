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

    /**
     * @return the districts
     */
    public Set<District> getDistricts() {
        return districts;
    }

}
