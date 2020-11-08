package blueduck.mysticalpumpkins.registry;

import blueduck.mysticalpumpkins.block.*;
import blueduck.mysticalpumpkins.client.gui.InfusionTableScreen;
import blueduck.mysticalpumpkins.container.InfusionTableContainer;
import blueduck.mysticalpumpkins.entity.*;
import blueduck.mysticalpumpkins.item.GreenMagicBallItem;
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
import net.minecraft.entity.EntitySpawnPlacementRegistry;
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
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
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

	public static final RegistryObject<Item> EYE_OF_DREAD = ITEMS.register("eye_of_dread", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));


	public static final RegistryObject<Block> AQUASIGHT_PUMPKIN = BLOCKS.register("aquasight_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> AQUASIGHT_PUMPKIN_ITEM = ITEMS.register("aquasight_pumpkin", () -> new UnderwaterEffectPumpkinItem(AQUASIGHT_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.NIGHT_VISION));
	public static final RegistryObject<Block> WATER_PUMPKIN = BLOCKS.register("undrowning_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> WATER_PUMPKIN_ITEM = ITEMS.register("undrowning_pumpkin", () -> new NightVisionUnderwaterEffectPumpkinItem(WATER_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.WATER_BREATHING));
	public static final RegistryObject<Block> CONDUIT_PUMPKIN = BLOCKS.register("conduit_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> CONDUIT_PUMPKIN_ITEM = ITEMS.register("conduit_pumpkin", () -> new NightVisionUnderwaterEffectPumpkinItem(CONDUIT_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.CONDUIT_POWER));

	public static final RegistryObject<Block> NIGHT_VISION_PUMPKIN = BLOCKS.register("night_vision_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> NIGHT_VISION_PUMPKIN_ITEM = ITEMS.register("night_vision_pumpkin", () -> new EffectPumpkinItem(NIGHT_VISION_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.NIGHT_VISION));

	public static final RegistryObject<Block> LAVA_PUMPKIN = BLOCKS.register("cinder_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> LAVA_PUMPKIN_ITEM = ITEMS.register("cinder_pumpkin", () -> new EffectPumpkinItem(LAVA_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.FIRE_RESISTANCE));

	public static final RegistryObject<Block> WRATH_PUMPKIN = BLOCKS.register("wrath_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> WRATH_PUMPKIN_ITEM = ITEMS.register("wrath_pumpkin", () -> new WrathPumpkinItem(WRATH_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Block> FLOATING_PUMPKIN = BLOCKS.register("floating_pumpkin", MysticalPumpkinBlock::new);
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

	public static final RegistryObject<Block> MIRE_PUMPKIN = BLOCKS.register("mire_pumpkin", MysticalPumpkinBlock::new);
	public static final RegistryObject<Item> MIRE_PUMPKIN_ITEM = ITEMS.register("mire_pumpkin", () -> new EffectPumpkinItem(MIRE_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC), Effects.JUMP_BOOST));

	public static final RegistryObject<Block> DREAD_PUMPKIN = BLOCKS.register("dread_pumpkin", DreadPumpkinBlock::new);
	public static final RegistryObject<Item> DREAD_PUMPKIN_ITEM = ITEMS.register("dread_pumpkin", () -> new MysticalPumpkinItem(DREAD_PUMPKIN.get(), new Item.Properties().group(ItemGroup.MISC)));


	public static final RegistryObject<Block> PUMPKIN_MINE = BLOCKS.register("pumpkin_mine", PumpkinMineBlock::new);
	public static final RegistryObject<Item> PUMPKIN_MINE_ITEM = ITEMS.register("pumpkin_mine", () -> new BlockItem(PUMPKIN_MINE.get(), new Item.Properties().group(ItemGroup.MISC)));


	public static final RegistryObject<EntityType<DragourdEntity>> DRAGOURD = ENTITIES.register("dragourd", () -> EntityType.Builder.create(DragourdEntity::new, EntityClassification.MONSTER).size(0.9F, 1.1F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/dragourd.png").toString()));

	public static final RegistryObject<Item> DRAGOURD_SPAWN_EGG = ITEMS.register("dragourd_spawn_egg", () -> new MysticalPumpkinSpawnEgg(DRAGOURD, 16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<EnemyPumpkinionEntity>> ENEMY_PUMPKINION = ENTITIES.register("enemy_pumpkinion", () -> EntityType.Builder.create(EnemyPumpkinionEntity::new, EntityClassification.MONSTER).size(1.1F, 1.1F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> ENEMY_PUMPKINION_SPAWN_EGG = ITEMS.register("enemy_pumpkinion_spawn_egg", () -> new MysticalPumpkinSpawnEgg(ENEMY_PUMPKINION, 16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<FriendlyPumpkinionEntity>> FRIENDLY_PUMPKINION = ENTITIES.register("friendly_pumpkinion", () -> EntityType.Builder.create(FriendlyPumpkinionEntity::new, EntityClassification.MISC).size(0.9F, 0.9F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> FRIENDLY_PUMPKINION_SPAWN_EGG = ITEMS.register("friendly_pumpkinion_spawn_egg", () -> new MysticalPumpkinSpawnEgg(FRIENDLY_PUMPKINION,16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<SludgeEntity>> PUMPKIN_SLUDGE = ENTITIES.register("pumpkin_sludge", () -> EntityType.Builder.<SludgeEntity>create(SludgeEntity::new, EntityClassification.MISC).size(2.04F, 2.04F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> PUMPKIN_SLUDGE_SPAWN_EGG = ITEMS.register("pumpkin_sludge_spawn_egg", () -> new MysticalPumpkinSpawnEgg(PUMPKIN_SLUDGE,16743215, 13335343, new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> PUMPKIN_SCEPTER = ITEMS.register("pumpkin_scepter", () -> new ScepterItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(32)));

	public static final RegistryObject<EntityType<PumpklopsEntity>> PUMPKLOPS = ENTITIES.register("pumpklops", () -> EntityType.Builder.create(PumpklopsEntity::new, EntityClassification.MISC).size(0.9F, 1.85F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/pumpkinion.png").toString()));

	public static final RegistryObject<Item> GREEN_MAGIC_BALL_ITEM = ITEMS.register("green_fireball", () -> new GreenMagicBallItem(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<EntityType<GreenMagicBallEntity>> GREEN_MAGIC_BALL = ENTITIES.register("green_fireball", () -> EntityType.Builder.<GreenMagicBallEntity>create(GreenMagicBallEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).build(new ResourceLocation("mystical_pumpkins", "textures/entity/green_fireball.png").toString()));


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
//	@OnlyIn(Dist.CLIENT)
//	public static void registerRenderer() {
//		RenderingRegistry.registerEntityRenderingHandler(DRAGOURD.get(), DragourdRenderer::new);
//		RenderingRegistry.registerEntityRenderingHandler(ENEMY_PUMPKINION.get(), EnemyPumpkinionRenderer::new);
//		RenderingRegistry.registerEntityRenderingHandler(FRIENDLY_PUMPKINION.get(), FriendlyPumpkinionRenderer::new);
//		RenderingRegistry.registerEntityRenderingHandler(PUMPKIN_SLUDGE.get(), SludgeRenderer::new);
//		RenderingRegistry.registerEntityRenderingHandler(PUMPKLOPS.get(), PumpklopsRenderer::new);
//		RenderingRegistry.registerEntityRenderingHandler(GREEN_MAGIC_BALL.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
//	}

	public static void attributeStuff() {
		EntitySpawnPlacementRegistry.register(DRAGOURD.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DragourdEntity::canSpawn);

		GlobalEntityTypeAttributes.put(DRAGOURD.get(), DragourdEntity.setCustomAttributes().create()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(ENEMY_PUMPKINION.get(), EnemyPumpkinionEntity.setCustomAttributes().create()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(FRIENDLY_PUMPKINION.get(), FriendlyPumpkinionEntity.setCustomAttributes().create()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(PUMPKIN_SLUDGE.get(), SludgeEntity.setCustomAttributes().create()/*(or your own)*/);
		GlobalEntityTypeAttributes.put(PUMPKLOPS.get(), PumpklopsEntity.setCustomAttributes().create()/*(or your own)*/);

	}

}
