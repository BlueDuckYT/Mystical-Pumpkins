package blueduck.registry;

import blueduck.MagicPumpkinsMod;
import blueduck.block.InfuserBlock;
import blueduck.tileentity.InfuserTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterHandler {

	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MagicPumpkinsMod.MODID);
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MagicPumpkinsMod.MODID);

	public static final RegistryObject<Block> INFUSER = BLOCKS.register("infuser", () -> new InfuserBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED)));
	public static final RegistryObject<TileEntityType<InfuserTileEntity>> INFUSER_TILE_ENTITY = TILE_ENTITIES.register("infuser_tile_entity", () -> TileEntityType.Builder.create(InfuserTileEntity::new, INFUSER.get()).build(null));

	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		TILE_ENTITIES.register(bus);
	}

}
