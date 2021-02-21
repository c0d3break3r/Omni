package pugz.omni.common.world.noise;

import java.util.Random;

public class WorldGenRandom extends Random {
    public WorldGenRandom(long var1) {
        super(var1);
    }

    public void a(int var1) {
        for(int var2 = 0; var2 < var1; ++var2) {
            this.next(1);
        }

    }

    protected int next(int var1) {
        return super.next(var1);
    }

    public long c(long var1, int var3, int var4) {
        this.setSeed(var1);
        long var5 = this.nextLong();
        long var7 = this.nextLong();
        long var9 = (long)var3 * var5 ^ (long)var4 * var7 ^ var1;
        this.setSeed(var9);
        return var9;
    }
}
