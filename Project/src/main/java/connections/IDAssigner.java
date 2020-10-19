package connections;

import user.Streamer;

import java.util.TreeMap;
import java.util.TreeSet;

public class IDAssigner {
    private TreeSet<Integer> availableIDs;
    private TreeMap<String,Integer> streamerIDs; //stores  live (streamer,id) pairs

    public IDAssigner() {
        streamerIDs = new TreeMap<String,Integer>();
        availableIDs = new TreeSet<Integer>();
        for(int i = 0; i <= 252; i+=3) availableIDs.add(i);
    }

    public synchronized void freeID(String username) { //Accepts streamer username and frees his id, called on stop streaming
        int id = streamerIDs.get(username);
        streamerIDs.remove(username);
        availableIDs.add(id);
    }

    public synchronized int assignID(String username) { //Accepts streamer username and gives him id, called on start streaming
        int id = availableIDs.first();
        availableIDs.remove(id);
        streamerIDs.put(username, id);

        return id;
    }

    public synchronized int getID(String username) {
        return streamerIDs.get(username);
    }

}
