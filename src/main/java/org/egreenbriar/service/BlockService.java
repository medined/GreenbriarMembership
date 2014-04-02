package org.egreenbriar.service;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    @Autowired
    private HouseService houseService = null;

    private final Set<Block> blocks = new TreeSet<>();

    @PostConstruct
    public void initialize() {
        for (Map.Entry<String, House> entry : houseService.getHouses().entrySet()) {
            House house = entry.getValue();
            addBlock(house.getBlockName());
        }
    }

    /* Find the district name for a given block by looking through the house list.
    */
    public String getDistrictName(final String blockName) {
        for (Map.Entry<String, House> entry : houseService.getHouses().entrySet()) {
            House house = entry.getValue();
            if (house.getBlockName().equals(blockName)) {
                return house.getDistrictName();
            }
        }
        return "";
    }
    
    /*
    public int getPercentMembership(String year) {
        int numHouses = 0;
        int numMembers = 0;
        for (Block block : blocks) {
            numHouses += block.getHouses().size();
            for (House house : block.getHouses()) {
                if (house.memberInYear(year)) {
                    numMembers++;
                }
            }
        }
        return (int) (((float) numMembers / (float) numHouses) * 100);
    }
    */

    public Block getBlock(final String blockName) {
        Block rv = null;
        for (Block block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                rv = block;
            }
        }
        return rv;
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

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }
}
