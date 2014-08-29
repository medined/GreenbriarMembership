package org.egreenbriar.service;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.egreenbriar.model.Block;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    @Value("${houses.csv.file}")
    private String housesFile = null;

    private final Set<Block> blocks = new TreeSet<>();

    @PostConstruct
    public void initialize() {
        String[] components;
        int lineCount = 0;

        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(getHousesFile()));
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to open " + getHousesFile(), e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read " + getHousesFile(), e);
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

    public int getNumberOfBlocks() {
        return blocks.size();
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

    public String getHousesFile() {
        return housesFile;
    }

    public void setHousesFile(String housesFile) {
        this.housesFile = housesFile;
    }
}
