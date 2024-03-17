import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class Scrabble {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Set<String> result = new HashSet<>();
        HashMap<Integer, Set<String>> wordsToCheck = loadAllWords("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
        collectValid9LetterWords(wordsToCheck, result);

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    public static HashMap<Integer, Set<String>> loadAllWords(String wordsUrl) throws IOException {
        HashMap<Integer, Set<String>> resultMap = new HashMap<>();
        URL url = new URL(wordsUrl);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            List<String> resultList = new ArrayList<>(br.lines().skip(2).toList());
            for (String word : resultList) {
                if (word.length() <= 9 && (word.contains("I") || word.contains("A"))) {
                    switch (word.length()) {
                        case 9:
                            resultMap.computeIfAbsent(9, k -> new HashSet<>());
                            resultMap.get(9).add(word);
                            break;
                        case 8:
                            resultMap.computeIfAbsent(8, k -> new HashSet<>());
                            resultMap.get(8).add(word);
                            break;
                        case 7:
                            resultMap.computeIfAbsent(7, k -> new HashSet<>());
                            resultMap.get(7).add(word);
                            break;
                        case 6:
                            resultMap.computeIfAbsent(6, k -> new HashSet<>());
                            resultMap.get(6).add(word);
                            break;
                        case 5:
                            resultMap.computeIfAbsent(5, k -> new HashSet<>());
                            resultMap.get(5).add(word);
                            break;
                        case 4:
                            resultMap.computeIfAbsent(4, k -> new HashSet<>());
                            resultMap.get(4).add(word);
                            break;
                        case 3:
                            resultMap.computeIfAbsent(3, k -> new HashSet<>());
                            resultMap.get(3).add(word);
                            break;
                        case 2:
                            resultMap.computeIfAbsent(2, k -> new HashSet<>());
                            resultMap.get(2).add(word);
                            break;
                    /*    case 1:
                            resultMap.computeIfAbsent(1, k -> new HashSet<>());
                            resultMap.get(1).add(word);
                            break;*/
                        default:
                            throw new IllegalStateException("Unexpected value: " + word.length());
                    }
                }
            }
            resultMap.computeIfAbsent(1, k -> new HashSet<>());
            resultMap.get(1).add("I");
            resultMap.get(1).add("A");
            return resultMap;
        }

    }

    public static void collectValid9LetterWords(HashMap<Integer, Set<String>> wordsToCheck, Set<String> result) {
        Set<String> nineLetterWords = wordsToCheck.get(9);
        for (String word : nineLetterWords) {
            if (producesValidSequence(word, wordsToCheck))
                result.add(word);
        }
    }

    public static boolean producesValidSequence(String word, HashMap<Integer, Set<String>> wordsToCheck) {
        if (word.length() == 1 && (word.equalsIgnoreCase("a") || word.equalsIgnoreCase("i")))
            return true;

        for (int i = 0; i < word.length(); i++) {
            String wordToCheck = word.substring(0, i).concat(word.substring(i + 1));
            if (!wordToCheck.isEmpty() && wordsToCheck.get(wordToCheck.length()).contains(wordToCheck))
                return producesValidSequence(wordToCheck, wordsToCheck);
        }

        return false;
    }

}
