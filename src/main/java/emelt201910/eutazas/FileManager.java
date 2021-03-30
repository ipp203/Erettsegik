package emelt201910.eutazas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");

    public List<PassengerData> loadFromFile(String filepath) {
        try (Stream<String> lineStream = Files.lines(Path.of(filepath))) {
            return lineStream
                    .map(this::lineProcess)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Can not read file ", ioe);
        }
    }

    public void writeAttentionsToFile(List<String> lines) {
        try {
            Files.write(Path.of("figyelemeztetes.txt"), lines, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new IllegalStateException("Can not write file", e);
        }
    }

    private PassengerData lineProcess(String line) {
        String[] data = line.split(" ");
        if (data.length == 5) {
            try {
                int stopNumb = Integer.parseInt(data[0]);
                LocalDateTime ticketingDate = LocalDateTime.parse(data[1], formatter);
                String ticketId = data[2];
                PassengerData.TicketType ticketType = PassengerData.TicketType.valueOf(data[3]);
                LocalDate validityDate = null;
                int ticketNumb = 0;
                if (data[4].length() > 3) {
                    validityDate = LocalDate.parse(data[4], DateTimeFormatter.ofPattern("yyyyMMdd"));
                } else {
                    ticketNumb = Integer.parseInt(data[4]);
                }
                return new PassengerData(stopNumb, ticketingDate, ticketId, ticketType, validityDate, ticketNumb);
            } catch (NumberFormatException | DateTimeParseException exception) {
                return null;
            }
        }
        return null;
    }

}
