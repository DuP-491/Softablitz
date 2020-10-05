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

    public User(String username, String password, String name, String bio, String dpLocation, Status status) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.dpLocation = dpLocation;
        this.status = status;
    }

}
