import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
//https://www.geeksforgeeks.org/trie-insert-and-search/
public class Trie {
    static final int ALPHABET_SIZE = 26;

    static TrieNode root;

    public void insert(String key)
    {
        int level;
        int length = key.length();
        int index;

        TrieNode currentRootNode = root;

        for (level = 0; level < length; level++)
        {
            if(key.charAt(level) >= 'A'&&key.charAt(level) <= 'Z')
                index = key.charAt(level) - 'A';
            else
                index = key.charAt(level) - 'a';
            if (currentRootNode.letters[index] == null)
                currentRootNode.letters[index] = new TrieNode();

            currentRootNode = currentRootNode.letters[index];
        }
        currentRootNode.isEndOfWord = true;
    }

    public boolean search(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode currentRootNode = root;

        for (level = 0; level < length; level++)
        {
            if(key.charAt(level) >= 'A' && key.charAt(level) <= 'Z')
                index = key.charAt(level) - 'A';
            else
                index = key.charAt(level) - 'a';

            if (currentRootNode.letters[index] == null)
                return false;

            currentRootNode = currentRootNode.letters[index];
        }

        return (currentRootNode != null && currentRootNode.isEndOfWord);
    }
    public void readWords() {
        root = new TrieNode();
        try {
            File myObj = new File("C:\\Users\\CLARA\\Documents\\Facultate\\JAVA\\server proiect\\cuvinte_reduse.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                insert(data);
                //System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
