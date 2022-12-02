package net.examplemod;

import com.google.common.base.Suppliers;
#if POST_MC_1_16_5
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
#else
import me.shedaniel.architectury.platform.Platform;
import me.shedaniel.architectury.registry.CreativeTabs;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.Registries;
import me.shedaniel.architectury.registry.RegistrySupplier;
#endif
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ExampleMod {
    public static final String MOD_ID = "examplemod";

    private static final ResourceLocation EXAMPLE_TAB_RL = new ResourceLocation(MOD_ID, "example_tab");

    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

    // Registering a new creative tab
    #if POST_MC_1_16_5
    public static final CreativeModeTab EXAMPLE_TAB = CreativeTabRegistry.create(EXAMPLE_TAB_RL, () ->
            new ItemStack(ExampleMod.EXAMPLE_ITEM.get()));
    #else
    public static final CreativeModeTab EXAMPLE_TAB = CreativeTabs.create(EXAMPLE_TAB_RL, () ->
            new ItemStack(ExampleMod.EXAMPLE_ITEM.get()));
    #endif
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Properties().tab(ExampleMod.EXAMPLE_TAB)));
    
    public static void init() {
        ITEMS.register();
        
        System.out.println(ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());

        #if POST_CURRENT_MC_1_16_5 && MC_1_19_2
        System.out.println("Is post 1.16.5 and is 1.19.2");
        #elif POST_CURRENT_MC_1_16_5
        System.out.println("Is 1.16.5 or post 1.16.5");
        #endif

        #if POST_MC_1_16_5
        System.out.println("Is post 1.16.5");
        #elif PRE_MC_1_18_2
        System.out.println("Is pre 1.18.2");
        #endif

        #if PRE_CURRENT_MC_1_18_2
        System.out.println("Is 1.18.2 or pre 1.18.2");
        #endif

        if (Platform.isFabric()) {
            System.out.println("Is Fabric");
        } else if (Platform.isForge()) {
            System.out.println("Is Forge");
        }
    }
}
