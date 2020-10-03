package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.MagicPumpkinsMod;
import blueduck.mysticalpumpkins.block.InfuserBlock;
import blueduck.mysticalpumpkins.client.gui.InfuserScreen;
import blueduck.mysticalpumpkins.container.InfuserContainer;
import blueduck.mysticalpumpkins.tileentity.InfuserTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterHandler {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicPumpkinsMod.MODID);
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MagicPumpkinsMod.MODID);
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MagicPumpkinsMod.MODID);
	public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MagicPumpkinsMod.MODID);

	public static final RegistryObject<Block> INFUSER = BLOCKS.register("infuser_block", () -> new InfuserBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED)));
	public static final RegistryObject<TileEntityType<InfuserTileEntity>> INFUSER_TILE_ENTITY = TILE_ENTITIES.register("infuser_tile_entity", () -> TileEntityType.Builder.create(InfuserTileEntity::new, INFUSER.get()).build(null));
	public static final RegistryObject<Item> INFUSER_ITEM = ITEMS.register("infuser", () -> new BlockItem(INFUSER.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<ContainerType<InfuserContainer>> INFUSER_CONTAINER = CONTAINERS.register("infuser", () -> new ContainerType<>(InfuserContainer::new));

	public static void initClient() {
		ScreenManager.registerFactory(INFUSER_CONTAINER.get(), InfuserScreen::new);
	}


	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
		TILE_ENTITIES.register(bus);
		CONTAINERS.register(bus);
	}

}
