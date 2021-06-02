import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Person {
    private String name;
    private int id;
    private Server server;
    private boolean connected;
    private boolean playing;
    private int lostRounds;

    public Person(String name, Server server) {
        this.name = name;
        this.server = server;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getLostRounds() {
        return lostRounds;
    }

    public void setLostRounds(int lostRounds) {
        this.lostRounds = lostRounds;
    }
    public void increaseLostRounds() {
        this.lostRounds = this.lostRounds + 1;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", server=" + server +
                ", connected=" + connected +
                ", playing=" + playing +
                ", lostRounds=" + lostRounds +
                '}';
    }
}
