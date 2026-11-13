package com.victusclient.module.modules;

import com.victusclient.module.Category;
import com.victusclient.module.HudModule;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/** Shows the distance of your last hit (display only). */
public class ReachModule extends HudModule {
    private double last = -1;

    public ReachModule() {
        super("Reach", Category.PVP, 6, 150);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (enabled && player == MinecraftClient.getInstance().player) {
                Vec3d eye = player.getEyePos();
                Box b = entity.getBoundingBox();
                double dx = eye.x - MathHelper.clamp(eye.x, b.minX, b.maxX);
                double dy = eye.y - MathHelper.clamp(eye.y, b.minY, b.maxY);
                double dz = eye.z - MathHelper.clamp(eye.z, b.minZ, b.maxZ);
                last = Math.sqrt(dx * dx + dy * dy + dz * dz);
            }
            return ActionResult.PASS;
        });
    }

    @Override protected void draw(DrawContext ctx, MinecraftClient mc) {
        box(ctx, mc, last < 0 ? "Reach: --" : String.format("Reach: %.2f", last));
    }
}
