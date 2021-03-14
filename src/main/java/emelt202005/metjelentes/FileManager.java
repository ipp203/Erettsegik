package emelt202005.metjelentes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileManager {

    public List<MetReport> readFile() {
        List<MetReport> reports = new ArrayList<>();
        InputStream is = FileManager.class.getResourceAsStream("tavirathu13.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                Optional<MetReport> report = processLine(line);
                report.ifPresent(reports::add);
            }
            return reports;
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file ", ioe);
        }
    }

    private Optional<MetReport> processLine(String line) {
        Optional<MetReport> report = Optional.empty();
        String[] parts = line.split(" ");
        if (parts.length == 4) {
            try {
                String city = parts[0];

                int hour = Integer.parseInt(parts[1].substring(0, 2));
                int minute = Integer.parseInt(parts[1].substring(2));
                LocalTime time = LocalTime.of(hour, minute);

                String wind = parts[2];
                int temp = Integer.parseInt(parts[3]);
                report = Optional.of(new MetReport(city, time, wind, temp));
            } catch (NumberFormatException nfe) {
                System.out.println("Hibas sor: " + line);
            }
        }
        return report;
    }

    public void writeWindFiles(Map<String, List<MetReport>> reportMap) {
        for (Map.Entry<String, List<MetReport>> entry : reportMap.entrySet()) {
            writeWindFile(entry.getKey(), entry.getValue());
        }
    }

    private void writeWindFile(String city, List<MetReport> reports) {
        Path dir = Path.of("emelt202005WindReports");
        createDirectory(dir);
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(dir.resolve(city + ".txt")))) {
            pw.println(city + " ");
            for (MetReport r : reports) {
                pw.print(r.getTime().toString() + " ");
                pw.println("#".repeat(r.getWindForce()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not write file " + city, ioe);
        }
    }

    private void createDirectory(Path dir) {
        try {
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
