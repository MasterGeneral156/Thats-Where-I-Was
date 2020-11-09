package themastergeneral.twiw.items;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.themastergeneral.ctdcore.item.CTDItem;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import themastergeneral.twiw.TWIW;

public class ItemTeleporter extends CTDItem {

	public ItemTeleporter() 
	{
		super(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1).maxDamage(5).isImmuneToFire());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) 
	{
	      return 0.0F;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		ItemStack stackIn = playerIn.getHeldItem(handIn);
		//Store coords in NBT
		if (playerIn.isSneaking())
		{
			setCoords(stackIn, playerIn);
			playerIn.sendStatusMessage(new TranslationTextComponent("Location set."), true);
			playerIn.getCooldownTracker().setCooldown(this, 10);
		}
		//TP to coords in NBT
		else
		{
			double posX = getCoordsX(stackIn);
			double posY = getCoordsY(stackIn);
			double posZ = getCoordsZ(stackIn);
			if (validTeleport(posX, posY, posZ))
			{
				stackIn.damageItem(1, playerIn, null);
				playerIn.getCooldownTracker().setCooldown(this, 200);
				playerIn.setPosition(posX, posY, posZ);
				double rposX = Math.round(stackIn.getTag().getDouble("posX") * 100.0) / 100.0;
				double rposY = Math.round(stackIn.getTag().getDouble("posY") * 100.0) / 100.0;
				double rposZ = Math.round(stackIn.getTag().getDouble("posZ") * 100.0) / 100.0;
				playerIn.sendStatusMessage(new TranslationTextComponent("Teleported to X: " + rposX + " Y: " + rposY + " Z: " + rposZ + "."), true);
			}
			else
			{
				playerIn.sendStatusMessage(new TranslationTextComponent("Please set a location by crouching and right clicking at the same time."), true);
			}
		}
		return ActionResult.resultPass(stackIn);
	}
	
	public static void setCoords(ItemStack stackIn, PlayerEntity playerIn)
	{		
		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putDouble("posX", playerIn.getPosX());
		compoundnbt.putDouble("posY", playerIn.getPosY());
		compoundnbt.putDouble("posZ", playerIn.getPosZ());
		stackIn.setTag(compoundnbt);
		
	}
	
	public static double getCoordsX(ItemStack stackIn)
	{
		CompoundNBT nbt = stackIn.getTag();
		return nbt.getDouble("posX");
	}
	
	public static double getCoordsY(ItemStack stackIn)
	{
		CompoundNBT nbt = stackIn.getTag();
		return nbt.getDouble("posY");
	}
	
	public static double getCoordsZ(ItemStack stackIn)
	{
		CompoundNBT nbt = stackIn.getTag();
		return nbt.getDouble("posZ");
	}
	
	public static boolean validTeleport(double posX, double posY, double posZ)
	{
		TWIW.LOGGER.info(posX);
		TWIW.LOGGER.info(posY);
		TWIW.LOGGER.info(posZ);
		if ((posX == 0.0D) && (posY < 1.0D) && (posZ == 0.0D))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) 
	{
		TWIW.LOGGER.info("Set defaults.");
		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putDouble("posX", Double.NaN);
		compoundnbt.putDouble("posY", Double.NaN);
		compoundnbt.putDouble("posZ", Double.NaN);
		stack.setTag(compoundnbt);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		double posX = Math.round(stack.getTag().getDouble("posX") * 100.0) / 100.0;
		double posY = Math.round(stack.getTag().getDouble("posY") * 100.0) / 100.0;
		double posZ = Math.round(stack.getTag().getDouble("posZ") * 100.0) / 100.0;
		tooltip.add(new TranslationTextComponent("Location X: " + posX + " Y: " + posY + " Z: " + posZ));
	}
}
