import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgorithmRSA {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter length of key");

        int L = Integer.parseInt(reader.readLine());
        BigInteger p = primeNumberGenerator(L).add(BigInteger.ONE);
        BigInteger q = primeNumberGenerator(L);
        BigInteger n = p.multiply(q);

        BigInteger fi = fi(p, q);
        BigInteger exp = searchExp(fi);
        List<String> publicKey = new ArrayList<>();
        publicKey.add(exp.toString());
        publicKey.add(n.toString());
        Encoder.writeText(publicKey, "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\public.txt");

        BigInteger d = extendedEuclideanAlgorithm(exp, fi);
        List<String> privateKey = new ArrayList<>();
        privateKey.add(d.toString());
        privateKey.add(n.toString());
        Encoder.writeText(privateKey, "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\private.txt");

        BigInteger encoded = encode();
        decode();


    }

    public static BigInteger fi (BigInteger p, BigInteger q) {

        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    public static BigInteger euclideanAlgorithm (BigInteger a, BigInteger b) {

        if (a.compareTo(b) < 0) {
            BigInteger buf = a;
            a = b;
            b = buf;
        }

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger t = a.remainder(b);
            a = b;
            b = t;
        }

        return a;

    }

    public static BigInteger searchExp (BigInteger fi) {

        BigInteger[] fermatNum = new BigInteger[] {new BigInteger("17"), new BigInteger("257"), new BigInteger("65537")};
        int i = 0;
        BigInteger exp = BigInteger.ZERO;
        BigInteger nod = BigInteger.ZERO;

        while (!nod.equals(BigInteger.ONE)) {
            exp = fermatNum[i];
            //System.out.println(exp);
            nod = euclideanAlgorithm(exp, fi);
            i += 1;
        }

        return exp;
    }

    public static BigInteger primeNumberGenerator (int L) {

        boolean primeCheck = false;
        BigInteger T = BigInteger.probablePrime(L, new Random());

        while (!primeCheck) {
            int k = 0;
            for (int i = 1; i <= 100; i++) {
                BigInteger a = new BigInteger(-i + "");
                a = a.add(T);
                if (fastPow(a, T.subtract(BigInteger.ONE), T).equals(BigInteger.ONE))
                    k++;
            }
            if (k >= 10) {
                primeCheck = true;
                //System.out.println(k);
            }
            else
                T = BigInteger.probablePrime(L, new Random());
        }

        return T;

    }

    public static BigInteger fastPow (BigInteger x, BigInteger d, BigInteger n) {

        BigInteger y = BigInteger.ONE;

        while (d.compareTo(BigInteger.ZERO) > 0) {

            if (d.remainder(BigInteger.TWO).equals(BigInteger.ONE)) {
                y = (y.multiply(x)).remainder(n);
            }

            d = d.divide(BigInteger.TWO);
            x = x.multiply(x).remainder(n);
        }

        return y;
    }

    public static BigInteger extendedEuclideanAlgorithm (BigInteger exp, BigInteger fi) {

        BigInteger a = exp;
        BigInteger b = fi;
        BigInteger u1 = BigInteger.ONE;
        BigInteger v1 = BigInteger.ZERO;
        BigInteger u2 = BigInteger.ZERO;
        BigInteger v2 = BigInteger.ONE;

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger q = a.divide(b);
            BigInteger r = a.remainder(b);
            a = b;
            b = r;
            r = u2;
            u2 = u1.subtract(q.multiply(u2));
            u1 = r;
            r = v2;
            v2 = v1.subtract(q.multiply(v2));
            v1 = r;
        }

        if (u1.compareTo(BigInteger.ZERO) < 0)
            u1 = u1.add(fi);

        return u1;
    }



    public static BigInteger encode() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter string string for encoding:");
        String str = reader.readLine();

        String binaryStr = Integer.toBinaryString(str.charAt(0)) ;
        //System.out.println(binaryStr);
        for (int i = 1; i < str.length(); i++) {
            binaryStr += Integer.toBinaryString(str.charAt(i));
        }
        //System.out.println(binaryStr);


        BigInteger e = new BigInteger(Cipher.readFile("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\public.txt").get(0));
        BigInteger n = new BigInteger(Cipher.readFile("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\public.txt").get(1));

        BigInteger result = fastPow(new BigInteger(binaryStr, 2), e, n);
        List<String> list = new ArrayList<>();
        list.add(result.toString(2));

        Encoder.writeText(list, "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\encrypted.txt");

        return result;

    }

    public static void decode() throws IOException {

        BigInteger d = new BigInteger(Cipher.readFile("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\private.txt").get(0));
        BigInteger n = new BigInteger(Cipher.readFile("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\private.txt").get(1));
        BigInteger encoded = new BigInteger(Cipher.readFile("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\encrypted.txt").get(0), 2);

        BigInteger result = fastPow(encoded, d, n);

        String decodedBinaryStr = result.toString(2);
        //System.out.println(decodedBinaryStr);

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
        //System.out.println(decodedStr);
        List<String> list = new ArrayList<>();
        list.add(decodedStr);

        Encoder.writeText(list, "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\RSAFiles\\decrypted.txt");

    }
}
