package emelt202010.sorozatok;

import java.time.LocalDate;

public class FilmSeries {
    private final LocalDate date;
    private final String title;
    private final String episode;
    private final int length;
    private final boolean watched;

    public FilmSeries(LocalDate date, String title, String episode, int length, boolean watched) {
        this.date = date;
        this.title = title;
        this.episode = episode;
        this.length = length;
        this.watched = watched;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isDateNotNull(){
        return date!=null;
    }

    public String getTitle() {
        return title;
    }

    public String getEpisode() {
        return episode;
    }

    public int getLength() {
        return length;
    }

    public boolean isWatched() {
        return watched;
    }

    public boolean isNotWatched() {
        return !watched;
    }
}
