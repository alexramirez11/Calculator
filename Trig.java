/**
 * This class is just to hold trig functions that can be called within the CalculatorDriver class.
 * @author Alex Ramirez
 */
public final class Trig {
    private static String pi = "π";

    /**
     * This method calculates the sin of the given value. This method can handle pi.
     * @param value The value to find the sin of
     * @return The sin of the given value as a double
     */
    public static double sin(String value) {
        if (value.equalsIgnoreCase(pi)) {
            return Math.sin(Math.PI);
        }
        return Math.sin(Double.parseDouble(value));
    }

    /**
     * This method calculates the cosine of the given value. This method can handle pi.
     * @param value The value to find the cosine of
     * @return The cosine of the given value as a double
     */
    public static double cos(String value) {
        if (value.equalsIgnoreCase(pi)) {
            return Math.cos(Math.PI);
        }
        return Math.cos(Double.parseDouble(value));
    }

    /**
     * This method calculates the tangent of the given value. This method can handle pi.
     * @param value The value to find the tangent of
     * @return The tangent of the given value as a double
     */
    public static double tan(String value) {
        if (value.equalsIgnoreCase(pi)) {
            return Math.tan(Math.PI);
        }
        return Math.tan(Double.parseDouble(value));
    }

    /**
     * This method calculates the cosecant of the given value. The cosecant of pi is undefined
     * @param value The value to find the cosecant of
     * @return The cosecant of the given value as a double
     */
    public static double csc(String value) {
        return (1 / Math.cos(Double.parseDouble(value)));
    }
}
