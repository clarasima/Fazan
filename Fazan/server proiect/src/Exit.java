public class Exit extends Command{
    private Server server;
    private ClientThread clientThread;
    public Exit(Server server, ClientThread clientThread) {
        super();
        this.server=server;
        this.clientThread=clientThread;
    }

    @Override
    public void execute() {
        clientThread.setRunningClient(false);
        clientThread.setAnswer("You exited the server.");
    }
}
