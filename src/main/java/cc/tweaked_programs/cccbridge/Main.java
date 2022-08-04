package cc.tweaked_programs.cccbridge;

import cc.tweaked_programs.cccbridge.block.source.SourceBlock;
import cc.tweaked_programs.cccbridge.block.source.SourceBlockDisplaySource;
import cc.tweaked_programs.cccbridge.block.source.SourceBlockEntity;
import cc.tweaked_programs.cccbridge.block.target.TargetBlock;
import cc.tweaked_programs.cccbridge.block.target.TargetBlockDisplayTarget;
import cc.tweaked_programs.cccbridge.block.target.TargetBlockEntity;
import com.simibubi.create.content.logistics.block.display.AllDisplayBehaviours;
import dan200.computercraft.ComputerCraft;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final String MOD_ID = "cccbridge";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Create Source Block
	private static final SourceBlock SOURCE_BLOCK = new SourceBlock();
	public static final BlockEntityType<SourceBlockEntity> SOURCE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "source_block_entity"), FabricBlockEntityTypeBuilder.create(SourceBlockEntity::new, SOURCE_BLOCK).build(null));
	// Create Target Block
	private static final TargetBlock TARGET_BLOCK = new TargetBlock();
	public static final BlockEntityType<TargetBlockEntity> TARGET_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "target_block_entity"), FabricBlockEntityTypeBuilder.create(TargetBlockEntity::new, TARGET_BLOCK).build(null));


	@Override
	public void onInitialize() {
		ItemGroup group;
		// Because CC likes to change things like this, here is a try catch thingy to solve it:
		try {
			group = ComputerCraft.MAIN_GROUP;
		} catch(NoSuchFieldError f) {
			group = ItemGroup.REDSTONE;
		}

		// Create Source Block
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "source_block"), SOURCE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "source_block"), new BlockItem(SOURCE_BLOCK, new FabricItemSettings().group(group)));
		AllDisplayBehaviours.assignTile(AllDisplayBehaviours.register(new Identifier(MOD_ID, "source_block_display_source"), new SourceBlockDisplaySource()), SOURCE_BLOCK_ENTITY);
		// Create Target Block
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "target_block"), TARGET_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "target_block"), new BlockItem(TARGET_BLOCK, new FabricItemSettings().group(group)));
		AllDisplayBehaviours.assignTile(AllDisplayBehaviours.register(new Identifier(MOD_ID, "target_block_display_source"), new TargetBlockDisplayTarget()), TARGET_BLOCK_ENTITY);
	}
}