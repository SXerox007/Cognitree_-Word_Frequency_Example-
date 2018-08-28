import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class WordFrequency implements Constants{
    private ArrayList<WordFrequencyModel> maxArrayList,minArrayList;
    private int n;
    private Scanner sc;
    private HashMap<String,Integer> wordList;
    private Pattern p = Pattern.compile("[a-zA-Z]+");
    private int i=0;
    
    private void init(){
        print(INPUT_ENTER_WORD_FREQUENCY);
        sc = new Scanner(System.in);
        n=sc.nextInt();
        maxArrayList = new ArrayList<>(n);
        minArrayList = new ArrayList<>(n);
        wordList = new HashMap<>();
    }
    
    
    private boolean isPathOfFileCorrect(String pathOfFile){
        Path path = Paths.get(pathOfFile);
        return Files.exists(path);
    }
    
    private void validatorAndExtractorFile(String pathOfFile){
        if(isPathOfFileCorrect(pathOfFile)){
            fetchData(pathOfFile);
        }else{
            print(ERROR_PATH_NOT_FOUND);
        }
    }
    
    private void fetchData(String pathOfFile){
        try{
            BufferedReader in = new BufferedReader(new FileReader(pathOfFile));
            String line;
            while((line = in.readLine()) != null)
            {
                //System.out.println(line);
                readerLine(line);
            }
            in.close();
        } catch(Exception e){
            print(e.getMessage());
        }
        
        LinkedHashMap<String, Integer> temp = sortHashMapByValues(wordList);
        //print(temp.toString());
        //print(String.valueOf(temp.size()));
        seprateMostAndLessFrequentWords(temp);
    }
    
    
    private void readerLine(String line){
        Matcher matcher = p.matcher(line);
        while (matcher.find()) {
            keyValuePairSetter(matcher.group());
        }
    }
    
    private void keyValuePairSetter(String key){
        if(wordList.containsKey(key)){
            wordList.put(key,wordList.get(key)+1);
        }else{
            wordList.put(key,1);
        }
    }
    
    private void mostFrequentWord(){
        print(MOST_FREQUENT);
        for(int i=maxArrayList.size()-1;i>=0;i--){
            printFrequentWords(maxArrayList.get(i).getWord(),maxArrayList.get(i).getRepetation());
        }
        leastFrequentWord();
    }
    
    
    private void leastFrequentWord(){
        print("\n");
        print(LEAST_FREQUENT);
        for(int i=0;i<minArrayList.size();i++){
            printFrequentWords(minArrayList.get(i).getWord(),minArrayList.get(i).getRepetation());
        }
    }
    
    
    private void seprateMostAndLessFrequentWords(LinkedHashMap<String, Integer> sortedValues){
        int temp = sortedValues.size()-n;
        sortedValues.forEach((key,value) -> {
            if(temp<=i){
                WordFrequencyModel wordFrequencyModel = new WordFrequencyModel();
                wordFrequencyModel.setWord(key);
                wordFrequencyModel.setRepetation(value);
                maxArrayList.add(wordFrequencyModel);
               // printFrequentWords(key,value);
            }else if(i<n){
                WordFrequencyModel wordFrequencyModel = new WordFrequencyModel();
                wordFrequencyModel.setWord(key);
                wordFrequencyModel.setRepetation(value);
                minArrayList.add(wordFrequencyModel);
               // printFrequentWords(key,value);
            }
            i++;
        });
        
        mostFrequentWord();
    }
    
    private void printFrequentWords(String key,int value){
        System.out.print("("+ key + ":" + value + ")" + ",");
    }
    
    
    private void print(String s){
        System.out.println(s);
    }
    
    
    private LinkedHashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);
        
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        
        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();
            
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;
                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    
    
    public static void main(String args[]){
        WordFrequency wordFrequency = new WordFrequency();
        if (args.length > 0){
            wordFrequency.init();
            wordFrequency.validatorAndExtractorFile(args[0]);
        }else{
            wordFrequency.print(ERROR_NUMBER_OF_ARRGUMENTS);
        }
    }
}
