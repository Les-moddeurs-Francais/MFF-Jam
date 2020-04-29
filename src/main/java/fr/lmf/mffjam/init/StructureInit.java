package fr.lmf.mffjam.init;

import fr.lmf.mffjam.utils.Utils;
import fr.lmf.mffjam.world.structure.IllagerTowerStructure;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructureInit {

    public static Structure<NoFeatureConfig> illagerTower = new IllagerTowerStructure(NoFeatureConfig::deserialize);

    @SubscribeEvent
    public static void registerFeature(final RegistryEvent.Register<Feature<?>> e)
    {

        e.getRegistry().registerAll(

                illagerTower.setRegistryName("illager_tower")

        );

    }

}