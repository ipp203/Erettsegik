package emelt202005.metjelentes;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MetReportMain {

    public static void main(String[] args) {
//1.
        List<MetReport> reports = new FileManager().readFile();
        ReportService rs = new ReportService(reports);
//2.
        System.out.println("2. feladat");
        String city = getCityName();
        LocalTime time = rs.getLastDataTime(city);
        System.out.println("Az utolso adat a megadott telepulesrol " + time.toString() + "-kor erkezett.");
//3.
        System.out.println("3. feladat");
        TemperatureStat minmax = new TemperatureStat(reports);
        printTemperature("A legalacsonyabb homerseklet: ", minmax.getMinTemp());
        printTemperature("A legmagasabb homerseklet: ", minmax.getMaxTemp());
//4.
        System.out.println("4. feladat");
        List<MetReport> calmWindReports = rs.getWindCalm();
        if (calmWindReports.isEmpty()) {
            System.out.println("Nem volt szélcsend a mérések idején.");
        } else {
            for (MetReport report : calmWindReports) {
                System.out.println(report.getCity() + " " + report.getTime().toString());
            }
        }
//5.
        System.out.println("5. feladat");
        Map<String, TemperatureStat> avgfluct = rs.getStatByCities();
        printMap(avgfluct);
//6.
        System.out.println("6. feladat");
        new FileManager().writeWindFiles(rs.getReportMap());
        System.out.println("A fajlok elkeszultek.");
    }

    private static void printMap(Map<String, TemperatureStat> avgfluct) {
        for (Map.Entry<String, TemperatureStat> entry : avgfluct.entrySet()) {
            System.out.println(entry.getKey() + " Középhőmérséklet: " +
                    (entry.getValue().getAvgTemp() > -8000 ? entry.getValue().getAvgTemp() : "NA") + " Hőmérséklet-ingadozás: " +
                    entry.getValue().getFluctTemp());
        }
    }

    private static String getCityName() {
        System.out.println("Adja meg egy telepules kodjat!");
        return new Scanner(System.in).nextLine();
    }

    private static void printTemperature(String text, MetReport report) {
        String sb = text + " " +
                report.getCity() + " " +
                report.getTime().toString() + " " +
                report.getTemperature() + " fok.";
        System.out.println(sb);
    }


}
