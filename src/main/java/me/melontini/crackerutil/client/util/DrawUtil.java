package me.melontini.crackerutil.client.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class provides a set of utility methods for drawing on screen.
 *
 * <p>It mirrors some of the methods from the Screen and DrawableHelper classes
 * to allow using them without creating an instance of a screen. Most int values
 * in those methods have been replaced by floats to allow for more flexibility.</p>
 */

@Environment(EnvType.CLIENT)
public class DrawUtil {
    private DrawUtil() {
        throw new UnsupportedOperationException();
    }
    public static void renderTooltip(MatrixStack matrices, ItemStack stack, float x, float y) {
        renderTooltip(matrices, getTooltipFromItem(stack), stack.getTooltipData(), x, y);
    }

    public static void renderTooltip(MatrixStack matrices, List<Text> lines, Optional<TooltipData> data, float x, float y) {
        List<TooltipComponent> list = lines.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Collectors.toList());
        data.ifPresent(datax -> list.add(1, TooltipComponent.of(datax)));
        renderTooltipFromComponents(matrices, list, x, y);
    }

    public static List<Text> getTooltipFromItem(ItemStack stack) {
        var client = MinecraftClient.getInstance();
        return stack.getTooltip(client.player, client.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL);
    }

    public static void renderTooltip(MatrixStack matrices, Text text, float x, float y) {
        renderOrderedTooltip(matrices, Arrays.asList(text.asOrderedText()), x, y);
    }

    public static void renderTooltip(MatrixStack matrices, List<Text> lines, float x, float y) {
        renderOrderedTooltip(matrices, Lists.transform(lines, Text::asOrderedText), x, y);
    }

    public static void renderOrderedTooltip(MatrixStack matrices, List<? extends OrderedText> lines, float x, float y) {
        renderTooltipFromComponents(matrices, lines.stream().map(TooltipComponent::of).collect(Collectors.toList()), x, y);
    }

    public static void renderTooltipFromComponents(MatrixStack matrices, List<TooltipComponent> components, float x, float y) {
        var itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        var textRenderer = MinecraftClient.getInstance().textRenderer;
        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        if (!components.isEmpty()) {
            int i = 0;
            int j = components.size() == 1 ? -2 : 0;

            for (TooltipComponent tooltipComponent : components) {
                int k = tooltipComponent.getWidth(textRenderer);
                if (k > i) {
                    i = k;
                }

                j += tooltipComponent.getHeight();
            }

            float l = x + 12;
            float m = y - 12;
            if (l + i > width) {
                l -= 28 + i;
            }

            if (m + j + 6 > height) {
                m = height - j - 6;
            }


            matrices.push();
            float f = itemRenderer.zOffset;
            itemRenderer.zOffset = 400.0F;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 4, l + i + 3, m - 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 3, m + j + 3, l + i + 3, m + j + 4, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 3, l + i + 3, m + j + 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 4, m - 3, l - 3, m + j + 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l + i + 3, m - 3, l + i + 4, m + j + 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 3 + 1, l - 3 + 1, m + j + 3 - 1, 400, 1347420415, 1344798847);
            fillGradient(matrix4f, bufferBuilder, l + i + 2, m - 3 + 1, l + i + 3, m + j + 3 - 1, 400, 1347420415, 1344798847);
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 3, l + i + 3, m - 3 + 1, 400, 1347420415, 1347420415);
            fillGradient(matrix4f, bufferBuilder, l - 3, m + j + 2, l + i + 3, m + j + 3, 400, 1344798847, 1344798847);
            RenderSystem.enableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            BufferRenderer.drawWithShader(bufferBuilder.end());
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            matrices.translate(0.0, 0.0, 400.0);
            float s = m;

            for (int t = 0; t < components.size(); ++t) {
                TooltipComponent tooltipComponent2 = components.get(t);
                if (tooltipComponent2 instanceof OrderedTextTooltipComponent) {
                    textRenderer.draw(((OrderedTextTooltipComponent) tooltipComponent2).text, l, s, -1, true, matrix4f, immediate, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
                } else {
                    RenderSystem.getModelViewStack().push();
                    RenderSystem.getModelViewStack().translate(l - (int) (l), s - (int) (s), 0);
                    RenderSystem.applyModelViewMatrix();
                    tooltipComponent2.drawText(textRenderer, (int) l, (int) s, matrix4f, immediate);
                    RenderSystem.getModelViewStack().pop();
                    RenderSystem.applyModelViewMatrix();
                }
                s += tooltipComponent2.getHeight() + (t == 0 ? 2 : 0);
            }

            immediate.draw();
            matrices.pop();
            s = m;

            for (int t = 0; t < components.size(); ++t) {
                TooltipComponent tooltipComponent2 = components.get(t);

                RenderSystem.getModelViewStack().push();
                RenderSystem.getModelViewStack().translate(l - (int) (l), s - (int) (s), 0);
                RenderSystem.applyModelViewMatrix();
                tooltipComponent2.drawItems(textRenderer, (int) l, (int) s, matrices, itemRenderer, 400);
                RenderSystem.getModelViewStack().pop();
                RenderSystem.applyModelViewMatrix();

                s += tooltipComponent2.getHeight() + (t == 0 ? 2 : 0);
            }

            itemRenderer.zOffset = f;
        }
    }

    public static void fillGradient(MatrixStack matrices, float startX, float startY, float endX, float endY, float z, int colorStart, int colorEnd) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        fillGradient(matrices.peek().getPositionMatrix(), bufferBuilder, startX, startY, endX, endY, z, colorStart, colorEnd);
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    public static void fillGradient(Matrix4f matrix, BufferBuilder builder, float startX, float startY, float endX, float endY, float z, int colorStart, int colorEnd) {
        float f = (float) (colorStart >> 24 & 0xFF) / 255.0F;
        float g = (float) (colorStart >> 16 & 0xFF) / 255.0F;
        float h = (float) (colorStart >> 8 & 0xFF) / 255.0F;
        float i = (float) (colorStart & 0xFF) / 255.0F;
        float j = (float) (colorEnd >> 24 & 0xFF) / 255.0F;
        float k = (float) (colorEnd >> 16 & 0xFF) / 255.0F;
        float l = (float) (colorEnd >> 8 & 0xFF) / 255.0F;
        float m = (float) (colorEnd & 0xFF) / 255.0F;
        builder.vertex(matrix, endX, startY, z).color(g, h, i, f).next();
        builder.vertex(matrix, startX, startY, z).color(g, h, i, f).next();
        builder.vertex(matrix, startX, endY, z).color(k, l, m, j).next();
        builder.vertex(matrix, endX, endY, z).color(k, l, m, j).next();
    }

    public static void drawTexture(MatrixStack matrices, float x, float y, float z, float u, float v, float width, float height) {
        drawTexture(matrices, x, y, z, u, v, width, height, 256, 256);
    }

    public static void drawTexture(MatrixStack matrices, float x, float y, float z, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        drawTexture(matrices, x, x + width, y, y + height, z, width, height, u, v, textureWidth, textureHeight);
    }

    public static void drawTexture(MatrixStack matrices, float x, float y, float width, float height, float u, float v, float regionWidth, float regionHeight, float textureWidth, float textureHeight) {
        drawTexture(matrices, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight);
    }

    public static void drawTexture(MatrixStack matrices, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        drawTexture(matrices, x, y, width, height, u, v, width, height, textureWidth, textureHeight);
    }

    public static void drawTexture(MatrixStack matrices, float x0, float x1, float y0, float y1, float z, float regionWidth, float regionHeight, float u, float v, float textureWidth, float textureHeight) {
        drawTexturedQuad(matrices.peek().getPositionMatrix(), x0, x1, y0, y1, z, (u + 0.0F) / textureWidth, (u + regionWidth) / textureWidth, (v + 0.0F) / textureHeight, (v + regionHeight) / textureHeight);
    }

    public static void drawTexturedQuad(Matrix4f matrix, float x0, float x1, float y0, float y1, float z, float u0, float u1, float v0, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, x0, y1, z).texture(u0, v1).next();
        bufferBuilder.vertex(matrix, x1, y1, z).texture(u1, v1).next();
        bufferBuilder.vertex(matrix, x1, y0, z).texture(u1, v0).next();
        bufferBuilder.vertex(matrix, x0, y0, z).texture(u0, v0).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());
    }

    public void renderGuiItemModelWithCustomMatrix(MatrixStack matrixStack, ItemStack stack, int x, int y, BakedModel model) {
        MinecraftClient client = MinecraftClient.getInstance();

        client.getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.push();
        matrixStack.translate(x, y, 100.0F + client.getItemRenderer().zOffset);
        matrixStack.translate(8.0, 8.0, 0.0);
        matrixStack.scale(1.0F, -1.0F, 1.0F);
        matrixStack.scale(16.0F, 16.0F, 16.0F);

        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        boolean bl = !model.isSideLit();
        if (bl) {
            DiffuseLighting.disableGuiDepthLighting();
        }

        client.getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, false, matrixStack, immediate, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, model);
        immediate.draw();
        RenderSystem.enableDepthTest();
        if (bl) {
            DiffuseLighting.enableGuiDepthLighting();
        }

        matrixStack.pop();
    }
}
