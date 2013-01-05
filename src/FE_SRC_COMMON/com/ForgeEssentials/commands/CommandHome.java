package com.ForgeEssentials.commands;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.ForgeEssentials.core.PlayerInfo;
import com.ForgeEssentials.core.commands.ForgeEssentialsCommandBase;
import com.ForgeEssentials.permission.PermissionsAPI;
import com.ForgeEssentials.permission.query.PermQueryPlayer;
import com.ForgeEssentials.util.FunctionHelper;
import com.ForgeEssentials.util.Localization;
import com.ForgeEssentials.util.OutputHandler;
import com.ForgeEssentials.util.AreaSelector.WorldPoint;

public class CommandHome extends ForgeEssentialsCommandBase
{

	@Override
	public String getCommandName()
	{
		return "home";
	}

	@Override
	public void processCommandPlayer(EntityPlayer sender, String[] args)
	{
		if (args.length == 0)
		{
			// homes aren't saving...
			WorldPoint home = PlayerInfo.getPlayerInfo(sender).home;
			if (home == null)
				OutputHandler.chatError(sender, Localization.get("message.error.nohome") + getSyntaxPlayer(sender));
			else
			{
				EntityPlayerMP player = ((EntityPlayerMP) sender);
				PlayerInfo playerInfo = PlayerInfo.getPlayerInfo(player);
				playerInfo.back = new WorldPoint(player);
				if (player.dimension != home.dim)
				{
					// Home is not in this dimension. Move the player.
					player.mcServer.getConfigurationManager().transferPlayerToDimension(player, home.dim);
				}
				player.playerNetServerHandler.setPlayerLocation(home.x + 0.5, home.y + 1, home.z + 0.5, player.rotationYaw, player.rotationPitch);
			}
		}
		else if (PermissionsAPI.checkPermAllowed(new PermQueryPlayer(sender, getCommandPerm()+".set")))
		{
			if (args.length >= 1 && (args[0].equals("here") || args[0].equals("set")))
			{
				WorldPoint p = FunctionHelper.getEntityPoint(sender);
				PlayerInfo.getPlayerInfo(sender).home = p;
				sender.sendChatToPlayer(Localization.format("command.home.confirm", p.x, p.y, p.z));
			}
			else if (args.length >= 3)
			{
				int x = 0;
				int y = 0;
				int z = 0;
				try
				{
					x = new Integer(args[0]);
				}
				catch (NumberFormatException e)
				{
					OutputHandler.chatError(sender, Localization.format(Localization.ERROR_NAN, args[0]));
					return;
				}
				try
				{
					y = new Integer(args[1]);
				}
				catch (NumberFormatException e)
				{
					OutputHandler.chatError(sender, Localization.format(Localization.ERROR_NAN, args[1]));
					return;
				}
				try
				{
					z = new Integer(args[2]);
				}
				catch (NumberFormatException e)
				{
					OutputHandler.chatError(sender, Localization.format(Localization.ERROR_NAN, args[2]));
					return;
				}
				WorldPoint p = new WorldPoint(sender.worldObj.getWorldInfo().getDimension(), x, y, z);
				PlayerInfo.getPlayerInfo(sender).home = p;
				sender.sendChatToPlayer(Localization.format("command.home.confirm", p.x, p.y, p.z));
			}
		}
	}

	@Override
	public void processCommandConsole(ICommandSender sender, String[] args)
	{
	}

	@Override
	public String getCommandPerm()
	{
		return "ForgeEssentials.BasicCommands." + getCommandName();
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		if (args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args, "here");
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean canConsoleUseCommand() 
	{
		return false;
	}
}
