package cc.tweaked_programs.cccbridge.common.mixin.animatronic;

import cc.tweaked_programs.cccbridge.common.minecraft.block.AnimatronicBlock;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@SuppressWarnings("UnusedMixin") // I have no idea why this is unused, but I guess I trust the mod author.
@Mixin(CarriageContraption.class)
public abstract class MixinCarriageContraption extends Contraption {

    // Gradle throws an error here, but fuck if I know what it means :D
    @Shadow
    private List<BlockPos> assembledBlazeBurners;

    @Inject(method = "capture", at = @At("HEAD"), remap = false)
    public void cccbridge$capture(Level world, BlockPos pos, CallbackInfoReturnable<Pair<StructureTemplate.StructureBlockInfo, BlockEntity>> cir) {
        BlockState blockState = world.getBlockState(pos);

        if (blockState.getBlock() instanceof AnimatronicBlock) {
            assembledBlazeBurners.add(toLocalPos(pos));
        }
    }

}