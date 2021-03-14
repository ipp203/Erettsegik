package emelt202005.metjelentes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TemperatureStat {
    private final MetReport minTemp;
    private final MetReport maxTemp;
    private final int avgTemp;
    private final int fluctTemp;

    public TemperatureStat(List<MetReport> reports) {

        int minTempValue = reports.stream()
                .map(MetReport::getTemperature)
                .min(Integer::compareTo).orElse(0);
        minTemp = reports.stream()
                .filter(mr -> mr.getTemperature() == minTempValue)
                .findFirst().orElse(null);

        int maxTempValue = reports.stream()
                .map(MetReport::getTemperature)
                .max(Integer::compareTo).orElse(0);
        maxTemp = reports.stream()
                .filter(mr -> mr.getTemperature() == maxTempValue)
                .findFirst().orElse(null);

        avgTemp = getAvgs(reports);
        fluctTemp = maxTempValue - minTempValue;
    }

    public MetReport getMinTemp() {
        return minTemp;
    }

    public MetReport getMaxTemp() {
        return maxTemp;
    }

    public int getAvgTemp() {
        return avgTemp;
    }

    public int getFluctTemp() {
        return fluctTemp;
    }

    private int getAvgs(List<MetReport> reports) {
        Set<Integer> hours = new HashSet<>();
        int sum = 0;
        int counter = 0;
        for (MetReport report : reports) {
            int hour = report.getTime().getHour();
            if (hour == 1 || hour == 7 || hour == 13 || hour == 19) {
                sum += report.getTemperature();
                counter++;
                hours.add(report.getTime().getHour());
            }
        }
        if (hours.size() == 4 && counter > 0) {
            return (int) Math.round((double) sum / counter);
        }
        return -8000;
    }

}
