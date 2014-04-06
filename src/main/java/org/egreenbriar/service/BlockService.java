package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    @Value("${houses.csv.file}")
    String housesFile = null;

    private final Set<Block> blocks = new TreeSet<>();

    @PostConstruct
    public void read() throws FileNotFoundException, IOException {
        String[] components = null;
        int lineCount = 0;

        CSVReader reader = new CSVReader(new FileReader(housesFile));
        while ((components = reader.readNext()) != null) {
            if (lineCount != 0) {
                String districtName = components[1];
                String blockName = components[2];
                Block block = new Block();
                block.setDistrictName(districtName);
                block.setBlockName(blockName);
                blocks.add(block);
            }
            lineCount++;
        }
        
    }

    public String getDistrictName(final String blockName) {
        for (Block block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                return block.getDistrictName();
            }
        }
        return "";
    }
    
    public Block getBlock(final String blockName) {
        Block rv = null;
        for (Block block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                rv = block;
            }
        }
        return rv;
    }
    
    public Set<Block> getBlocks() {
        return blocks;
    }

    public Set<Block> getBlocks(final String districtName) {
        Set<Block> rv = new TreeSet<>();
        
        for (Block block : blocks) {
            if (block.getDistrictName().equals(districtName)) {
                rv.add(block);
            }
        }
        
        return rv;
    }
}
