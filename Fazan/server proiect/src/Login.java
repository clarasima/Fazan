public class Login extends Command{
    private Server server;
    private ClientThread clientThread;
    private String[] commandLine;
    public Login(Server server, ClientThread clientThread) {
        super();
        this.server=server;
        this.clientThread= clientThread;
    }
    @Override
    public void execute() {
        this.commandLine = clientThread.getCommandLine();
        String name = commandLine[1];
        if(server.getIdByName(name) == -1)
            clientThread.setAnswer(clientThread.getAnswer()+" The username is not recognized");
        else {
            clientThread.setAnswer("successful login");
            clientThread.setClient(server.getPersonAtIndex(server.getIdByName(name)));
            server.addConnectedPerson(server.getPersonAtIndex(server.getIdByName(name)));
            server.getPersonAtIndex(server.getIdByName(name)).setConnected(true);
        }
    }
}
