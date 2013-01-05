package com.ForgeEssentials.commands;

import java.util.HashMap;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;

import com.ForgeEssentials.core.PlayerInfo;
import com.ForgeEssentials.core.commands.ForgeEssentialsCommandBase;
import com.ForgeEssentials.util.FunctionHelper;
import com.ForgeEssentials.util.Localization;
import com.ForgeEssentials.util.OutputHandler;
import com.ForgeEssentials.util.AreaSelector.Point;
import com.ForgeEssentials.util.AreaSelector.WorldPoint;

import cpw.mods.fml.common.FMLCommonHandler;

public class CommandBed extends ForgeEssentialsCommandBase
{

	/** Spawn point for each dimension */
	public static HashMap<Integer, Point> spawnPoints = new HashMap<Integer, Point>();

	@Override
	public String getCommandName()
	{
		return "bed";
	}

	@Override
	public void processCommandPlayer(EntityPlayer sender, String[] args)
	{
		if (args.length >= 1)
		{
			EntityPlayer player = FunctionHelper.getPlayerFromUsername(args[0]);
			if (player != null)
			{
				ChunkCoordinates spawn = player.getBedLocation();
				PlayerInfo.getPlayerInfo((EntityPlayer) player).back = new WorldPoint(player);
				((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(spawn.posX, spawn.posY, spawn.posZ, player.rotationYaw, player.rotationPitch);
				player.sendChatToPlayer(Localization.get(Localization.SPAWNED));
			} else
				OutputHandler.chatError(sender, Localization.format(Localization.ERROR_NOPLAYER, args[0]));
		} else
		{
			ChunkCoordinates spawn = sender.getBedLocation();
			if (spawn != null)
			{
				PlayerInfo.getPlayerInfo((EntityPlayer) sender).back = new WorldPoint(sender);
				((EntityPlayerMP) sender).playerNetServerHandler.setPlayerLocation(spawn.posX, spawn.posY, spawn.posZ, sender.rotationYaw, sender.rotationPitch);
				sender.sendChatToPlayer(Localization.get(Localization.SPAWNED));
			}
		}
	}

	@Override
	public void processCommandConsole(ICommandSender sender, String[] args)
	{
		if (args.length >= 1)
		{
			EntityPlayer player = FMLCommonHandler.instance().getSidedDelegate().getServer().getConfigurationManager().getPlayerForUsername(args[0]);
			if (player != null)
			{
				PlayerInfo.getPlayerInfo((EntityPlayer) player).back = new WorldPoint(player);
				ChunkCoordinates spawn = player.getBedLocation();
				((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(spawn.posX, spawn.posY, spawn.posZ, player.rotationYaw, player.rotationPitch);
				player.sendChatToPlayer(Localization.get(Localization.SPAWNED));
			} else
				sender.sendChatToPlayer(Localization.format(Localization.ERROR_NOPLAYER, args[0]));
		}
	}

	@Override
	public boolean canConsoleUseCommand()
	{
		return true;
	}

	@Override
	public String getCommandPerm()
	{
		return "ForgeEssentials.BasicCommands." + getCommandName();
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	if(args.length == 1)
    	{
    		return getListOfStringsMatchingLastWord(args, FMLCommonHandler.instance().getMinecraftServerInstance().getAllUsernames());
    	}
    	else
    	{
    		return null;
    	}
    }
}
