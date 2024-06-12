package cc.tweaked_programs.cccbridge.common.minecraft.block;

import cc.tweaked_programs.cccbridge.common.minecraft.blockEntity.TargetBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TargetBlock extends Block implements EntityBlock {

    public static final Properties TARGET_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.METAL);

    public TargetBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TargetBlockEntity(pos, state);
    }

}