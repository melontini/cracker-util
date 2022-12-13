package me.melontini.crackerutil.util;

/**
 * This class provides utility methods for fast approximate calculations.
 *
 * <p>Fast square root methods produce inaccurate results, unless you run 4+ iterations
 * which can slow down the calculation. However, the {@code fastCeil()} method is always
 * fast and accurate.
 *
 * <p>consider this a <a href="https://chat.openai.com/chat">ChatGPT</a> test</p>
 *
 * @author Lol, first result on Google <a href="https://stackoverflow.com/questions/11513344/how-to-implement-the-fast-inverse-square-root-in-java">https://stackoverflow.com/questions/11513344/how-to-implement-the-fast-inverse-square-root-in-java</a>
 * @see Math
 */
public class MathStuff {
    public static long round(double value) {
        long x = (long) value; //this works since casting to long just truncates doubles
        if (value - x >= 0.5) {
            return x + 1;
        }
        if (value - x < -0.5) {
            return x - 1;
        }
        return x;
    }

    public static int round(float value) {
        int x = (int) value; //this works since casting to long just truncates doubles
        if (value - x >= 0.5) {
            return x + 1;
        }
        if (value - x < -0.5) {
            return x - 1;
        }
        return x;
    }

    public static int fastCeil(double value) {
        return (int) (value - 1024.0) + 1024;
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
