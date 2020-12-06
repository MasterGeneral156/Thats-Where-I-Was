package themastergeneral.twiw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import themastergeneral.twiw.items.TWIWItems;

@Mod("twiw")
public class TWIW 
{
	public static final Logger LOGGER = LogManager.getLogger();
    public static TWIW instance;
    public TWIW() 
    {
    	instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	MinecraftForge.EVENT_BUS.register(this);
    	
    	ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    
    private void setup(final FMLCommonSetupEvent event)
    {
		LOGGER.info("That's Where I Was! is launching.");
    }
    
    public static class ItemRegistry 
    {
    	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModUtils.ModID);
    	public static final RegistryObject<Item> item_teleporter = ItemRegistry.ITEMS.register("item_teleporter", () -> TWIWItems.item_teleporter);
    	public static final RegistryObject<Item> item_teleporter_2 = ItemRegistry.ITEMS.register("item_teleporter_2", () -> TWIWItems.item_teleporter_2);
    	public static final RegistryObject<Item> item_teleporter_3 = ItemRegistry.ITEMS.register("item_teleporter_3", () -> TWIWItems.item_teleporter_3);
    	public static final RegistryObject<Item> item_teleporter_4 = ItemRegistry.ITEMS.register("item_teleporter_4", () -> TWIWItems.item_teleporter_4);
    	public static final RegistryObject<Item> item_teleporter_5 = ItemRegistry.ITEMS.register("item_teleporter_5", () -> TWIWItems.item_teleporter_5);
    }
}
