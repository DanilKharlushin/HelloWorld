import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StreamCipher2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter power of polynomial");
        int power1 = Integer.parseInt(reader.readLine());
        System.out.println("Enter second power");
        int power2 = Integer.parseInt(reader.readLine());
        byte[] initialState = setInitialState(power1);
        System.out.println("Enter length of M-sequence to display");
        int lengthOfSequenceToDisplay = Integer.parseInt(reader.readLine());
        System.out.println("Enter length of M-sequence for tests");
        int lengthOfSequence = Integer.parseInt(reader.readLine());

        ArrayList<Byte> sequence = sequenceOutput(initialState, power1, power2, lengthOfSequence);
        System.out.println(lengthOfSequenceToDisplay + " elements of sequence:");
        for (int i = 0; i < lengthOfSequenceToDisplay; i++) {
            System.out.print(sequence.get(i));
        }
        System.out.println();

        System.out.println("Enter length of series for serial test");
        int seriesLength = Integer.parseInt(reader.readLine());
        serialTest(sequence, seriesLength, power1);
        correlationTest(sequence, 1);
        correlationTest(sequence, 2);
        correlationTest(sequence, 8);
        correlationTest(sequence, 9);

    }

    public static byte[] setInitialState (int power1)  {
        byte[] array = new byte[power1];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) (Math.random() * 2);
        }

        String str = Integer.toString(array[0]);
        try {
            FileWriter writer = new FileWriter("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\StreamCipherFiles\\key.txt", false);
            for (int i = 1; i < array.length; i++) {
                str += array[i];
            }
            writer.write(str);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return array;
    }

    public static ArrayList<Byte> sequenceOutput (byte[] initialState, int power1, int power2, int lengthOfSequence){

        ArrayList<Byte> sequenceOutput = new ArrayList<>();

        for (int i = 0; i < initialState.length; i++) {
            sequenceOutput.add(initialState[i]);
        }

        for (int i = 0; i < lengthOfSequence; i++) {
            sequenceOutput.add((byte) (sequenceOutput.get(sequenceOutput.size() - power1) ^ sequenceOutput.get(sequenceOutput.size() - power2)));
        }


        return sequenceOutput;
    }

    public static double serialTest (ArrayList<Byte> sequence, int seriesLength, int power1) {

        Map<String, Integer> map = new HashMap<>();
        for (int i = power1 * 2; i < sequence.size() - seriesLength; i ++) {
            String series = Integer.toString(sequence.get(i));
            for (int j = i + 1; j < seriesLength + i; j++) {
                series += sequence.get(j);
            }

            map.put(series, 0);
        }


        for (int i = power1 * 2; i < sequence.size() - seriesLength; i += seriesLength) {
            String series = Integer.toString(sequence.get(i));
            for (int j = i + 1; j < seriesLength + i; j++) {
                series += sequence.get(j);
            }

            for (Map.Entry<String, Integer> pair : map.entrySet()) {
                if (pair.getKey().equals(series)) {
                    map.put(pair.getKey(), pair.getValue() + 1);
                }
            }
        }

        double  chiSquared = 0;
        double tFrequency = (sequence.size() - 2 * power1) / (seriesLength * Math.pow(2, seriesLength));

        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            chiSquared += Math.pow(pair.getValue() - tFrequency, 2) / tFrequency;
        }
        System.out.println("Chi-square for series of length " + seriesLength + " bit: " + chiSquared);
        return chiSquared;
    }

    public static int sum (ArrayList<Byte> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum;
    }

    public static double correlationTest (ArrayList<Byte> sequence, int k) {
        ArrayList<Byte> x = sequence;
        ArrayList<Byte> y = new ArrayList<>();
        for (int i = k; i < x.size(); i++) {
            y.add(x.get(i));
        }

        double sx = sum(x);
        double sy = sum(y);
        double sumxy = 0;
        double sumxsq = 0;
        double sumysq = 0;

        for (int i = 0; i < y.size(); i++) {
            sumxy += x.get(i) * y.get(i);
        }

        for (int i = 0; i < x.size(); i++) {
            sumxsq += Math.pow(x.get(i), 2);
        }

        for (int i = 0; i < y.size(); i++) {
            sumysq += Math.pow(y.get(i), 2);
        }

        double R = (x.size() * sumxy - sx * sy) / Math.sqrt((x.size() * sumxsq - Math.pow(sx, 2)) * (y.size() * sumysq - Math.pow(sy, 2)));
        boolean check = Math.abs(R) <= 1.0 / (x.size() - 1) + (2.0 / (x.size() - 2)) * Math.sqrt(x.size() * (x.size() - 3.0) / (x.size() + 1));
        System.out.println("correlation coefficient for k = " + k + ": " + R + " " + check);
        return R;
    }
}
