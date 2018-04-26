import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Cheaters {

    public static void main(String[] args) throws FileNotFoundException {
        // parse args
        // get file path
        File folder = new File(args[0]); // arg for the folder list
        int numberOfWords = Integer.parseInt(args[1]);
        ArrayList<File> files = new ArrayList<>();
        files = parseFolder(folder);
        Map<String, ArrayList<File>> mapping = parseFiles(files, numberOfWords);
        //  public static Map<Connection, AtomicInteger> processMap(Map<String, ArrayList<File>> map)
       // printMapping(mapping);
        Map<Connection, AtomicInteger>  resultsFinal = processMap(mapping);
        printResults(resultsFinal);
        // get n
    }

    public static ArrayList<File> parseFolder(File folderDir){
        ArrayList<File> filesInDir = new ArrayList<>();
        for(File file : folderDir.listFiles()){
            filesInDir.add(file);
        }
        return filesInDir;
    }

    public static Map<String, ArrayList<File>> parseFiles(ArrayList<File> files, int wordNum) throws FileNotFoundException {
        Map<String, ArrayList<File>> mapping = new HashMap<>();
        for(File curFile : files){
            Scanner scanner = new Scanner(curFile);
            while (scanner.hasNextLine()){
                Scanner lineScanner = new Scanner(scanner.nextLine());
                ArrayList<String> sequences = new ArrayList<>();
                while (lineScanner.hasNext()){
                    // read into map already? Seems like it would be long ugh
                    if(sequences.size() < wordNum){
                        sequences.add(lineScanner.next());
                    }else {
                        // process and put into map
                        String str = makeStringSequence(sequences);
                        // handle putting into mapping
                        if(mapping.containsKey(str)){
                            // add to arraylist
                            mapping.get(str).add(curFile); // add the file to the arraylist
                          //  System.out.println("Neat it found something");
                        }else{
                            // add new entry
                            ArrayList<File> newEntry = new ArrayList<>();
                            newEntry.add(curFile);
                            mapping.put(str, newEntry);
                        }// else
                        sequences.remove(0);
                    }//to process and put in map
                } // while line has word
            }// while next line
        }// for loop

        return mapping;
    }

    public static String makeStringSequence(ArrayList<String> words){
        String str = "";
        for(String word : words){
            str += word;
        }
        String modified = str.replaceAll("[^A-Za-z]+", "").toLowerCase();
        return modified;
    }

    public static Map<Connection, AtomicInteger> processMap(Map<String, ArrayList<File>> map){
        // array of connections
        Map<Connection, AtomicInteger> results = new HashMap<>();
        for(Map.Entry<String, ArrayList<File>> entry : map.entrySet()){
            if(entry.getValue().size() > 1){
                // has two or more hits
                for(int i = 0; i<entry.getValue().size(); i++){
                    for(int inner = i+1; inner < entry.getValue().size(); inner++){
                        Connection c = new Connection(entry.getValue().get(i), entry.getValue().get(inner));
                        if(results.containsKey(c)){
                            // increment the integer
                            results.get(c).incrementAndGet();
                        }else {
                            // add it to the map
                            AtomicInteger ai = new AtomicInteger(1);
                            results.put(c, ai);
                        }// else
                    }// inner for
                }// outer for
            }// check if there are two valyes IF
        }// iterates through the entries

        return results;
    }// process map

    public static void printResults(Map<Connection, AtomicInteger> results){
        // gets for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        for(Map.Entry<Connection, AtomicInteger> entry : results.entrySet()){
            System.out.println(entry.getKey().toString()+ ": " + entry.getValue().toString());
        }
    }


    public static void printMapping(Map<String, ArrayList<File>> map){
        for(Map.Entry<String, ArrayList<File>> entry : map.entrySet()){
            if(entry.getValue().size() > 1){
                System.out.print(entry.getKey() + ": ");
                for(File f : entry.getValue()){
                    System.out.print(f.getName() + " ");
                }
                System.out.println("");
            }

        }
    }

}
