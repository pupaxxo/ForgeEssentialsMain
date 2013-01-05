package com.ForgeEssentials.commands;

import java.util.Arrays;
import java.util.List;

import com.ForgeEssentials.core.commands.ForgeEssentialsCommandBase;
import com.ForgeEssentials.permission.PermissionsAPI;
import com.ForgeEssentials.permission.query.PermQueryPlayer;
import com.ForgeEssentials.util.FunctionHelper;
import com.ForgeEssentials.util.Localization;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;

public class CommandGameMode extends ForgeEssentialsCommandBase
{
    public String getCommandName()
    {
        return "gamemode";
    }
    
    @Override
	public List getCommandAliases()
    {
		return Arrays.asList(new String[] {"gm"});
    }

	@Override
	public void processCommandPlayer(EntityPlayer sender, String[] args) 
	{
		if(args.length < 2)
		{
			if(args.length == 1)
			{
				sender.setGameType(getGameTypeFromString(sender, args[0]));
			}
			else
			{
				EnumGameType gm;
				if(((EntityPlayerMP) sender).theItemInWorldManager.getGameType().isCreative()) gm = EnumGameType.SURVIVAL;
				else gm = EnumGameType.CREATIVE;
				
				sender.setGameType(gm);
			}
		}
		else if(args.length == 2 && PermissionsAPI.checkPermAllowed(new PermQueryPlayer(sender, getCommandPerm() + ".others")))
		{
			EntityPlayer victim = FunctionHelper.getPlayerFromUsername(args[0]);
			if(args.length == 2)
			{
				victim.setGameType(getGameTypeFromString(sender, args[1]));
			}
			else
			{
				EnumGameType gm;
				if(((EntityPlayerMP) victim).theItemInWorldManager.getGameType().isSurvivalOrAdventure()) gm = EnumGameType.CREATIVE;
				else gm = EnumGameType.SURVIVAL;
				
				victim.setGameType(gm);
			}
			sender.sendChatToPlayer("Gamemode changed of " + victim.username);
		}
		else
		{
			sender.sendChatToPlayer(Localization.get(Localization.ERROR_BADSYNTAX) + getSyntaxPlayer(sender));
		}
	}

	@Override
	public void processCommandConsole(ICommandSender sender, String[] args) 
	{
		if(args.length <= 2)
		{
			EntityPlayer victim = FunctionHelper.getPlayerFromUsername(args[0]);
			if(args.length == 2)
			{
				victim.setGameType(getGameTypeFromString(sender, args[1]));
			}
			else
			{
				EnumGameType gm;
				if(((EntityPlayerMP) victim).theItemInWorldManager.getGameType().isSurvivalOrAdventure()) gm = EnumGameType.CREATIVE;
				else gm = EnumGameType.SURVIVAL;
				
				victim.setGameType(gm);
			}
			sender.sendChatToPlayer("Gamemode changed of " + victim.username);
		}
		else
		{
			sender.sendChatToPlayer(Localization.get(Localization.ERROR_BADSYNTAX) + getSyntaxConsole());
		}
	}
	
	public EnumGameType getGameTypeFromString(ICommandSender sender, String string)
	{
		return !string.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) && !string.equalsIgnoreCase("s") ? (!string.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) && !string.equalsIgnoreCase("c") ? (!string.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) && !string.equalsIgnoreCase("a") ? WorldSettings.getGameTypeById(parseIntBounded(sender, string, 0, 2)) : EnumGameType.ADVENTURE) : EnumGameType.CREATIVE) : EnumGameType.SURVIVAL;
	}

	@Override
	public boolean canConsoleUseCommand() 
	{
		return true;
	}

	@Override
	public String getCommandPerm() 
	{
		return "ForgeEssentials.BasicCommands.gamemode.self";
	}
	
	@Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        if(args.length == 2)
        {
        	return getListOfStringsMatchingLastWord(args, new String[] {"survival", "creative", "adventure"});
        }
        else if(args.length == 1)
        {
        	List match = getListOfStringsMatchingLastWord(args, FMLCommonHandler.instance().getMinecraftServerInstance().getAllUsernames());
        	if(match == null)
        		match = getListOfStringsMatchingLastWord(args, new String[] {"survival", "creative", "adventure"});
        	return match;
        }
        else
        {
        	return null;
        }
    }
}
