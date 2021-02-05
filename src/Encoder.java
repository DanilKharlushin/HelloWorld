import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encoder {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Choose operation: encrypt/decipher (e/d)");
        String button = reader.readLine();

        if (button.equals("e")){

            String inputTextAddress = "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\Files\\InputText.txt";
            String keyAddress = "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\Files\\key.txt";
            String encryptedTextAddress = "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\Files\\EncryptedText.txt";

            List<String> text = Cipher.readFile(inputTextAddress);
            List<Character> key =  keyGenerator();
            writeKey(key, keyAddress);
            writeText(encrypt(text, key), encryptedTextAddress);
        }
        else if (button.equals("d")) {

            String encryptedTextAddress = "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\Files\\EncryptedText.txt";
            String keyAddress = "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\Files\\key.txt";
            String originalTextAddress = "C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\Files\\OriginalText.txt";

            List<String> text = Cipher.readFile(encryptedTextAddress);
            List<Character> key = readKey(keyAddress);
            writeText(decipher(text, key), originalTextAddress);
        }
    }

    public static List<Character> keyGenerator() {
        List<Character> key = new ArrayList<>();
        for (char i = 1072; i < 1104; i++) {
            key.add(i);
        }
        Collections.shuffle(key);
        return key;
    }

    public static List<Character> readKey(String keyAddress) throws IOException {
        List<Character> key = new ArrayList<>();
        List<String> file;

        file = Cipher.readFile(keyAddress);
        for (int i = 0; i < file.size(); i++) {
            key.add(file.get(i).charAt(2));
        }
        return key;
    }

    public static List<String> encrypt(List<String> text, List<Character> key) {
        for (int i = 0; i < text.size(); i++) {
            text.set(i,text.get(i).toLowerCase());
        }

        for (int i = 0; i < text.size(); i++) {
            char[] letters = text.get(i).toCharArray();
            for (int j = 0; j < letters.length; j++) {
                for (char k = 1072; k < 1104; k++) {
                    if (letters[j] == k) {
                        letters[j] = key.get(k - 1072);
                        break;
                    }
                }
            }
            text.set(i, new String(letters));
        }

        return text;
    }

    public static List<String> decipher(List<String> text, List<Character> key) {
        for (int i = 0; i < text.size(); i++) {
            char[] letters = text.get(i).toCharArray();
            for (int j = 0; j < letters.length; j++) {
                for (int k = 0; k < key.size(); k++) {
                    if (letters[j] == key.get(k)) {
                        letters[j] = (char)(k + 1072);
                        break;
                    }
                }
            }
            text.set(i, new String(letters));
        }
        return text;
    }

    public static void writeKey(List<Character> key, String keyAddress)  {
        try {
            FileWriter writer = new FileWriter(keyAddress, false);
            for (int i = 0; i < key.size(); i++) {
                char trueLit = (char) (1072 + i);
                writer.write(trueLit + " " + key.get(i) + "\r\n");

            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void writeText(List<String> text, String textAddress) {
        try {
            FileWriter writer = new FileWriter(textAddress, false);
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i) + "\r\n");
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}

