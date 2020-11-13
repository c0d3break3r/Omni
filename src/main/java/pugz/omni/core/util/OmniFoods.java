package pugz.omni.core.util;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

@SuppressWarnings("deprecation")
public class OmniFoods {
    public static final Food ENCHANTED_GOLDEN_CARROT = (new Food.Builder()).hunger(3).saturation(1.8F).effect(new EffectInstance(Effects.JUMP_BOOST, 6000, 0), 1.0F).effect(new EffectInstance(Effects.NIGHT_VISION, 6000, 0), 1.0F).effect(new EffectInstance(Effects.SPEED, 6000, 0), 1.0F).effect(new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).setAlwaysEdible().build();
}