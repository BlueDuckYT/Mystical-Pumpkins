package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.MysticalPumpkinsMod;
import blueduck.mysticalpumpkins.block.EffectPumpkinItem;
import blueduck.mysticalpumpkins.block.InfuserBlock;
import blueduck.mysticalpumpkins.block.MysticalPumpkinBlock;
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
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterHandler {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MysticalPumpkinsMod.MODID);
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MysticalPumpkinsMod.MODID);
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MysticalPumpkinsMod.MODID);
	public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MysticalPumpkinsMod.MODID);

	public static final RegistryObject<Block> INFUSER = BLOCKS.register("infusion_table", () -> new InfuserBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED)));
	public static final RegistryObject<TileEntityType<InfuserTileEntity>> INFUSER_TILE_ENTITY = TILE_ENTITIES.register("infuser_tile_entity", () -> TileEntityType.Builder.create(InfuserTileEntity::new, INFUSER.get()).build(null));
	public static final RegistryObject<Item> INFUSER_ITEM = ITEMS.register("infusion_table", () -> new BlockItem(INFUSER.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> PUMPKIN_ESSENCE = ITEMS.register("pumpkin_essence", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<ContainerType<InfuserContainer>> INFUSER_CONTAINER = CONTAINERS.register("infuser", () -> new ContainerType<>(InfuserContainer::new));

	public static final RegistryObject<Block> WATER_PUMPKIN = BLOCKS.register("undrowning_pumpkin", () -> new MysticalPumpkinBlock());
	public static final RegistryObject<Item> WATER_PUMPKIN_ITEM = ITEMS.register("undrowning_pumpkin", () -> new EffectPumpkinItem(WATER_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.WATER_BREATHING));
	public static final RegistryObject<Block> LAVA_PUMPKIN = BLOCKS.register("cinder_pumpkin", () -> new MysticalPumpkinBlock());
	public static final RegistryObject<Item> LAVA_PUMPKIN_ITEM = ITEMS.register("cinder_pumpkin", () -> new EffectPumpkinItem(LAVA_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.FIRE_RESISTANCE));
	public static final RegistryObject<Block> LUCK_PUMPKIN = BLOCKS.register("luck_pumpkin", () -> new MysticalPumpkinBlock());
	public static final RegistryObject<Item> LUCK_PUMPKIN_ITEM = ITEMS.register("luck_pumpkin", () -> new EffectPumpkinItem(LUCK_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.LUCK));


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
