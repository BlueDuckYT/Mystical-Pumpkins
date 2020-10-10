package blueduck.mysticalpumpkins.utils;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpecialConstants {

	public static final Logger LOGGER = LogManager.getLogger("Mystical Pumpkins Mod");
	public static final String MODID = "mystical_pumpkins";
	public static final ResourceLocation PUMPKIN_INFUSER_TEX = new ResourceLocation(MODID, "textures/gui/container/pinfuser.png");
	public static final ResourceLocation PUMPKIN_INFUSER_JEI = new ResourceLocation(MODID, "textures/gui/container/jei_pinfuser.png");
	public static final ResourceLocation INFUSION_TABLE_UID = new ResourceLocation(MODID, "pumpkin_infusion_table");
}
