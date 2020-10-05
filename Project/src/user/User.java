package user;

enum Status {
    OFFLINE,
    ONLINE,
    WATCHING,
    STREAMING
}

public class User {
    private String username;
    private String password;
    private String name;
    private String bio;
    private String dpLocation;
    private Status status;
}
