import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PollardAlgorithm {

    public static void main(String[] args) throws IOException {


        for (int i = 2; i < 24; i++) {
            long count = 0;
            for (int j = 0; j < 10; j++) {
                long start = System.currentTimeMillis();
                int L = i;
                BigInteger p = AlgorithmRSA.primeNumberGenerator(L);
                BigInteger q = AlgorithmRSA.primeNumberGenerator(L);
                BigInteger n = p.multiply(q);
                pollard(n, L);
                long finish = System.currentTimeMillis();
                long timeConsumedMillis = finish - start;
                count += timeConsumedMillis;
            }
            System.out.println(count * 1.0 / 10);
        }

    }

    public static void pollard(BigInteger n, int L) {

        List<BigInteger> x = new ArrayList<>();
        x.add(BigInteger.probablePrime(L, new Random()));
        BigInteger nod = BigInteger.ONE;

        while  (nod.compareTo(BigInteger.ONE) <= 0) {
            x.add(0, (((x.get(0).multiply(x.get(0))).add(BigInteger.ONE))).remainder(n));
            for (int i = 0; i < x.size(); i++) {
                for (int j = i + 1; j < x.size(); j *= 2) {
                    nod = AlgorithmRSA.euclideanAlgorithm(n, x.get(i).subtract(x.get(j)).abs());
                    if (nod.compareTo(BigInteger.ONE) > 0)
                        break;
                }
                if (nod.compareTo(BigInteger.ONE) > 0)
                    break;
            }


        }


    }
}
