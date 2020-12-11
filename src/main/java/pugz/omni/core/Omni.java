package pugz.omni.core;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import pugz.omni.core.module.*;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.stats.StatType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Omni.MOD_ID)
public class Omni {
    public static final String MOD_ID = "omni";

    public Omni() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Registries.BLOCKS.register(eventBus);
        OverrideRegistries.BLOCKS.register(eventBus);
        Registries.ITEMS.register(eventBus);
        OverrideRegistries.ITEMS.register(eventBus);
        Registries.TILE_ENTITIES.register(eventBus);
        Registries.ENTITIES.register(eventBus);
        Registries.BIOMES.register(eventBus);
        OverrideRegistries.BIOMES.register(eventBus);
        Registries.SURFACE_BUILDERS.register(eventBus);
        Registries.CARVERS.register(eventBus);
        Registries.FEATURES.register(eventBus);
        Registries.STRUCTURES.register(eventBus);
        Registries.ENCHANTMENTS.register(eventBus);
        OverrideRegistries.ENCHANTMENTS.register(eventBus);
        Registries.EFFECTS.register(eventBus);
        Registries.SOUNDS.register(eventBus);
        Registries.PARTICLES.register(eventBus);
        Registries.STATS.register(eventBus);

        registerModuleInit();
        eventBus.addListener(this::setupCommon);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(EventPriority.LOWEST, this::setupClient);
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CoreModule.Configuration.COMMON_SPEC);
    }

    private void registerModuleInit() {
        CoreModule.instance.initialize();

        CavierCavesModule.instance.initialize();
        ColormaticModule.instance.initialize();
        WildWestModule.instance.initialize();
        FieryNetherModule.instance.initialize();
        ForestryModule.instance.initialize();
        //HallowsEveModule.instance.initialize();
        MiscellaneousModule.instance.initialize();
        ParadiseModule.instance.initialize();
        WintertimeModule.instance.initialize();
    }

    @OnlyIn(Dist.CLIENT)
    private void registerModuleClient() {
        CoreModule.instance.initializeClient();

        CavierCavesModule.instance.initializeClient();
        ColormaticModule.instance.initializeClient();
        WildWestModule.instance.initializeClient();
        FieryNetherModule.instance.initializeClient();
        ForestryModule.instance.initializeClient();
        //HallowsEveModule.instance.initializeClient();
        MiscellaneousModule.instance.initializeClient();
        ParadiseModule.instance.initializeClient();
        WintertimeModule.instance.initializeClient();
    }

    private void registerModulePost() {
        CoreModule.instance.initializePost();

        CavierCavesModule.instance.initializePost();
        ColormaticModule.instance.initializePost();
        WildWestModule.instance.initializePost();
        FieryNetherModule.instance.initializePost();
        ForestryModule.instance.initializePost();
        //HallowsEveModule.instance.initializePost();
        MiscellaneousModule.instance.initializePost();
        ParadiseModule.instance.initializePost();
        WintertimeModule.instance.initializePost();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        registerModulePost();
        //CompatReferences.initializeReferences();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        registerModuleClient();
    }

    public static class Registries {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
        public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
        public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
        public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MOD_ID);
        public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, MOD_ID);
        public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, MOD_ID);
        public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);
        public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);
        public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
        public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID);
        public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
        public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
        public static final DeferredRegister<StatType<?>> STATS = DeferredRegister.create(ForgeRegistries.STAT_TYPES, MOD_ID);
    }

    public static class OverrideRegistries {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
        public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "minecraft");
        public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "minecraft");
    }
}