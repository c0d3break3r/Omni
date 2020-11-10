package pugz.omni.common.world.noise;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;

public class NormalNoise {
    private final double a;
    private final PerlinNoise perlinNoise1;
    private final PerlinNoise perlinNoise2;

    public static NormalNoise a(WorldGenRandom random, int var1, double... doubles) {
        return new NormalNoise(random, var1, new DoubleArrayList(doubles));
    }

    public static NormalNoise a(WorldGenRandom random, int var1, DoubleList doubles) {
        return new NormalNoise(random, var1, doubles);
    }

    private NormalNoise(WorldGenRandom random, int var2, DoubleList doubles) {
        this.perlinNoise1 = PerlinNoise.a(random, var2, doubles);
        this.perlinNoise2 = PerlinNoise.a(random, var2, doubles);
        int var4 = 2147483647;
        int var5 = -2147483648;
        DoubleListIterator iterator = doubles.iterator();

        while(iterator.hasNext()) {
            int var7 = iterator.nextIndex();
            double var8 = iterator.nextDouble();
            if (var8 != 0.0D) {
                var4 = Math.min(var4, var7);
                var5 = Math.max(var5, var7);
            }
        }

        this.a = 0.16666666666666666D / a(var5 - var4);
    }

    private static double a(int var0) {
        return 0.1D * (1.0D + 1.0D / (double)(var0 + 1));
    }

    public double a(double var1, double var3, double var5) {
        double var7 = var1 * 1.0181268882175227D;
        double var9 = var3 * 1.0181268882175227D;
        double var11 = var5 * 1.0181268882175227D;
        return (this.perlinNoise1.a(var1, var3, var5) + this.perlinNoise2.a(var7, var9, var11)) * this.a;
    }
}