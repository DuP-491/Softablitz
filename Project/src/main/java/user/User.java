package user;

import connections.db.UserToDB;

enum Status {
    OFFLINE,
    ONLINE,
    WATCHING,
    STREAMING
}

public class User {
    protected String username;
    protected String password;
    protected String name;
    protected String bio;
    protected String dpLocation;
    protected Status status;
    protected UserToDB uToDB;

    public User(String username, String password, String name, String bio, String dpLocation, Status status) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.dpLocation = dpLocation;
        this.status = status;
        this.uToDB = new UserToDB();
    }

}
