package com.infinityraider.agricraft.tiles.irrigation;


import com.infinityraider.agricraft.reference.Constants;
import com.infinityraider.agricraft.reference.AgriCraftNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import com.infinityraider.agricraft.api.v1.misc.IAgriDebuggable;

public class TileEntityChannelValve extends TileEntityChannel implements IAgriDebuggable{
	
    private boolean powered = false;

    @Override
    protected final void writeChannelNBT(NBTTagCompound tag) {
        tag.setBoolean(AgriCraftNBT.POWER, powered);
    }

    //this loads the saved data for the tile entity
    @Override
    protected final void readChannelNBT(NBTTagCompound tag) {
        this.powered = tag.getBoolean(AgriCraftNBT.POWER);
    }

    @Override
    public void update() {
        if(!this.worldObj.isRemote) {
            if(!this.powered) {
                super.update();
            } else {
                updateNeighbours();
            }
        }
    }

    public void updatePowerStatus() {
        final boolean wasPowered = powered;
        powered = worldObj.isBlockIndirectlyGettingPowered(getPos()) > 0;
        if (powered != wasPowered) {
            markForUpdate();
        }
    }

    public boolean isPowered() {
    	return powered;
    }
    
	@Override
    public boolean canAcceptFluid(int y, int amount, boolean partial) {
    	return !powered && super.canAcceptFluid(y, amount, partial);
    }

    @Override
    public void addDebugInfo(List<String> list) {
        list.add("VALVE");
        list.add("  - State: "+(this.isPowered()?"closed":"open"));
        list.add("  - FluidLevel: " + this.getFluidAmount(0) + "/" + Constants.BUCKET_mB / 2);
        list.add("  - FluidHeight: " + this.getFluidHeight());
        list.add("  - Material: " + this.getMaterial().getRegistryName() + ":" + this.getMaterialMeta()); //Much Nicer.
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addDisplayInfo(List information) {
    	//Required super call
    	super.addDisplayInfo(information);
    	//show status
        String status = I18n.translateToLocal(powered?"agricraft_tooltip.closed":"agricraft_tooltip.open");
        information.add(I18n.translateToLocal("agricraft_tooltip.state")+": "+status);
    }
}
