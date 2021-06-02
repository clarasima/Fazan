import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Server {
    // Define the port on which the server is listening
    public static final int PORT = 8100;
    private List<Person> persons;
    private List<Game> games;
    private List<Person> connectedPersons;
    Map<String, Integer> idByName;
    private Trie trie;
    public Server() throws IOException {
        ServerSocket serverSocket = null;
        trie = new Trie();
        trie.readWords();
        persons = new LinkedList<>();
        idByName = new HashMap<>();
        games = new LinkedList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for a player...");
                Socket socket = serverSocket.accept();
                new ClientThread(this, socket).start();
            }
        } catch (IOException e) {
            System.err.println("Ups... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public void addConnectedPerson(Person person) {
        this.persons.add(person);
    }
    public List<Person> getConnectedPersons() {
        return this.persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }
    public List<Person> getPersons() {
        return this.persons;
    }

    public Person getPersonAtIndex(int i) {
        return this.persons.get(i);
    }


    public void addInIdByName(String name) {
        this.idByName.put(name,getPersons().size());
    }

    public int getIdByName(String name){
        if(idByName.get(name)==null)
            return -1;
        return this.idByName.get(name);
    }

    public List<Game> getGames() {
        return games;
    }
    public Game getGameAtIndex(int index) {
        return games.get(index);
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }

    public Trie getTrie() {
        return trie;
    }
}