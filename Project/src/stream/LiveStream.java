package stream;

import java.awt.image.BufferedImage;

enum Category {
    LEAGUEOFLEGENDS,
    DOTA2,
    ROCKETLEAGUE,
    GTAV,
    MINECRAFT,
    INDIEGAMES,
    IRL
}

public class LiveStream {
    private BufferedImage currentFrame;
    private int viewCount;
    private int startedAtTime;
    private Category category;
    private String title;

}
