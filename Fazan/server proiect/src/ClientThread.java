import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientThread extends Thread {
    private Socket socket = null ;
    private Server server;
    private Person client = null;
    private String[] commandLine;
    private String request, answer;
    private BufferedReader in;
    private PrintWriter out;
    private Exit exit;
    private Register register;
    private Login login;
    private Play play;
    private boolean runningClient = true;
    private long startTime = -1, endTime;

    public ClientThread (Server server, Socket socket) {
        this.server = server; this.socket = socket;
        this.exit = new Exit(server, this);
        this.register = new Register(server, this);
        this.login = new Login(server, this);
        this.play = new Play(server,this);
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //client → server
            this.out = new PrintWriter(socket.getOutputStream()); //server → client
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
            try {
                socket.close();
            } catch (IOException e1) { System.err.println (e1); }
        }
    }

    public void run () {
        while(runningClient){
            request = readRequest();
            answer = "Server received the request: " + request + ".";
            timeout();
            chooseCommand();
            sendAnswer(answer);
        }
        try {
            socket.close();
        } catch (IOException e1) { System.err.println (e1); }
    }

    public String readRequest() {
        try {
            return in.readLine();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) { System.err.println (e1); }
            System.err.println("Communication error... " + e);
        }
        return "";
    }

    public void sendAnswer(String answer) {
        out.println(answer);
        out.flush();
    }

    private void chooseCommand() {
        if(runningClient){
            startTime = System.nanoTime();
            commandLine = request.split(" ");
            String command = commandLine[0];
            if(command.equals("exit"))
                exit.execute();
            else if(command.equals("register"))
                register.execute();
            else if(command.equals("login"))
                login.execute();
            else if(command.equals("play"))
            {
                play.execute();
                startTime = System.nanoTime();
            }
        }
    }

    private void timeout() {
        if(startTime != -1){
            endTime = System.nanoTime();
            if(elapsedSeconds() > 60){
                exit.execute();
                answer = "You were gone for too long.";
            }
        }
    }

    private long elapsedSeconds() {
        return (endTime -  startTime)/1000000000;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setRunningClient(boolean runningClient) {
        this.runningClient = runningClient;
    }

    public String[] getCommandLine() {
        return commandLine;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    public Person getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "ClientThread{" +
                "client=" + client +
                '}';
    }
}