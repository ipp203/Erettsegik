package emelt202010.sorozatok;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private LocalDate date;
    private String title;
    private String episode;
    private int length;
    private boolean watched;

    enum FileLineType {
        DATE, TITLE, EPISODE, LENGTH, WATCHED
    }

    public List<FilmSeries> loadFile() {
        InputStream is = FileManager.class.getResourceAsStream("lista.txt");
        List<FilmSeries> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            int counter = 0;
            String line;
            while ((line = br.readLine()) != null) {
                FileLineType type = FileLineType.values()[counter % 5];
                processLine(type, line);
                if (type == FileLineType.WATCHED) {
                    result.add(new FilmSeries(date, title, episode, length, watched));
                }
                counter++;
            }
            return result;
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not load file", ioe);
        }

    }

    private void processLine(FileLineType type, String line) {
        switch (type) {
            case DATE:
                try {
                    date = LocalDate.parse(line.strip(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                } catch (DateTimeParseException dtpe) {
                    date = null;
                }
                break;
            case TITLE:
                title = line;
                break;
            case EPISODE:
                episode = line;
                break;
            case LENGTH:
                length = Integer.parseInt(line.strip());
                break;
            case WATCHED:
                watched = "1".equals(line.strip());
        }

    }

}
