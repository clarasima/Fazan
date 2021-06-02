import java.io.BufferedReader;
import java.io.PrintWriter;

public class Play extends Command{
    private BufferedReader in;
    private PrintWriter out;
    private Server server;
    private ClientThread clientThread;
    private String[] commandLine;
    private int numberOfPlayers;
    private Game game;
    private String answer;
    private String request;
    private int gameIndex;
    public Play(Server server, ClientThread clientThread) {
        super();
        this.server=server;
        this.clientThread= clientThread;
    }

    @Override
    public void execute() {
        //clientThread.setAnswer("play");
        this.commandLine = clientThread.getCommandLine();
        answer = clientThread.getAnswer() + " Please wait for other players to join.";
        clientThread.sendAnswer(answer);
        numberOfPlayers = asciiToInt(commandLine[1]);
        clientThread.getClient().setPlaying(true);
        boolean found = false;
        boolean lastPlayer = false;
        for(int i = 0;i < server.getGames().size();i++)
            if(server.getGameAtIndex(i).getNumberOfPlayers()==numberOfPlayers && !server.getGameAtIndex(i).isBeingPlayed()&&!server.getGameAtIndex(i).wasPlayed()){
                found = true;
                this.game = server.getGameAtIndex(i);
                gameIndex = i;
                game.addClientThreads(clientThread);
                if(numberOfPlayers == game.getCurrentPlayers()){ //the game has the wanted number of players
                    game.setBeingPlayed(true);
                    lastPlayer = true;
                }
                break;
            }
        if(!found){
            this.game = new Game(server.getGames().size(),numberOfPlayers, server);
            game.addClientThreads(clientThread);
            server.addGame(game);
            gameIndex = server.getGames().size() - 1;
        }
        System.out.println(game);
        if(lastPlayer) game.play(); //if there are enough players for this game, the game can start
        else{
            System.out.println(clientThread.getClient().getName()+ "before while1");
            while(server.getGameAtIndex(gameIndex).isBeingPlayed()==false){
                System.out.println(server.getGameAtIndex(gameIndex).isBeingPlayed());
                //the players have to wait till there are enough players
            }
            System.out.println(clientThread.getClient().getName()+ "after while1");
            while (server.getGameAtIndex(gameIndex).isBeingPlayed() && clientThread.getClient().isPlaying()); //wait till the player is playing in the game
            System.out.println(clientThread.getClient().getName()+ "after while2");
        }
        String gameStatement;
        if(game.getWinner()==null || game.getWinner().getId()!=clientThread.getClient().getId())
            gameStatement = "Ai pierdut.";
        else
            gameStatement = "Ai castigat!";
        System.out.println(clientThread.getClient().getName()+gameStatement);
        clientThread.setAnswer(gameStatement);
    }
    static int asciiToInt(String str)
    {
        int res = 0;
        for (int i = 0; i < str.length(); ++i)
            res = res * 10 + str.charAt(i) - '0';
        return res;
    }
}
