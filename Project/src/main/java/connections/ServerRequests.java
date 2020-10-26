package connections;

public enum ServerRequests {
    ASSIGNID(0),
    RETURNID(1),
    ENDCONNECTION(2),
    GETSTREAM(3),
    ADDSTREAMTODB(4),
    REMOVESTREAMFROMDB(5),
    REGISTERUSER(6),
    LOGIN(7),
    BROWSECATEGORY(8),
    STOPWATCHING(9);

    private int id;

    ServerRequests(int i) {
        id = i;
    }
    public boolean compare(int i) {return id == i;}
    public int geti() { return id; }
}
