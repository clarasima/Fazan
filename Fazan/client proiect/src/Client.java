import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private String request;
    private String answer;
    public void connect() throws IOException {
        Boolean connected = true;
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()))
            ) {
            while(connected){
                try {
                    request = "";
                    Scanner scanner = new Scanner(System.in);
                    request = scanner.nextLine();
                }
                catch(Exception e){
                    System.out.println(e);
                }
                if(request.equals("exit"))
                    connected = false;
                out.println(request); //client → server
                String[] requestLine = request.split(" ");
                if(requestLine[0].equals("play")){
                    answer = in.readLine(); //server → client //wait
                    System.out.println(answer);
                    answer = in.readLine(); //server → client //game starts
                    System.out.println(answer);
                    while(true){
                        answer = in.readLine(); //server → client //whose turn it is or gameStatement
                        System.out.println(answer);
                        if(answer.equals("Ai pierdut.") ||answer.equals("Ai castigat!")){
                            //System.out.println("break");
                            break;
                        }
                        if(answer.contains("E randul tau!")){
                            //citeste cuvant si trimite
                            try {
                                request = "";
                                Scanner scanner = new Scanner(System.in);
                                request = scanner.nextLine();
                                out.println(request);
                            }
                            catch(Exception e){
                                System.out.println(e);
                            }
                        }
                        answer = in.readLine(); //server → client //chosen word
                        System.out.println(answer);
                        answer = in.readLine(); //server → client //corect sau gresit
                        System.out.println(answer);
                    }

                }
                if(!requestLine[0].equals("play")){
                    answer = in.readLine(); //server → client
                    System.out.println(answer);
                    if(answer.equals("You were gone for too long.")){
                        connected = false;
                        System.exit(0);
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}