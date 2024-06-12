package cc.tweaked_programs.cccbridge.client.blockEntityRenderer;

import cc.tweaked_programs.cccbridge.client.animatronic.AnimatronicModel;
import cc.tweaked_programs.cccbridge.common.assistance.Randomness;
import cc.tweaked_programs.cccbridge.common.minecraft.block.AnimatronicBlock;
import cc.tweaked_programs.cccbridge.common.minecraft.blockEntity.AnimatronicBlockEntity;
import cc.tweaked_programs.cccbridge.common.modloader.CCCBridge;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static cc.tweaked_programs.cccbridge.client.animatronic.AnimatronicModel.createBodyLayer;

@OnlyIn(Dist.CLIENT)
public class AnimatronicBlockEntityRenderer implements BlockEntityRenderer<AnimatronicBlockEntity> {

    public static final ResourceLocation TEXTURE_BODY = new ResourceLocation(CCCBridge.MOD_ID, "textures/entity/animatronic/body.png");

    public static final ResourceLocation TEXTURE_FACE_NORMAL = new ResourceLocation(CCCBridge.MOD_ID, "textures/entity/animatronic/face_normal.png");
    public static final ResourceLocation TEXTURE_FACE_HAPPY = new ResourceLocation(CCCBridge.MOD_ID, "textures/entity/animatronic/face_happy.png");
    public static final ResourceLocation TEXTURE_FACE_QUESTION = new ResourceLocation(CCCBridge.MOD_ID, "textures/entity/animatronic/face_question.png");
    public static final ResourceLocation TEXTURE_FACE_SAD = new ResourceLocation(CCCBridge.MOD_ID, "textures/entity/animatronic/face_sad.png");

    public static final ResourceLocation TEXTURE_FACE_CURSED = new ResourceLocation(CCCBridge.MOD_ID, "textures/entity/animatronic/face_creepy.png");

    private static final float scale = 1f / 32f * (32f - 4f);
    private final AnimatronicModel<AnimatronicBlockEntity> model = new AnimatronicModel<>(createBodyLayer().bakeRoot());

    @SuppressWarnings("unused")
    public AnimatronicBlockEntityRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    @SuppressWarnings("unused")
    public void render(@NotNull AnimatronicBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.scale(-scale, -scale, scale);
        poseStack.translate(-0.5666F,  -1.5F, 0.5666F);

        this.model.setupAnim(blockEntity, 0.0F, 0.0F, partialTick, 0.0F, 0.0F);

        // Train hat
        if (blockEntity.getLevel() instanceof VirtualRenderWorld virtualRenderWorld)
            this.model.hasJob(true); // Normally, we would check for the block being an actual driver for the contraption. Let's just give him a hat anyway.
        else
            this.model.hasJob(blockEntity.getBlockState().getValue(AnimatronicBlock.IS_DRIVER));

        // Render model
        VertexConsumer vertexConsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_BODY));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        // Eyes
        vertexConsumer = bufferSource.getBuffer(this.model.renderType(Randomness.rareCreepiness() ? TEXTURE_FACE_CURSED : AnimatronicModel.getFace(blockEntity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, Randomness.lightFlickering(), OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        // Checkout
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull AnimatronicBlockEntity blockEntity) {
        return true;
    }

}