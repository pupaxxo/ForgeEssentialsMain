package com.ForgeEssentials.commands.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ForgeEssentials.core.PlayerInfo;
import com.ForgeEssentials.permission.PermissionsAPI;
import com.ForgeEssentials.permission.query.PermQueryPlayer;
import com.ForgeEssentials.util.FEChatFormatCodes;
import com.ForgeEssentials.util.FunctionHelper;

import cpw.mods.fml.common.FMLCommonHandler;

public class EventHandler
{
	@ForgeSubscribe()
	public void playerInteractEvent(PlayerInteractEvent e)
	{
		/*
		 * Colorize!
		 */
		
		if(e.entityPlayer.getEntityData().getBoolean("colorize"))
		{
			e.setCanceled(true);
			TileEntity te = e.entityPlayer.worldObj.getBlockTileEntity(e.x, e.y, e.z);
			if(te != null)
			{
				if(te instanceof TileEntitySign)
				{
					String[] signText = ((TileEntitySign)te).signText;
						
					signText[0] = colorize(signText[0]);
					signText[1] = colorize(signText[1]);
					signText[2] = colorize(signText[2]);
					signText[3] = colorize(signText[3]);
						
					((TileEntitySign)te).signText = signText;
					e.entityPlayer.worldObj.setBlockTileEntity(e.x, e.y, e.z, te);
					e.entityPlayer.worldObj.markBlockForUpdate(e.x, e.y, e.z);
				}
				else
				{
					e.entityPlayer.sendChatToPlayer("That is no sign!");
				}
			}
			else
			{
				e.entityPlayer.sendChatToPlayer("That is no sign!");
			}
			
			e.entityPlayer.getEntityData().setBoolean("colorize", false);
		}
		
		/*
		 * Jump with compass
		 */
		
		if(e.action == e.action.RIGHT_CLICK_AIR || e.action == e.action.RIGHT_CLICK_BLOCK)
		{
			if(e.entityPlayer.getCurrentEquippedItem() != null && FMLCommonHandler.instance().getEffectiveSide().isServer())
			{
				if(e.entityPlayer.getCurrentEquippedItem().itemID == Item.compass.itemID)
				{
					if(PermissionsAPI.checkPermAllowed(new PermQueryPlayer(e.entityPlayer, "ForgeEssentials.BasicCommands.jump")))
					{
						MovingObjectPosition mo = FunctionHelper.getPlayerLookingSpot(e.entityPlayer, false);
						((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.setPlayerLocation(mo.blockX, mo.blockY, mo.blockZ, e.entityPlayer.rotationPitch, e.entityPlayer.rotationYaw);
					}
				}
			}
		}
	}
	
	private String colorize(String string) 
	{
		return string.replaceAll("&", FEChatFormatCodes.CODE.toString());
	}

	@ForgeSubscribe(priority = EventPriority.LOW)
	public void onPlayerDeath(LivingDeathEvent e)
	{
		if (e.entity instanceof EntityPlayer)
			PlayerInfo.getPlayerInfo((EntityPlayer) e.entity).back = FunctionHelper.getEntityPoint(e.entity);
	}
}
