package me.melontini.crackerutil.util;

/**
 * Literally the "quic mafs" meme
 * <p>fast sqrt methods produce inaccurate results, unless you run 4+ iterations which kind of defeats the point. They're also not always faster, except fastCeil(), it's always fast :)
 * <p>consider this a <a href="https://chat.openai.com/chat">ChatGPT</a> test</p>
 * <p>Lol, first result on Google <a href="https://stackoverflow.com/questions/11513344/how-to-implement-the-fast-inverse-square-root-in-java">https://stackoverflow.com/questions/11513344/how-to-implement-the-fast-inverse-square-root-in-java</a></p>
 */
public class MathStuff {

    public static int fastCeil(double value) {
        return (int)(value - 1024.0) + 1024;
    }

    public static double fastSqrt(double x, int numIterations) {
        return inverseSqrt(x, numIterations) * x;
    }

    public static float fastSqrt(float x, int numIterations) {
        return inverseSqrt(x, numIterations) * x;
    }
    public static double inverseSqrt(double x, int numIterations) {
        MakeSure.isFalse(x < 0, "Negative numbers cannot have a square root.");

        double xhalf = 0.5 * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);

        for (int j = 0; j < numIterations; j++) {
            x = x * (1.5 - xhalf * x * x);
        }

        return x;
    }


    public static float inverseSqrt(float x, int numIterations) {
        MakeSure.isFalse(x < 0, "Negative numbers cannot have a square root.");

        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);

        for (int j = 0; j < numIterations; j++) {
            x = x * (1.5f - xhalf * x * x);
        }

        return x;
    }
}
