package core;

public class VolumeConverter {
    public static int toIntern(int externalVolume) {
        return (int) (externalVolume * Statics.VOLUMEMULTIPLICATOR);
    }
    public static int toExtern(int internalVolume) {
        return (int) (internalVolume / Statics.VOLUMEMULTIPLICATOR);
    }
}
