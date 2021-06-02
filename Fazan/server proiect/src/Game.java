import java.util.*;

public class Game {
    private int id;
    private int numberOfPlayers;
    private int currentPlayers;
    private List<ClientThread> clientThreads;
    private Map<String, Boolean> usedWords;
    private boolean beingPlayed;
    private boolean wasPlayed;
    private Person winner;
    private Server server;
    private final String[] fazanVersions={"F","FA","FAZ","FAZA","FAZAN"};
    private String lastWord;


    public Game(int id, int numberOfPlayers, Server server) {
        this.id = id;
        this.beingPlayed = false;
        this.numberOfPlayers = numberOfPlayers;
        this.server = server;
        this.clientThreads = new LinkedList<>();
        this.usedWords = new HashMap<>();
        this.winner = null;
        this.currentPlayers = 0;
        this.wasPlayed = false;
    }

    public void play() {
        int turn = 0;
        boolean fazan = true;
        String answer = "";
        String word = "";
        Boolean ok;
        int numberOfLostRounds = 0;
        int number = 0;
        for(ClientThread clientThread: clientThreads){
            clientThread.sendAnswer("Jocul incepe acum!");
            clientThread.getClient().setLostRounds(0);
        }
        while(beingPlayed){
            if(clientThreads.get(turn).getClient().isPlaying()){ // if player whose turn is now, is still playing
                Person currentPlayer = clientThreads.get(turn).getClient();
                if(fazan == true){
                    Random random = new Random();
                    number = random.nextInt(26);
                    char letter = (char) ('A'+ number);
                    answer = "E randul tau! Incepe cu litera " + letter;
                }
                else{
                    answer = "E randul tau! Incepe cu ultimele doua litere ale cuvantului " + lastWord;
                }
                for(int i = 0; i < numberOfPlayers; i++ )
                    if(i != turn)
                        clientThreads.get(i).sendAnswer("E randul lui " + currentPlayer.getName() +".");
                    else
                        clientThreads.get(i).sendAnswer(answer);
                word = clientThreads.get(turn).readRequest();
                for(int i = 0; i < numberOfPlayers; i++ )
                    if(i != turn)
                        clientThreads.get(i).sendAnswer("Cuvantului lui " + currentPlayer.getName() +" este: "+ word + ".");
                    else
                        clientThreads.get(i).sendAnswer("Cuvantul tau este: " + word + ".");
                ok = true;
                if(!server.getTrie().search(word)){ //if the word exists
                    ok = false;
                    answer = "Gresit! Cuvant incorect. ";
                }
                else{ //if the word exists
                    if(usedWords.get(word)!=null){ // it has already been used
                        answer = "Gresit! Cuvant deja folosit. ";
                        ok = false;
                    }
                    else{
                        if(fazan == true){ // nu se verifica si alte cuvinte
                            if(word.charAt(0)==(char) ('A'+ number) ||word.charAt(0)==(char) ('a'+ number)){
                                answer = "Cuvant corect. ";
                                fazan = false;
                            }
                            else{
                                ok = false;
                                answer = "Gresit! Prima litera gresita. ";
                            }
                        }
                        else{ //se verifica si primele 2 litere
                            if(lastWord.charAt(lastWord.length()-2)==word.charAt(0) && lastWord.charAt(lastWord.length()-1)==word.charAt(1)){
                                usedWords.put(word,true);
                                answer = "Cuvant corect. ";
                            }
                            else{
                                answer = "Gresit! Primele doua litere nu corespund. ";
                                ok = false;
                            }
                        }
                    }
                }
                if(ok == false){
                    fazan = true;
                    currentPlayer.increaseLostRounds();
                    numberOfLostRounds = currentPlayer.getLostRounds();
                    if(numberOfLostRounds == 5){
                        currentPlayer.setPlaying(false);
                        System.out.println("Jucatori: " + currentPlayers);
                        currentPlayers--;
                        if(currentPlayers == 1){
                            for(int i = 0; i < numberOfPlayers; i++)
                                if(clientThreads.get(i).getClient().isPlaying()){
                                    winner = clientThreads.get(i).getClient();
                                    winner.setPlaying(false);
                                    break;
                                }
                            beingPlayed = false;
                            wasPlayed = true;
                            currentPlayers--;
                        }
                    }
                }
                else{ //cuvantul ok
                    lastWord = word;
                }
                if(answer.equals("Cuvant corect. ")){
                    for(int i = 0; i < numberOfPlayers; i++ )
                        clientThreads.get(i).sendAnswer(answer);
                }else{
                    for(int i = 0; i < numberOfPlayers; i++ )
                        if(i != turn)
                            clientThreads.get(i).sendAnswer(answer + currentPlayer.getName() + " are: " + fazanVersions[numberOfLostRounds- 1]+".");
                        else
                            clientThreads.get(i).sendAnswer(answer + "Ai: " + fazanVersions[numberOfLostRounds- 1]+".");
                }
            }
            System.out.println(clientThreads.get(turn).getClient().getName() + "being played" + beingPlayed + "isplaying" +clientThreads.get(turn).getClient().isPlaying());
            turn++;
            if(turn == numberOfPlayers)
                turn = 0;
        }
        System.out.println(clientThreads.get(turn).getClient().getName() +" am iesit");

    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public List<ClientThread> getClientThreads() {
        return clientThreads;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public boolean isBeingPlayed() { return beingPlayed; }

    public void setBeingPlayed(boolean beingPlayed) {
        this.beingPlayed = beingPlayed;
    }

    public Person getWinner() {
        return winner;
    }

    public void addClientThreads(ClientThread clientThread) {
        currentPlayers++;
        this.clientThreads.add(clientThread);
        if(clientThreads.size()==numberOfPlayers){
            this.beingPlayed=true;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", numberOfPlayers=" + numberOfPlayers +
                ", currentPlayers=" + currentPlayers +
                ", clientThreads=" + clientThreads +
                ", usedWords=" + usedWords +
                ", beingPlayed=" + beingPlayed +
                ", winner=" + winner +
                ", server=" + server +
                ", fazanVersions=" + Arrays.toString(fazanVersions) +
                ", lastWord='" + lastWord + '\'' +
                '}';
    }

    public boolean wasPlayed() {
        return this.wasPlayed;
    }
}
