package pugz.omni.core.module;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HallowsEveModule extends AbstractModule {
    public static final HallowsEveModule instance = new HallowsEveModule();

    public HallowsEveModule() {
        super("Hallows Eve");
    }

    @Override
    protected void sendInitMessage() {
        System.out.println("BOO! Happy Hallow's Eve!");
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void onClientInitialize() {
    }

    @Override
    protected void onPostInitialize() {
    }
}