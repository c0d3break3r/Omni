package pugz.omni.core.util;

public interface IOmniChest {
    String getWood();

    default boolean isTrapped() {
        return false;
    }
}