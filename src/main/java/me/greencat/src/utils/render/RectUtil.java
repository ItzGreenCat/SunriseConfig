package me.greencat.src.utils.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class RectUtil {
    public static void drawBorderedRoundedRect(float x, float y, float width, float height, float radius, float linewidth, int insideC, int borderC) {
        drawRoundRect(x, y, x + width, y + height, radius, insideC);
        drawOutlinedRoundedRect(x, y, width, height, radius, linewidth, borderC);
    }

    public static void drawOutlinedRoundedRect(float x, float y, float width, float height, float radius, float linewidth, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double x1 = x + width;
        double y1 = y + height;
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0D;
        y1 *= 2.0D;
        GL11.glLineWidth(linewidth);
        GL11.glDisable(3553);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(2848);
        GL11.glBegin(2);

        int i;
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double) (x + radius) + sin((double) i * 3.141592653589793D / 180.0D) * (double) (radius * -1.0F), (double) (y + radius) + cos((double) i * 3.141592653589793D / 180.0D) * (double) (radius * -1.0F));
        }

        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double) (x + radius) + sin((double) i * 3.141592653589793D / 180.0D) * (double) (radius * -1.0F), y1 - (double) radius + cos((double) i * 3.141592653589793D / 180.0D) * (double) (radius * -1.0F));
        }

        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - (double) radius + sin((double) i * 3.141592653589793D / 180.0D) * (double) radius, y1 - (double) radius + cos((double) i * 3.141592653589793D / 180.0D) * (double) radius);
        }

        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - (double) radius + sin((double) i * 3.141592653589793D / 180.0D) * (double) radius, (double) (y + radius) + cos((double) i * 3.141592653589793D / 180.0D) * (double) radius);
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRoundRect(float left, float top, float right, float bottom, float radius, int color) {
        left += radius;
        right -= radius;
        float f3;
        if (left < right) {
            f3 = left;
            left = right;
            right = f3;
        }

        if (top < bottom) {
            f3 = top;
            top = bottom;
            bottom = f3;
        }

        f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(right - radius, top - radius, 0.0D).endVertex();
        worldrenderer.pos(right, top - radius, 0.0D).endVertex();
        worldrenderer.pos(right, bottom + radius, 0.0D).endVertex();
        worldrenderer.pos(right - radius, bottom + radius, 0.0D).endVertex();
        tessellator.draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, top - radius, 0.0D).endVertex();
        worldrenderer.pos(left + radius, top - radius, 0.0D).endVertex();
        worldrenderer.pos(left + radius, bottom + radius, 0.0D).endVertex();
        worldrenderer.pos(left, bottom + radius, 0.0D).endVertex();
        tessellator.draw();
        drawArc(right, bottom + radius, radius, 180);
        drawArc(left, bottom + radius, radius, 90);
        drawArc(right, top - radius, radius, 270);
        drawArc(left, top - radius, radius, 0);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    public static void drawArc(float x, float y, float radius, int angleStart) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
        GlStateManager.translate(x, y, 0.0D);
        worldrenderer.pos(0.0D, 0.0D, 0.0D).endVertex();
        int points = 21;

        for (double i = 0.0D; i < (double) points; ++i) {
            double radians = Math.toRadians(i / (double) points * 90.0D + (double) angleStart);
            worldrenderer.pos((double) radius * sin(radians), (double) radius * cos(radians), 0.0D).endVertex();
        }

        tessellator.draw();
        GlStateManager.translate(-x, -y, 0.0D);
    }
}
