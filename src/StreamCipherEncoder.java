import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamCipherEncoder {

        public static void main(String[] args) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String binaryStr = stringToBinaryStr(Cipher.readFile("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\StreamCipherFiles\\originalText.txt").get(0));
            ArrayList<Byte> originalStrList = strToList(binaryStr);

            System.out.println("Enter power of polynomial");
            int power1 = Integer.parseInt(reader.readLine());
            System.out.println("Enter second power");
            int power2 = Integer.parseInt(reader.readLine());

            ArrayList<Byte> key = StreamCipher2.sequenceOutput(StreamCipher2.setInitialState(power1), power1, power2, binaryStr.length());

            String encodedStr = encodedStr(binaryStr, key);
            List<String> encodedList = new ArrayList<>();
            encodedList.add(encodedStr);
            Encoder.writeText(encodedList, "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\StreamCipherFiles\\encoded.txt");
            String decodedStr = decodeStr(encodedStr, key);
            List<String> decodedList = new ArrayList<>();
            decodedList.add(decodedStr);
            Encoder.writeText(decodedList, "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\StreamCipherFiles\\decoded.txt");


            System.out.println("Enter length of series for serial test");
            int seriesLength = Integer.parseInt(reader.readLine());

            System.out.println("Tests results of original file:");
            StreamCipher2.serialTest(originalStrList, seriesLength, 0);
            StreamCipher2.correlationTest(originalStrList, 1);
            StreamCipher2.correlationTest(originalStrList, 2);
            StreamCipher2.correlationTest(originalStrList, 8);
            StreamCipher2.correlationTest(originalStrList, 9);
            System.out.println();

            System.out.println("Tests results of encoded file:");
            StreamCipher2.serialTest(strToList(encodedStr), seriesLength, 0);
            StreamCipher2.correlationTest(strToList(encodedStr), 1);
            StreamCipher2.correlationTest(strToList(encodedStr), 2);
            StreamCipher2.correlationTest(strToList(encodedStr), 8);
            StreamCipher2.correlationTest(strToList(encodedStr), 9);

        }

        public static String stringToBinaryStr (String str) {
            String binaryStr = Integer.toBinaryString(str.charAt(0));

            for (int i = 1; i < str.length(); i++) {
                binaryStr += Integer.toBinaryString(str.charAt(i));
            }

            return binaryStr;
        }

        public static String encodedStr (String binaryStr, ArrayList<Byte> key) {
            String encodedBinaryStr = Integer.toString(Integer.parseInt(String.valueOf(binaryStr.charAt(0))) ^ key.get(0));

            for (int i = 1; i < binaryStr.length(); i++) {
                encodedBinaryStr += Integer.toString(Integer.parseInt(String.valueOf(binaryStr.charAt(i))) ^ key.get(i));
            }


            return encodedBinaryStr;

        }

        public static String decodeStr (String encodedBinaryStr, ArrayList<Byte> key) {
            String decodedBinaryStr = Integer.toString(Integer.parseInt(String.valueOf(encodedBinaryStr.charAt(0))) ^ key.get(0));

            for (int i = 1; i < encodedBinaryStr.length(); i++) {
                decodedBinaryStr += Integer.toString(Integer.parseInt(String.valueOf(encodedBinaryStr.charAt(i))) ^ key.get(i));
            }


            String[] binaryLettersStr = new String[decodedBinaryStr.length() / 7];
            for (int i = 0; i < decodedBinaryStr.length(); i += 7) {
                char[] binaryLetter = new char[7];
                for (int j = i; j < binaryLetter.length + i; j++) {
                    binaryLetter[j - i] = decodedBinaryStr.charAt(j);
                }
                binaryLettersStr[i / 7] = new String(binaryLetter);
            }


            int[] intLetters = new int[binaryLettersStr.length];
            for (int i = 0; i < binaryLettersStr.length; i++) {
                intLetters[i] = Integer.parseInt(binaryLettersStr[i], 2);
            }

            char[] charLetters = new char[intLetters.length];
            for (int i = 0; i < intLetters.length; i++) {
                charLetters[i] = (char) intLetters[i];
            }


            String decodedStr = new String(charLetters);

            return decodedStr;
        }

        public static ArrayList<Byte> strToList (String binaryStr) {
             ArrayList<Byte> binaryList = new ArrayList<>();

             for (int i = 0; i < binaryStr.length(); i++) {
                 binaryList.add(Byte.parseByte(String.valueOf(binaryStr.charAt(i))));
             }

            return binaryList;
        }


}
