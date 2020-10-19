package connections;

public enum ServerRequests {
    GETID(0),
    RETURNID(1),
    ENDCONNECTION(2);
    private int id;

    ServerRequests(int i) {
        id = i;
    }
    public boolean compare(int i) {return id == i;}
}
