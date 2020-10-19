package stream;

public enum Category {
    LEAGUEOFLEGENDS(0),
    DOTA2(1),
    ROCKETLEAGUE(2),
    GTAV(3),
    MINECRAFT(4),
    INDIEGAMES(5),
    IRL(6);
    private int id;

    Category(int i) {
        id = i;
    }
    public boolean compare(int i) {return id == i;}
    public int geti() { return id; }
}
