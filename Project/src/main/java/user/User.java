package user;

import connections.db.UserToDB;



public class User {
    protected String username;
    protected String name;
    protected String bio;
    protected String dpLocation;
    protected Status status;
    protected UserToDB uToDB;

    public User(String username, String name, String bio, String dpLocation, Status status) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.dpLocation = dpLocation;
        this.status = status;
        this.uToDB = new UserToDB();
    }

    public String getUsername() {
        return this.username;
    }
}
