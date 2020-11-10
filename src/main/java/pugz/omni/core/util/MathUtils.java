package pugz.omni.core.util;

public class MathUtils {
    public static double smoothStep(double value) {
        return value * value * value * (value * (value * 6.0D - 15.0D) + 10.0D);
    }
}