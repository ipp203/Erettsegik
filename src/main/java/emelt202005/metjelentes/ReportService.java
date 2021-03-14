package emelt202005.metjelentes;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private final List<MetReport> reports;

    public ReportService(List<MetReport> reports) {
        this.reports = reports;
    }

    public LocalTime getLastDataTime(String city) {
        Optional<LocalTime> result = reports.stream()
                .filter(mr -> mr.getCity().equals(city))
                .map(MetReport::getTime)
                .max(LocalTime::compareTo);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public List<MetReport> getWindCalm() {
        return reports.stream()
                .filter(r -> r.getWind().equals("00000"))
                .collect(Collectors.toList());
    }

    public Map<String, TemperatureStat> getStatByCities() {
        Map<String, TemperatureStat> result = new HashMap<>();

        Map<String, List<MetReport>> reportMap = getReportMap();

        for (Map.Entry<String, List<MetReport>> entry : reportMap.entrySet()) {
            result.put(entry.getKey(),new TemperatureStat(entry.getValue()));
        }
        return result;
    }

    public Map<String, List<MetReport>> getReportMap() {
        return reports.stream()
                .collect(Collectors.groupingBy(MetReport::getCity));
    }
}
