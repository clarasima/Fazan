public class Register extends Command{
    private Server server;
    private ClientThread clientThread;
    private String[] commandLine;
    public Register(Server server, ClientThread clientThread) {
        super();
        this.server=server;
        this.clientThread= clientThread;
    }

    @Override
    public void execute() {
        this.commandLine = clientThread.getCommandLine();
        if(registerPerson(new Person(commandLine[1], server))==false)
            clientThread.setAnswer(clientThread.getAnswer()+" The username is taken. Try another one.");
        else
            clientThread.setAnswer("registered successfully");

    }
    private Boolean registerPerson(Person person) {
        if(server.getIdByName(person.getName()) != -1)//doesn't exist
            return false;
        server.addInIdByName(person.getName());
        person.setId(server.getPersons().size());
        server.addPerson(person);
        return true;
    }
}
