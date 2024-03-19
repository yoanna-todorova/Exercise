import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class Scrabble {

    public static void main(String[] args) throws IOException {
        Set<String> result = new HashSet<>();
        
        HashMap<Integer, Set<String>> wordsToCheck = loadAllWords("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
        collectValid9LetterWords(wordsToCheck, result);
        
        System.out.println(result.size());
        result.forEach(System.out::println);
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
            if (producesValidSequence(word, wordsToCheck, 0)) {
                result.add(word);
            }
        }
    }

    public static boolean producesValidSequence(String word, HashMap<Integer, Set<String>> wordsToCheck, int position) {
        if (word.length() == 1 && (word.equals("A") || word.equals("I"))) {
            return true;
        }
        if (position == 8) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            String reducedWord = word.substring(0, i) + word.substring(i + 1);
            if (wordsToCheck.get(reducedWord.length()).contains(reducedWord.toUpperCase()) && producesValidSequence(reducedWord, wordsToCheck, position + 1)) {
                return true;
            }
        }
        return false;
    }

}
