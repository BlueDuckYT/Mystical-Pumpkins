package blueduck.mysticalpumpkins.utils;


import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class MysticalPumpkinsConfig {

    public ConfigHelper.ConfigValueListener<List<? extends String>> DRAGOURD_SPAWN_BIOMES;
    public ConfigHelper.ConfigValueListener<Boolean> DRAGOURD_SPAWN_EVERYWHERE_ON_FULL_MOON;

    public MysticalPumpkinsConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.push("General");
        ArrayList<String> BIOMES = Lists.newArrayList();
        BIOMES.add(Biomes.DARK_FOREST.getLocation().toString());
        BIOMES.add(Biomes.DARK_FOREST_HILLS.getLocation().toString());
        this.DRAGOURD_SPAWN_BIOMES = subscriber.subscribe(builder
                .comment("Specifies biomes that Dragourds can spawn in.")
                .defineList("Dragourd_Spawn_Biomes", BIOMES, o -> o instanceof String));

        this.DRAGOURD_SPAWN_EVERYWHERE_ON_FULL_MOON = subscriber.subscribe(builder
                .comment("Should Dragourds spawn everywhere on full moons?")
                .define("Dragourds_Spawn_Everywhere", true, o -> o instanceof Boolean));
        builder.pop();
    }

}
