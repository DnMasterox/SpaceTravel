package nshumakov.com.spacetravel.Database;

/**
 * Created by nshumakov on 02.07.2017.
 */

public class Stats {
    int id;
    String name;
    String score;

    public Stats(String name, String score, String clickScore) {
        this.name = name;
        this.score = score;
        this.clickScore = clickScore;
    }

    public Stats() {

    }

    String clickScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getClickScore() {
        return clickScore;
    }

    public void setClickScore(String clickScore) {
        this.clickScore = clickScore;
    }

    public Stats(int id, String name, String score, String clickScore) {

        this.id = id;
        this.name = name;
        this.score = score;
        this.clickScore = clickScore;
    }
}
