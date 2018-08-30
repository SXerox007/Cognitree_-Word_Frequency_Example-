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
    private String pathOfFile;
    private boolean flag;
    
    private void init(){
        print(INPUT_ENTER_WORD_FREQUENCY);
        sc = new Scanner(System.in);
        n=sc.nextInt();
        print(INPUT_ENTER_THE_PATH);
        pathOfFile = sc.next();
        maxArrayList = new ArrayList<>(n);
        minArrayList = new ArrayList<>(n);
        wordList = new HashMap<>();
    }
    
    private boolean isPathOfFileCorrect(){
        Path path = Paths.get(pathOfFile);
        return Files.exists(path);
    }
    
    private void validatorAndExtractorFile(){
        if(isPathOfFileCorrect()){
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
        
        printValues(valueAddBySortingOrder(wordList));
    }
    
    
    private void printValues(ArrayList<WordFrequencyModel> sortedArrayList){
        
        print(MOST_FREQUENT);
        for(int i=0;i<n;i++){
            printFrequentWords(sortedArrayList.get(i).getWord(),sortedArrayList.get(i).getRepetation());
        }
        print("\n");
        print(LEAST_FREQUENT);
        for(int i=(sortedArrayList.size()-n);i<sortedArrayList.size();i++){
            printFrequentWords(sortedArrayList.get(i).getWord(),sortedArrayList.get(i).getRepetation());
        }
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
    
    private void printFrequentWords(String key,int value){
        System.out.print("("+ key + ":" + value + ")" + ",");
    }
    
    
    private void print(String s){
        System.out.println(s);
    }
    
    private ArrayList<WordFrequencyModel> valueAddBySortingOrder(HashMap<String,Integer> passedMap){
        ArrayList<WordFrequencyModel> sortedArrayList = new ArrayList<>();
        passedMap.forEach((key,value) -> {
            flag=true;
            WordFrequencyModel wordFrequencyModel = new WordFrequencyModel();
            wordFrequencyModel.setRepetation(value);
            wordFrequencyModel.setWord(key);
            for(int i=0;i<sortedArrayList.size();i++){
                if(sortedArrayList.get(i).getRepetation()<value){
                    //shifting logic
                    sortedArrayList.add(i,wordFrequencyModel);
                    flag=false;
                    break;
                }
            }
            if(flag)
                sortedArrayList.add(wordFrequencyModel);
            
        });
                          
        return sortedArrayList;
    }

    public static void main(String args[]){
            WordFrequency wordFrequency = new WordFrequency();
            wordFrequency.init();
            wordFrequency.validatorAndExtractorFile();
    }
}
