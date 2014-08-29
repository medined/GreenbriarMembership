package org.egreenbriar.service;

import org.egreenbriar.model.Block;
import org.egreenbriar.model.District;
import org.springframework.stereotype.Service;

@Service
public class ZeroBlocksService {

    private DistrictService districtService = null;

    private BlockService blockService = null;
    
    private BlockCaptainService blockCaptainService = null;
    
    private HouseService houseService = null;

    private OfficierService officierService = null;

    public void getZeroBlocks() {
        int blockCount = 0;
        String lastDistrictName = null;
        System.out.println("--------------------------------------------------");
        for (District district : districtService.getDistricts()) {
            String districtName = district.getName();
            for (Block block : blockService.getBlocks(districtName)) {
                final String blockName = block.getBlockName();
                int percent = getHouseService().getPercentMembership(blockName, "2014");
                if (percent == 0) {
                    String blockCaptainName = getBlockCaptainService().getCaptainName(blockName);
                    String representativeName = getOfficierService().getDistrictRepresentative(districtName);
                    System.out.println(String.format("%s | %s | %-14.14s | %s", district.getName(), blockName, representativeName, blockCaptainName));
                    blockCount++;
                }
            }
            if (!districtName.equals(lastDistrictName)) {
                System.out.println("--------------------------------------------------");
                lastDistrictName = districtName;
            }
        }
        System.out.println(String.format("%d Blocks With Zero Percent Returns", blockCount));
    }

    public DistrictService getDistrictService() {
        return districtService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    public BlockService getBlockService() {
        return blockService;
    }

    public void setBlockService(BlockService blockService) {
        this.blockService = blockService;
    }

    public HouseService getHouseService() {
        return houseService;
    }

    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    public BlockCaptainService getBlockCaptainService() {
        return blockCaptainService;
    }

    public void setBlockCaptainService(BlockCaptainService blockCaptainService) {
        this.blockCaptainService = blockCaptainService;
    }

    public OfficierService getOfficierService() {
        return officierService;
    }

    public void setOfficierService(OfficierService officierService) {
        this.officierService = officierService;
    }

}
