import java.util.Arrays;

/**
 * This class is to hold data saved by the user and provides statistical operations for the data.
 * @author Alex Ramirez
 */
public class Data {
    private double[] data;
    private String pi = "π";

    public Data(String commaSeparatedNums) {
        String[] raw = commaSeparatedNums.split(",");
        if (raw.length == 0) {
            throw new NullPointerException();
        }
        data = new double[raw.length];
        for (int i = 0; i < raw.length; i++) {
            if (raw[i].equalsIgnoreCase(pi)) {
                data[i] = Math.PI;
            } else {
                data[i] = Double.parseDouble(raw[i]);
            }
        }
    }

    /**
     * This method calculates the mean of the currently saved data.
     * @return The average of the data as a double
     */
    public Double mean() {
        double avg = 0;
        for (double d : data) {
            avg += d;
        }
        return avg / data.length;
    }

    /**
     * This method finds the median of the currently saved data.
     * @return The median of the data as a double
     */
    public Double median() {
        double[] cl = data.clone();
        Arrays.sort(cl);
        int mid = cl.length / 2;
        double med = cl[mid];
        if (cl.length % 2 == 0) {
            med = cl[mid - 1] + cl[mid];
            med = med / 2;
        }
        return med;
    }

    /**
     * This method calculates the standard deviation of the currently saved data.
     * @return The standard deviation of the data as a double
     */
    public Double sd() {
        double mu = 0;
        for (double n : data) {
            mu += n;
        }
        mu = mu / data.length;
        double variance = 0;
        for (double n : data) {
            variance += Math.pow(n - mu, 2);
        }
        variance = variance / data.length;
        return Math.sqrt(variance);
    }

    /**
     * This method clears the current data by being null. This will prevent operations attempted on an empty data set.
     */
    public void clean() {
        data = null;
    }

    public String toString() {
        String str = "[";

        for (int i = 0; i < data.length; i++) {
            if (Double.compare(data[i], Math.PI) == 0) {
                str += pi;
            } else {
                str += data[i];
            }

            if (i == data.length - 1) {
                str += "]";
            } else {
                str += ",";
            }
        }

        return str;
    }

}
