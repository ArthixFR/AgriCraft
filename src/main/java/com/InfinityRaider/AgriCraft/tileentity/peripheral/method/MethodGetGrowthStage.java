package com.InfinityRaider.AgriCraft.tileentity.peripheral.method;

import com.InfinityRaider.AgriCraft.tileentity.TileEntityCrop;

public class MethodGetGrowthStage extends MethodCropBase {
    protected MethodGetGrowthStage() {
        super("getGrowthStage");
    }

    @Override
    protected Object[] onMethodCalled(TileEntityCrop crop) {
        double growthStage = (100.00*crop.getWorldObj().getBlockMetadata(crop.xCoord, crop.yCoord, crop.zCoord))/7;
        return new Object[] {growthStage};
    }

    @Override
    protected boolean requiresJournal() {
        return false;
    }
}
