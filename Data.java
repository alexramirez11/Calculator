import java.util.Arrays;

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

    public Double mean() {
        double avg = 0;
        for (double d : data) {
            avg += d;
        }
        return avg / data.length;
    }

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
