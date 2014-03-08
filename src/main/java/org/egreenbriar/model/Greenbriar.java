package org.egreenbriar.model;

import org.egreenbriar.model.District;
import java.util.Set;
import java.util.TreeSet;

public class Greenbriar {
    
    Set<District> districts = new TreeSet<District>();

    public District addDistrict(final String districtName) {
        for (District district : districts) {
            if (district.is(districtName)) {
                return district;
            }
        }
        District district = new District(districtName);
        districts.add(district);
        return district;
    }

    @Override
    public String toString() {
        return "Greenbriar{" + "districts=" + districts + '}';
    }

}
