package pugz.omni.common.world.noise;

import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class SimplexNoise {
    protected static final int[][] a = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {0, -1, 1}, {-1, 1, 0}, {0, -1, -1}};
    private static final double e = Math.sqrt(3.0D);
    private static final double f;
    private static final double g;
    private final int[] h = new int[512];
    public final double b;
    public final double c;
    public final double d;

    public SimplexNoise(Random var1) {
        this.b = var1.nextDouble() * 256.0D;
        this.c = var1.nextDouble() * 256.0D;
        this.d = var1.nextDouble() * 256.0D;

        int var2;
        for(var2 = 0; var2 < 256; this.h[var2] = var2++) {
        }

        for(var2 = 0; var2 < 256; ++var2) {
            int var3 = var1.nextInt(256 - var2);
            int var4 = this.h[var2];
            this.h[var2] = this.h[var3 + var2];
            this.h[var3 + var2] = var4;
        }

    }

    private int a(int var1) {
        return this.h[var1 & 255];
    }

    protected static double a(int[] var0, double var1, double var3, double var5) {
        return (double)var0[0] * var1 + (double)var0[1] * var3 + (double)var0[2] * var5;
    }

    private double a(int var1, double var2, double var4, double var6, double var8) {
        double var12 = var8 - var2 * var2 - var4 * var4 - var6 * var6;
        double var10;
        if (var12 < 0.0D) {
            var10 = 0.0D;
        } else {
            var12 *= var12;
            var10 = var12 * var12 * a(a[var1], var2, var4, var6);
        }

        return var10;
    }

    public double a(double var1, double var3) {
        double var5 = (var1 + var3) * f;
        int var7 = MathHelper.floor(var1 + var5);
        int var8 = MathHelper.floor(var3 + var5);
        double var9 = (double)(var7 + var8) * g;
        double var11 = (double)var7 - var9;
        double var13 = (double)var8 - var9;
        double var15 = var1 - var11;
        double var17 = var3 - var13;
        byte var19;
        byte var20;
        if (var15 > var17) {
            var19 = 1;
            var20 = 0;
        } else {
            var19 = 0;
            var20 = 1;
        }

        double var21 = var15 - (double)var19 + g;
        double var23 = var17 - (double)var20 + g;
        double var25 = var15 - 1.0D + 2.0D * g;
        double var27 = var17 - 1.0D + 2.0D * g;
        int var29 = var7 & 255;
        int var30 = var8 & 255;
        int var31 = this.a(var29 + this.a(var30)) % 12;
        int var32 = this.a(var29 + var19 + this.a(var30 + var20)) % 12;
        int var33 = this.a(var29 + 1 + this.a(var30 + 1)) % 12;
        double var34 = this.a(var31, var15, var17, 0.0D, 0.5D);
        double var36 = this.a(var32, var21, var23, 0.0D, 0.5D);
        double var38 = this.a(var33, var25, var27, 0.0D, 0.5D);
        return 70.0D * (var34 + var36 + var38);
    }

    public double a(double var1, double var3, double var5) {
        double var7 = 0.3333333333333333D;
        double var9 = (var1 + var3 + var5) * 0.3333333333333333D;
        int var11 = MathHelper.floor(var1 + var9);
        int var12 = MathHelper.floor(var3 + var9);
        int var13 = MathHelper.floor(var5 + var9);
        double var14 = 0.16666666666666666D;
        double var16 = (double)(var11 + var12 + var13) * 0.16666666666666666D;
        double var18 = (double)var11 - var16;
        double var20 = (double)var12 - var16;
        double var22 = (double)var13 - var16;
        double var24 = var1 - var18;
        double var26 = var3 - var20;
        double var28 = var5 - var22;
        byte var30;
        byte var31;
        byte var32;
        byte var33;
        byte var34;
        byte var35;
        if (var24 >= var26) {
            if (var26 >= var28) {
                var30 = 1;
                var31 = 0;
                var32 = 0;
                var33 = 1;
                var34 = 1;
                var35 = 0;
            } else if (var24 >= var28) {
                var30 = 1;
                var31 = 0;
                var32 = 0;
                var33 = 1;
                var34 = 0;
                var35 = 1;
            } else {
                var30 = 0;
                var31 = 0;
                var32 = 1;
                var33 = 1;
                var34 = 0;
                var35 = 1;
            }
        } else if (var26 < var28) {
            var30 = 0;
            var31 = 0;
            var32 = 1;
            var33 = 0;
            var34 = 1;
            var35 = 1;
        } else if (var24 < var28) {
            var30 = 0;
            var31 = 1;
            var32 = 0;
            var33 = 0;
            var34 = 1;
            var35 = 1;
        } else {
            var30 = 0;
            var31 = 1;
            var32 = 0;
            var33 = 1;
            var34 = 1;
            var35 = 0;
        }

        double var36 = var24 - (double)var30 + 0.16666666666666666D;
        double var38 = var26 - (double)var31 + 0.16666666666666666D;
        double var40 = var28 - (double)var32 + 0.16666666666666666D;
        double var42 = var24 - (double)var33 + 0.3333333333333333D;
        double var44 = var26 - (double)var34 + 0.3333333333333333D;
        double var46 = var28 - (double)var35 + 0.3333333333333333D;
        double var48 = var24 - 1.0D + 0.5D;
        double var50 = var26 - 1.0D + 0.5D;
        double var52 = var28 - 1.0D + 0.5D;
        int var54 = var11 & 255;
        int var55 = var12 & 255;
        int var56 = var13 & 255;
        int var57 = this.a(var54 + this.a(var55 + this.a(var56))) % 12;
        int var58 = this.a(var54 + var30 + this.a(var55 + var31 + this.a(var56 + var32))) % 12;
        int var59 = this.a(var54 + var33 + this.a(var55 + var34 + this.a(var56 + var35))) % 12;
        int var60 = this.a(var54 + 1 + this.a(var55 + 1 + this.a(var56 + 1))) % 12;
        double var61 = this.a(var57, var24, var26, var28, 0.6D);
        double var63 = this.a(var58, var36, var38, var40, 0.6D);
        double var65 = this.a(var59, var42, var44, var46, 0.6D);
        double var67 = this.a(var60, var48, var50, var52, 0.6D);
        return 32.0D * (var61 + var63 + var65 + var67);
    }

    static {
        f = 0.5D * (e - 1.0D);
        g = (3.0D - e) / 6.0D;
    }
}
