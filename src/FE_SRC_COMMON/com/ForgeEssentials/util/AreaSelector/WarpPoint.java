package com.ForgeEssentials.util.AreaSelector;

import net.minecraft.entity.player.EntityPlayer;

import com.ForgeEssentials.data.SaveableObject;
import com.ForgeEssentials.data.SaveableObject.Reconstructor;
import com.ForgeEssentials.data.SaveableObject.SaveableField;
import com.ForgeEssentials.data.SaveableObject.UniqueLoadingKey;
import com.ForgeEssentials.data.TaggedClass;

@SaveableObject(SaveInline = true)
public class WarpPoint extends WorldPoint
{
	@SaveableField
	public float pitch;
	@SaveableField
	public float yaw;

	public WarpPoint(int dimension, int x, int y, int z, float playerPitch, float playerYaw)
	{
		super(dimension, x, y, z);
		this.pitch = playerPitch;
		this.yaw = playerYaw;
	}
	
	public WarpPoint(Point p, int dimension, float playerPitch, float playerYaw)
	{
		this(dimension, p.x, p.y, p.z, playerPitch, playerYaw);
	}
	
	public WarpPoint(WorldPoint p, float playerPitch, float playerYaw)
	{
		this(p.dim, p.x, p.y, p.z, playerPitch, playerYaw);
	}
	
	public WarpPoint(EntityPlayer sender) 
	{
		super(sender);
		this.pitch = sender.cameraPitch;
		this.yaw = sender.cameraYaw;
	}

	@Reconstructor()
	public static WarpPoint reconstruct(TaggedClass tag)
	{
		int x = (Integer) tag.getFieldValue("x");
		int y = (Integer) tag.getFieldValue("y");
		int z = (Integer) tag.getFieldValue("z");
		int dim = (Integer) tag.getFieldValue("dim");
		float pitch = (Float) tag.getFieldValue("pitch");
		float yaw = (Float) tag.getFieldValue("yaw");
		return new WarpPoint(dim, x, y, z, pitch, yaw);
	}
	
	@UniqueLoadingKey()
	private String getLoadingField()
	{
		return "warppoint_"+dim+"_"+x+"_"+y+"_"+z+"_"+pitch+"_"+yaw;
	}

}
