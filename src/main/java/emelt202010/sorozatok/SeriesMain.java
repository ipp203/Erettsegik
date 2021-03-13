package emelt202010.sorozatok;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SeriesMain {
    public static void main(String[] args) {
        FileManager fm = new FileManager();
        List<FilmSeries> series = fm.loadFile();
        SeriesService ss = new SeriesService(series);

        System.out.println("Ismert datumu epizodok szama: " + ss.getNumberOfNotNullDate());

        System.out.printf("Megnezett epizodok aranya: %.2f%%%n", 100 * ss.getWatchedSeriesRate());

        Optional<Duration> duration = ss.getDurationOfWatching();
        System.out.println("Osszes ido: " + convertDurationToString(duration));

        LocalDate date = getDate();
        System.out.println(ss.getNotWatchedEpisodes(date));

        String day = getDay();
        System.out.println("A nap filmjei: \n" + ss.getTitlesOfDay(day));

        ss.writeMapToFile(ss.getSeriesLength(), Path.of("summa.txt"));

        //System.out.println(ss.getSeriesLength());

    }

    private static LocalDate getDate() {
        Scanner scanner = new Scanner(System.in);
        LocalDate result = null;
        boolean success = false;
        do {
            System.out.println("Kerek egy datumot yyyy.mm.dd formatumban!");
            String line = scanner.nextLine();
            try {
                result = LocalDate.parse(line, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                success = true;
            } catch (DateTimeParseException dtpe) {

            }
        } while (!success);
        return result;
    }

    private static String convertDurationToString(Optional<Duration> duration) {
        if (duration.isPresent()) {
            long days = duration.get().toDays();
            long hours = duration.get().toHoursPart();
            long minutes = duration.get().toMinutesPart();
            return days + " nap " + hours + " ora " + minutes + " perc";
        } else {
            return "";
        }
    }



    private static String getDay(){
        System.out.println("Adja meg a het egy napjat: v, h, k, sze, cs, p, szo");
        return new Scanner(System.in).nextLine();
    }

}
