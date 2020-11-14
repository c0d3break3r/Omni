package pugz.omni.common.world.noise;

import net.minecraft.util.math.MathHelper;
import pugz.omni.core.util.MathUtils;

import java.util.Random;

public class ImprovedNoise {
    private final byte[] d;
    public final double a;
    public final double b;
    public final double c;

    public ImprovedNoise(Random random) {
        this.a = random.nextDouble() * 256.0D;
        this.b = random.nextDouble() * 256.0D;
        this.c = random.nextDouble() * 256.0D;
        this.d = new byte[256];

        int var2;
        for(var2 = 0; var2 < 256; ++var2) {
            this.d[var2] = (byte)var2;
        }

        for(var2 = 0; var2 < 256; ++var2) {
            int var3 = random.nextInt(256 - var2);
            byte var4 = this.d[var2];
            this.d[var2] = this.d[var2 + var3];
            this.d[var2 + var3] = var4;
        }

    }

    public double a(double var1, double var3, double var5, double var7, double var9) {
        double var11 = var1 + this.a;
        double var13 = var3 + this.b;
        double var15 = var5 + this.c;
        int var17 = MathHelper.floor(var11);
        int var18 = MathHelper.floor(var13);
        int var19 = MathHelper.floor(var15);
        double var20 = var11 - (double)var17;
        double var22 = var13 - (double)var18;
        double var24 = var15 - (double)var19;
        double var26 = MathUtils.smoothStep(var20);
        double var28 = MathUtils.smoothStep(var22);
        double var30 = MathUtils.smoothStep(var24);
        double var32;
        if (var7 != 0.0D) {
            double var34 = Math.min(var9, var22);
            var32 = (double)MathHelper.floor(var34 / var7) * var7;
        } else {
            var32 = 0.0D;
        }

        return this.a(var17, var18, var19, var20, var22 - var32, var24, var26, var28, var30);
    }

    private static double grad(int var0, double var1, double var3, double var5) {
        int var7 = var0 & 15;
        return SimplexNoise.a(SimplexNoise.a[var7], var1, var3, var5);
    }

    private int fade(int var1) {
        return this.d[var1 & 255] & 255;
    }

    public double a(int var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14) {
        int var16 = this.fade(var1) + var2;
        int var17 = this.fade(var16) + var3;
        int var18 = this.fade(var16 + 1) + var3;
        int var19 = this.fade(var1 + 1) + var2;
        int var20 = this.fade(var19) + var3;
        int var21 = this.fade(var19 + 1) + var3;
        double var22 = grad(this.fade(var17), var4, var6, var8);
        double var24 = grad(this.fade(var20), var4 - 1.0D, var6, var8);
        double var26 = grad(this.fade(var18), var4, var6 - 1.0D, var8);
        double var28 = grad(this.fade(var21), var4 - 1.0D, var6 - 1.0D, var8);
        double var30 = grad(this.fade(var17 + 1), var4, var6, var8 - 1.0D);
        double var32 = grad(this.fade(var20 + 1), var4 - 1.0D, var6, var8 - 1.0D);
        double var34 = grad(this.fade(var18 + 1), var4, var6 - 1.0D, var8 - 1.0D);
        double var36 = grad(this.fade(var21 + 1), var4 - 1.0D, var6 - 1.0D, var8 - 1.0D);
        return MathHelper.lerp3(var10, var12, var14, var22, var24, var26, var28, var30, var32, var34, var36);
    }
}