package com.victusclient.module.modules;

import com.victusclient.module.Category;
import com.victusclient.module.Module;
import com.victusclient.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;

/** Forces the particle level while enabled and restores your previous level when switched off. */
public class ParticleSettingsModule extends Module {
    private final Setting.Mode level = add(new Setting.Mode("Particle Level", 2, "All", "Decreased", "Minimal"));
    private ParticlesMode saved;

    public ParticleSettingsModule() { super("Particle Settings", Category.VISUAL); }

    private static ParticlesMode map(int i) {
        return switch (i) {
            case 0 -> ParticlesMode.ALL;
            case 1 -> ParticlesMode.DECREASED;
            default -> ParticlesMode.MINIMAL;
        };
    }

    @Override public void onTick(MinecraftClient mc) {
        if (mc.options == null) return;
        ParticlesMode want = map(level.index);
        if (saved == null) saved = mc.options.getParticles().getValue();
        if (mc.options.getParticles().getValue() != want) mc.options.getParticles().setValue(want);
    }

    @Override public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (saved != null && mc != null && mc.options != null) mc.options.getParticles().setValue(saved);
        saved = null;
    }
}
