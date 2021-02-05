import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Cipher {

    public static void main(String[] args) throws IOException {
        String addressFile = "C:\\Users\\User\\Desktop\\14_crypt.txt";
        List<String> text = readFile(addressFile);
        createMapAlphabet();
        Map<Character, Integer> map = frequencyLit(text);
        String[] result = swapLit(text);
        bigramCheck(text);
        trigramCheck(text);
        for (int i = 0; i < result.length; i++) { //valid
            System.out.println(result[i]);
        }


    }

    public static List<String> readFile (String address)  throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(address));
        BufferedReader reader = new BufferedReader(inputStreamReader);

        List<String> text = new ArrayList<>();
        while (reader.ready()) {
            text.add(reader.readLine());
        }

        return text;
    }

    public static Map<Character, Integer> createMapAlphabet() {
        Map<Character, Integer> map = new HashMap<>();

        for (char i = 1072; i < 1104; i++) {
            map.put(i, 0);
        }

        return map;
    }

    public static Map<Character, Integer> frequencyLit(List<String> text) {
        Map<Character, Integer> map = createMapAlphabet();
        int count = 0;

        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < text.get(i).length(); j++) {
                if (text.get(i).charAt(j) != ' ')
                    count++;
            }
        }

        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < text.get(i).length(); j++) {
                for (Map.Entry<Character, Integer> pair : map.entrySet()) {
                    if (text.get(i).charAt(j) == pair.getKey())
                        map.put(pair.getKey(), pair.getValue() + 1);
                }
            }
        }

        map.entrySet().stream().sorted((Map.Entry.<Character, Integer>comparingByValue().reversed())).forEach(System.out::println);

        return map;
    }

    public static Map<String, Integer> bigramCheck(List<String> text) {
        Map<String, Integer> bigrams = new HashMap<>();

        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < text.get(i).length(); j++) {
                if ((j != text.get(i).length() - 1) && text.get(i).charAt(j) != ' ' && text.get(i).charAt(j + 1) != ' ' && text.get(i).charAt(j) != ',' && text.get(i).charAt(j + 1) != ',' && text.get(i).charAt(j) != '-' && text.get(i).charAt(j + 1) != '-' && text.get(i).charAt(j) != ':' && text.get(i).charAt(j + 1) != ':' && text.get(i).charAt(j) != ';' && text.get(i).charAt(j + 1) != ';' && text.get(i).charAt(j) != '?' && text.get(i).charAt(j + 1) != '?' && text.get(i).charAt(j) != '!' && text.get(i).charAt(j + 1) != '!' && text.get(i).charAt(j) != ')' && text.get(i).charAt(j + 1) != ')' && text.get(i).charAt(j) != '(' && text.get(i).charAt(j + 1) != '(') {
                    char[] array = {text.get(i).charAt(j), text.get(i).charAt(j + 1)};
                    bigrams.put(new String(array), 0);
                }
            }
        }

        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < text.get(i).length(); j++) {
                if (j != text.get(i).length() - 1) {
                    char[] array = {text.get(i).charAt(j), text.get(i).charAt(j + 1)};
                    for (Map.Entry<String, Integer> pair : bigrams.entrySet()) {
                        if (pair.getKey().equals(new String(array))) {
                            bigrams.put(pair.getKey(), pair.getValue() + 1);
                        }
                    }
                }
            }
        }

        bigrams.entrySet().stream().sorted((Map.Entry.<String, Integer>comparingByValue().reversed())).forEach(System.out::println);
        return bigrams;
    }

    public static Map<String, Integer> trigramCheck(List<String> text) {
        Map<String, Integer> trigrams = new HashMap<>();

        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < text.get(i).length()  - 2; j++) {
                if ((j != text.get(i).length() - 2) && text.get(i).charAt(j) != ' ' && text.get(i).charAt(j + 1) != ' ' && text.get(i).charAt(j) != ',' && text.get(i).charAt(j + 1) != ',' && text.get(i).charAt(j) != '-' && text.get(i).charAt(j + 1) != '-' && text.get(i).charAt(j) != ':' && text.get(i).charAt(j + 1) != ':' && text.get(i).charAt(j) != ';' && text.get(i).charAt(j + 1) != ';' && text.get(i).charAt(j) != '?' && text.get(i).charAt(j + 1) != '?' && text.get(i).charAt(j) != '!' && text.get(i).charAt(j + 1) != '!' && text.get(i).charAt(j) != ')' && text.get(i).charAt(j + 1) != ')' && text.get(i).charAt(j) != '(' && text.get(i).charAt(j + 1) != '(') {
                    char[] array = {text.get(i).charAt(j), text.get(i).charAt(j + 1), text.get(i).charAt(j + 2)};
                    trigrams.put(new String(array), 0);
                }
            }
        }

        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < text.get(i).length()  - 2; j++) {
                if (j != text.get(i).length() - 2) {
                    char[] array = {text.get(i).charAt(j), text.get(i).charAt(j + 1), text.get(i).charAt(j + 2)};
                    for (Map.Entry<String, Integer> pair : trigrams.entrySet()) {
                        if (pair.getKey().equals(new String(array))) {
                            trigrams.put(pair.getKey(), pair.getValue() + 1);
                        }
                    }
                }
            }
        }
        trigrams.entrySet().stream().sorted((Map.Entry.<String, Integer>comparingByValue().reversed())).forEach(System.out::println);
        return trigrams;
    }

    public static String[] swapLit (List<String> text) {

        String[] array =  text.toArray(new String[0]);

        for (int i = 0; i < array.length; i++) {
            char[] letters = array[i].toCharArray();
            for (int j = 0; j < letters.length; j++) {
                if (letters[j] == 'а')
                    letters[j] = 'в';
                else if (letters[j] == 'б')
                    letters[j] = 'ч';
                else if (letters[j] == 'в')
                    letters[j] = 'о';
                else if (letters[j] == 'г')
                    letters[j] = 'ю';
                else if (letters[j] == 'д')
                    letters[j] = 'г';
                else if (letters[j] == 'е')
                    letters[j] = 'ь';
                else if (letters[j] == 'ж')
                    letters[j] = 'п';
                else if (letters[j] == 'з')
                    letters[j] = 'з';
                else if (letters[j] == 'и')
                    letters[j] = 'а';
                else if (letters[j] == 'й')
                    letters[j] = 'ъ';
                else if (letters[j] == 'к')
                    letters[j] = 'э';
                else if (letters[j] == 'л')
                    letters[j] = 'щ';
                else if (letters[j] == 'м')
                    letters[j] = 'н';
                else if (letters[j] == 'н')
                    letters[j] = 'т';
                else if (letters[j] == 'о')
                    letters[j] = 'х';
                else if (letters[j] == 'п')
                    letters[j] = 'ц';
                else if (letters[j] == 'р')
                    letters[j] = 'ж';
                else if (letters[j] == 'с')
                    letters[j] = 'у';
                else if (letters[j] == 'т')
                    letters[j] = 'л';
                else if (letters[j] == 'у')
                    letters[j] = 'и';
                else if (letters[j] == 'ф')
                    letters[j] = 'б';
                else if (letters[j] == 'х')
                    letters[j] = 'й';
                else if (letters[j] == 'ц')
                    letters[j] = 'м';
                else if (letters[j] == 'ч')
                    letters[j] = 'с';
                else if (letters[j] == 'ш')
                    letters[j] = 'к';
                else if (letters[j] == 'щ')
                    letters[j] = 'я';
                else if (letters[j] == 'ъ')
                    letters[j] = 'е';
                else if (letters[j] == 'ы')
                    letters[j] = 'ы';
                else if (letters[j] == 'ь')
                    letters[j] = 'ф';
                else if (letters[j] == 'э')
                    letters[j] = 'р';
                else if (letters[j] == 'ю')
                    letters[j] = 'д';
                else if (letters[j] == 'я')
                    letters[j] = 'ш';
            }
            array[i] = new String(letters);
        }
        System.out.println();
        return array;
    }


}
