package connections;

import user.Streamer;

import java.util.TreeMap;
import java.util.TreeSet;

public class IDAssigner {
    private TreeSet<Integer> availableIDs; //0 to 255
    private TreeMap<String,Integer> streamerIDs; //stores  live (streamer,id) pairs

    public IDAssigner() {
        streamerIDs = new TreeMap<String,Integer>();
        availableIDs = new TreeSet<Integer>();
        for(int i = 0; i < 256; i++) availableIDs.add(i);
    }

    public void freeID(Streamer guy) {
        String username = guy.getUsername();
        int id = streamerIDs.get(username);
        streamerIDs.remove(username);
        availableIDs.add(id);
    }

    public int assignID(Streamer guy) {
        String username = guy.getUsername();
        int id = availableIDs.first();
        availableIDs.remove(id);
        streamerIDs.put(username, id);

        return id;
    }

    public int getID(Streamer guy) {
        return streamerIDs.get(guy.getUsername());
    }

}
