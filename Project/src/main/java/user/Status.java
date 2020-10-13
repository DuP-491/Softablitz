package user;

public enum Status {
    OFFLINE(0),
    ONLINE(1),
    WATCHING(2),
    STREAMING(3);
    private int id;

    Status(int i) {
        id = i;
    }
}
