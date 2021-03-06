package com.ForgeEssentials.commands.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import com.ForgeEssentials.commands.CommandMotd;
import com.ForgeEssentials.util.DataStorage;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTrackerCommands implements IPlayerTracker 
{
	@Override
	public void onPlayerLogin(EntityPlayer player) 
	{
		player.sendChatToPlayer(CommandMotd.motd);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) 
	{
		if (DataStorage.getData("spawn").hasKey("dim"))
		{
			ChunkCoordinates var4 = ((EntityPlayerMP) player).getBedLocation();
			if (var4 == null)
			{
				NBTTagCompound spawn = DataStorage.getData("spawn");
				Integer X = spawn.getInteger("x");
				Integer Y = spawn.getInteger("y");
				Integer Z = spawn.getInteger("z");
				Float yaw = spawn.getFloat("yaw");
				Float pitch = spawn.getFloat("pitch");
				Integer dim = spawn.getInteger("Dim");
				if (player.dimension!=dim) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player), dim);
				player.setPositionAndRotation(X, Y, Z, yaw, pitch);
			}
		}
	}
}
