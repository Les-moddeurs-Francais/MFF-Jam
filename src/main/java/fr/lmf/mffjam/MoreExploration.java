package fr.lmf.mffjam;

import fr.lmf.mffjam.event.FallingEvent;
import fr.lmf.mffjam.init.BlockInit;
import fr.lmf.mffjam.init.EntityInit;
import fr.lmf.mffjam.init.ItemInit;
import fr.lmf.mffjam.init.TileEntityInit;
import fr.lmf.mffjam.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Utils.MODID)
public class MoreExploration
{
	private static final Logger LOGGER = LogManager.getLogger();

	public static final ItemGroup me_group = new ItemGroup("me_group")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemInit.PLAYER_SLING.get());
		}
	};

	public MoreExploration()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.REGISTRY.register(bus);
		BlockInit.REGISTRY.register(bus);
		TileEntityInit.REGISTRY.register(bus);
		EntityInit.REGISTRY.register(bus);

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new FallingEvent());
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		LOGGER.info("HELLO FROM PREINIT");
		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	private void doClientStuff(final FMLClientSetupEvent event)
	{
		LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{
		//InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
	}

	private void processIMC(final InterModProcessEvent event)
	{
        /*
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
        */
	}
}
