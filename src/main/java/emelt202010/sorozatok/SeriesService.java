package emelt202010.sorozatok;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SeriesService {
    private List<FilmSeries> seriesList;

    public SeriesService(List<FilmSeries> seriesList) {
        this.seriesList = new ArrayList<>(seriesList);
    }

    public int getNumberOfNotNullDate() {
        return (int) seriesList.stream()
                .filter(s -> s.getDate() != null)
                .count();
    }

    public double getWatchedSeriesRate() {
        long counter = seriesList.stream()
                .filter(FilmSeries::isWatched)
                .count();
        return (double) counter / seriesList.size();
    }

    public Optional<Duration> getDurationOfWatching() {

        return seriesList.stream()
                .filter(FilmSeries::isWatched)
                .map(s -> Duration.ofMinutes(s.getLength()))
                .reduce(Duration::plus);

    }

    public String getNotWatchedEpisodes(LocalDate date) {
        return seriesList.stream()
                .filter(FilmSeries::isDateNotNull)
                .filter(s -> s.getDate().isBefore(date.plusDays(1)))
                .filter(FilmSeries::isNotWatched)
                .map(s -> s.getEpisode() + "\t" + s.getTitle() + "\n")
                .reduce("", String::concat);
    }

    public String getTitlesOfDay(String day) {
        return seriesList.stream()
                .filter(FilmSeries::isDateNotNull)
                .filter(f -> getDayOfWeek(f.getDate()).equals(day))
                .map(f -> f.getTitle() + "\n")
                .distinct()
                .reduce("", String::concat);
    }

    public Map<String, String> getSeriesLength() {
        Map<String, Integer> length = seriesList.stream()
                .collect(Collectors.toMap(FilmSeries::getTitle, FilmSeries::getLength, Integer::sum));

        Map<String, Integer> number = seriesList.stream()
                .collect(Collectors.toMap(FilmSeries::getTitle, f -> 1, Integer::sum));

        return length.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue() + " " + number.get(e.getKey())));
    }


    //    Függvény hetnapja(ev, ho, nap : Egész) : Szöveg
//    napok: Tömb(0..6: Szöveg)= (″v″, ″h″, ″k″, ″sze″,
//            ″cs″, ″p″, ″szo″)
//    honapok: Tömb(0..11: Egész)= (0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
//    Ha ho < 3 akkor ev := ev -1
//    hetnapja := napok[(ev + ev div 4 – ev div 100 +
//    ev div 400 + honapok[ho-1] + nap) mod 7]
//    Függvény vége

    private String getDayOfWeek(LocalDate date) {
        return getDayOfWeek(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    private String getDayOfWeek(int year, int month, int day) {
        String[] days = {"v", "h", "k", "sze", "cs", "p", "szo"};
        int[] months = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if (month < 3) {
            year--;
        }
        return days[(year + year / 4 - year / 100 + year / 400 + months[month - 1] + day) % 7];
    }

    public void writeMapToFile(Map<String, String> seriesLength, Path path) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(path))) {
            for (Map.Entry<String, String> entry : seriesLength.entrySet()) {
                pw.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            throw new IllegalStateException("can not write file ", e);
        }
    }
}
