import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StreamCipher {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /*System.out.println("Enter power of polynomial");
        int power1 = Integer.parseInt(reader.readLine());
        System.out.println("Enter second power");
        int power2 = Integer.parseInt(reader.readLine());
        byte[][] polyMatrix = matrixOfPolynomial(power1, power2);*/

        //setInitialState(12);


    }

    public static byte[][] matrixOfPolynomial (int power1, int power2) {
        byte[][] matrix = new byte[power1][power1];

        for (int i = 0; i < matrix[0].length; i++) {
            if (i == power1 - 1 || i == power2 - 1) {
                matrix[0][i] = 1;
            }
            else
                matrix[0][i] = 0;
        }

        for (int i = 1; i < matrix.length; i++)  {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j + 1)
                    matrix[i][j] = 1;
                else
                    matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < matrix.length; i++)  {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        return matrix;
    }

    public static byte[] setInitialState (int power1)  {
        byte[] array = new byte[power1];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) (Math.random() * 2);
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
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

    /*public static List<Byte> sequenceOutput (byte[][] polyMatrix, byte[] initialState, int numOfTerm) {
        List<Byte> sequenceOutput = new ArrayList<>();
        byte[] state = new byte[initialState.length];
        for (int i = 0; i < polyMatrix.length; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < polyMatrix.length; k++) {

                }
            }
        }
    }*/
}
