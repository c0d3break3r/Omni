package pugz.omni.common.world.noise;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import net.minecraft.world.gen.ImprovedNoiseGenerator;

import javax.annotation.Nullable;

public class PerlinNoise implements SurfaceNoise {
    private final ImprovedNoiseGenerator[] improvedNoises;
    private final DoubleList doubles;
    private final double c;
    private final double d;

    public static PerlinNoise a(WorldGenRandom random, int var1, DoubleList doubles) {
        return new PerlinNoise(random, Pair.of(var1, doubles));
    }

    private static Pair<Integer, DoubleList> a(IntSortedSet integers) {
        if (integers.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        } else {
            int var1 = -integers.firstInt();
            int var2 = integers.lastInt();
            int var3 = var1 + var2 + 1;
            if (var3 < 1) {
                throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
            } else {
                DoubleList var4 = new DoubleArrayList(new double[var3]);
                IntBidirectionalIterator var5 = integers.iterator();

                while(var5.hasNext()) {
                    int var6 = var5.nextInt();
                    var4.set(var6 + var1, 1.0D);
                }

                return Pair.of(-var1, var4);
            }
        }
    }

    private PerlinNoise(WorldGenRandom random, IntSortedSet var2) {
        this(random, a(var2));
    }

    private PerlinNoise(WorldGenRandom random, Pair<Integer, DoubleList> var2) {
        int var3 = var2.getFirst();
        this.doubles = var2.getSecond();
        ImprovedNoiseGenerator noise = new ImprovedNoiseGenerator(random);
        int var5 = this.doubles.size();
        int var6 = -var3;
        this.improvedNoises = new ImprovedNoiseGenerator[var5];
        if (var6 >= 0 && var6 < var5) {
            double var7 = this.doubles.getDouble(var6);
            if (var7 != 0.0D) {
                this.improvedNoises[var6] = noise;
            }
        }

        for(int var13 = var6 - 1; var13 >= 0; --var13) {
            if (var13 < var5) {
                double var8 = this.doubles.getDouble(var13);
                if (var8 != 0.0D) {
                    this.improvedNoises[var13] = new ImprovedNoiseGenerator(random);
                } else {
                    random.a(262);
                }
            } else {
                random.a(262);
            }
        }

        if (var6 < var5 - 1) {
            long var14 = (long)(noise.func_215456_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D) * 9.223372036854776E18D);
            WorldGenRandom random1 = new WorldGenRandom(var14);

            for(int var10 = var6 + 1; var10 < var5; ++var10) {
                if (var10 >= 0) {
                    double var11 = this.doubles.getDouble(var10);
                    if (var11 != 0.0D) {
                        this.improvedNoises[var10] = new ImprovedNoiseGenerator(random1);
                    } else {
                        random1.a(262);
                    }
                } else {
                    random1.a(262);
                }
            }
        }

        this.d = Math.pow(2.0D, -var6);
        this.c = Math.pow(2.0D, var5 - 1) / (Math.pow(2.0D, var5) - 1.0D);
    }

    public double a(double var1, double var3, double var5) {
        return this.a(var1, var3, var5, 0.0D, 0.0D, false);
    }

    public double a(double var1, double var3, double var5, double var7, double var9, boolean var11) {
        double var12 = 0.0D;
        double var14 = this.d;
        double var16 = this.c;

        for(int var18 = 0; var18 < this.improvedNoises.length; ++var18) {
            ImprovedNoiseGenerator noise = this.improvedNoises[var18];
            if (noise != null) {
                var12 += this.doubles.getDouble(var18) * noise.func_215456_a(a(var1 * var14), var11 ? -noise.yCoord : a(var3 * var14), a(var5 * var14), var7 * var14, var9 * var14) * var16;
            }

            var14 *= 2.0D;
            var16 /= 2.0D;
        }

        return var12;
    }

    @Nullable
    public ImprovedNoiseGenerator a(int var1) {
        return this.improvedNoises[this.improvedNoises.length - 1 - var1];
    }

    public static double a(double var0) {
        return var0 - Math.floor(var0 / 3.3554432E7D + 0.5D) * 3.3554432E7D;
    }

    public double a(double var1, double var3, double var5, double var7) {
        return this.a(var1, var3, 0.0D, var5, var7, false);
    }
}