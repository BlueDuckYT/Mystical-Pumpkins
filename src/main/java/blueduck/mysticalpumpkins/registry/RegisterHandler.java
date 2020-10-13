package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.block.*;
import blueduck.mysticalpumpkins.client.gui.InfusionTableScreen;
import blueduck.mysticalpumpkins.client.renderer.DragourdRenderer;
import blueduck.mysticalpumpkins.client.renderer.EnemyPumpkinionRenderer;
import blueduck.mysticalpumpkins.client.renderer.FriendlyPumpkinionRenderer;
import blueduck.mysticalpumpkins.client.renderer.SludgeRenderer;
import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.entity.DragourdEntity;
import blueduck.mysticalpumpkins.entity.EnemyPumpkinionEntity;
import blueduck.mysticalpumpkins.entity.FriendlyPumpkinionEntity;
import blueduck.mysticalpumpkins.entity.SludgeEntity;
import blueduck.mysticalpumpkins.item.MysticalPumpkinSpawnEgg;
import blueduck.mysticalpumpkins.item.ScepterItem;
import blueduck.mysticalpumpkins.tileentity.InfusionTableTileEntity;
import blueduck.mysticalpumpkins.utils.SpecialConstants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterHandler {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpecialConstants.MODID);
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SpecialConstants.MODID);
	public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SpecialConstants.MODID);
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SpecialConstants.MODID);
	public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SpecialConstants.MODID);

	public static final RegistryObject<Block> INFUSION_TABLE = BLOCKS.register("infusion_table", () -> new InfusionTableBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED)));
	public static final RegistryObject<TileEntityType<InfusionTableTileEntity>> INFUSER_TILE_ENTITY = TILE_ENTITIES.register("infuser_tile_entity", () -> TileEntityType.Builder.create(InfusionTableTileEntity::new, INFUSION_TABLE.get()).build(null));
	public static final RegistryObject<Item> INFUSION_TABLE_ITEM = ITEMS.register("infusion_table", () -> new BlockItem(INFUSION_TABLE.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> PUMPKIN_ESSENCE = ITEMS.register("pumpkin_essence", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<ContainerType<InfusionTableContainer>> INFUSION_TABLE_CONTAINER = CONTAINERS.register("infuser", () -> new ContainerType<>(InfusionTableContainer::new));

	public static final RegistryObject<Item> HEART_OF_PUMPKLOPS = ITEMS.register("heart_of_pumpklops", () -> new Item(new Item.Properties().group(ItemGroup.MISC).rarity(Rarity.RARE)));


	public static final RegistryObject<Block> GRACE_PUMPKIN = BLOCKS.register("grace_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> GRACE_PUMPKIN_ITEM = ITEMS.register("grace_pumpkin", () -> new EffectPumpkinItem(GRACE_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.DOLPHINS_GRACE));
	public static final RegistryObject<Block> WATER_PUMPKIN = BLOCKS.register("undrowning_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> WATER_PUMPKIN_ITEM = ITEMS.register("undrowning_pumpkin", () -> new EffectPumpkinItem(WATER_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.WATER_BREATHING));
	public static final RegistryObject<Block> CONDUIT_PUMPKIN = BLOCKS.register("conduit_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> CONDUIT_PUMPKIN_ITEM = ITEMS.register("conduit_pumpkin", () -> new EffectPumpkinItem(CONDUIT_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.CONDUIT_POWER));

	public static final RegistryObject<Block> LAVA_PUMPKIN = BLOCKS.register("cinder_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> LAVA_PUMPKIN_ITEM = ITEMS.register("cinder_pumpkin", () -> new EffectPumpkinItem(LAVA_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.FIRE_RESISTANCE));

	public static final RegistryObject<Block> WRATH_PUMPKIN = BLOCKS.register("wrath_pumpkin", () -> new MysticalPumpkinBlock());
	public static final RegistryObject<Item> WRATH_PUMPKIN_ITEM = ITEMS.register("wrath_pumpkin", () -> new WrathPumpkinItem(WRATH_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Block> FLOATING_PUMPKIN = BLOCKS.register("floating_pumpkin", () -> new MysticalPumpkinBlock());
	public static final RegistryObject<Item> FLOATING_PUMPKIN_ITEM = ITEMS.register("floating_pumpkin", () -> new EffectPumpkinItem(FLOATING_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.SLOW_FALLING));
	public static final RegistryObject<Block> VOID_PUMPKIN = BLOCKS.register("void_pumpkin", () -> new LuminousMysticalPumpkinBlock(6));
	public static final RegistryObject<Item> VOID_PUMPKIN_ITEM = ITEMS.register("void_pumpkin", () -> new VoidPumpkinItem(VOID_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Block> GRAVITY_PUMPKIN = BLOCKS.register("gravity_pumpkin", () -> new LuminousMysticalPumpkinBlock(12));
	public static final RegistryObject<Item> GRAVITY_PUMPKIN_ITEM = ITEMS.register("gravity_pumpkin", () -> new GravityPumpkinItem(GRAVITY_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Block> GALAXY_PUMPKIN = BLOCKS.register("galaxy_pumpkin", () -> new LuminousMysticalPumpkinBlock(15));
	public static final RegistryObject<Item> GALAXY_PUMPKIN_ITEM = ITEMS.register("galaxy_pumpkin", () -> new GalaxyPumpkinItem(GALAXY_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));


	public static final RegistryObject<Block> LUCK_PUMPKIN = BLOCKS.register("luck_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> LUCK_PUMPKIN_ITEM = ITEMS.register("luck_pumpkin", () -> new EffectPumpkinItem(LUCK_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.LUCK));

	public static final RegistryObject<Block> HEALING_PUMPKIN = BLOCKS.register("healing_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> HEALING_PUMPKIN_ITEM = ITEMS.register("healing_pumpkin", () -> new EffectPumpkinItem(HEALING_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.REGENERATION));

	public static final RegistryObject<Block> MIRE_PUMPKIN = BLOCKS.register("mire_pumpkin", () -> new MirePumpkinBlock());
	public static final RegistryObject<Item> MIRE_PUMPKIN_ITEM = ITEMS.register("mire_pumpkin", () -> new MysticalPumpkinItem(MIRE_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<DragourdEntity>> DRAGOURD = ENTITIES.register("dragourd", () -> EntityType.Builder.<DragourdEntity>create(DragourdEntity::new, EntityClassification.MONSTER).size(0.9F, 1.1F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/dragourd.png").toString()));

	public static final RegistryObject<Item> DRAGOURD_SPAWN_EGG = ITEMS.register("dragourd_spawn_egg", () -> new MysticalPumpkinSpawnEgg(() -> DRAGOURD.get(),16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<EnemyPumpkinionEntity>> ENEMY_PUMPKINION = ENTITIES.register("enemy_pumpkinion", () -> EntityType.Builder.<EnemyPumpkinionEntity>create(EnemyPumpkinionEntity::new, EntityClassification.MONSTER).size(1.1F, 1.1F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> ENEMY_PUMPKINION_SPAWN_EGG = ITEMS.register("enemy_pumpkinion_spawn_egg", () -> new MysticalPumpkinSpawnEgg(() -> ENEMY_PUMPKINION.get(),16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<FriendlyPumpkinionEntity>> FRIENDLY_PUMPKINION = ENTITIES.register("friendly_pumpkinion", () -> EntityType.Builder.<FriendlyPumpkinionEntity>create(FriendlyPumpkinionEntity::new, EntityClassification.MISC).size(1.1F, 1.1F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> FRIENDLY_PUMPKINION_SPAWN_EGG = ITEMS.register("friendly_pumpkinion_spawn_egg", () -> new MysticalPumpkinSpawnEgg(() -> FRIENDLY_PUMPKINION.get(),16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<SludgeEntity>> PUMPKIN_SLUDGE = ENTITIES.register("pumpkin_sludge", () -> EntityType.Builder.<SludgeEntity>create(SludgeEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> PUMPKIN_SLUDGE_SPAWN_EGG = ITEMS.register("pumpkin_sludge_spawn_egg", () -> new MysticalPumpkinSpawnEgg(() -> PUMPKIN_SLUDGE.get(),16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> PUMPKIN_SCEPTER = ITEMS.register("pumpkin_scepter", () -> new ScepterItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(32)));


	public static void initClient() {
		ScreenManager.registerFactory(INFUSION_TABLE_CONTAINER.get(), InfusionTableScreen::new);
	}

	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
		ENTITIES.register(bus);
		TILE_ENTITIES.register(bus);
		CONTAINERS.register(bus);
	}

	//Can this method be deleted?
	@OnlyIn(Dist.CLIENT)
	public static void registerRenderer() {
		RenderingRegistry.registerEntityRenderingHandler((EntityType) DRAGOURD.get(), (manager) -> {
			return new DragourdRenderer(manager);
		});
		RenderingRegistry.registerEntityRenderingHandler((EntityType) ENEMY_PUMPKINION.get(), (manager) -> {
			return new EnemyPumpkinionRenderer(manager);
		});
		RenderingRegistry.registerEntityRenderingHandler((EntityType) FRIENDLY_PUMPKINION.get(), (manager) -> {
			return new FriendlyPumpkinionRenderer(manager);
		});
		RenderingRegistry.registerEntityRenderingHandler((EntityType) PUMPKIN_SLUDGE.get(), (manager) -> {
			return new SludgeRenderer(manager);
		});
	}
	public static void attributeStuff() {
		GlobalEntityTypeAttributes.put(DRAGOURD.get(), DragourdEntity.setCustomAttributes().func_233813_a_()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(ENEMY_PUMPKINION.get(), EnemyPumpkinionEntity.setCustomAttributes().func_233813_a_()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(FRIENDLY_PUMPKINION.get(), FriendlyPumpkinionEntity.setCustomAttributes().func_233813_a_()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(PUMPKIN_SLUDGE.get(), SludgeEntity.setCustomAttributes().func_233813_a_()/*(or your own)*/);
	}

}
