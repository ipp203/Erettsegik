package emelt201910.eutazas;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class EUtazasService {

    private final List<PassengerData> passengerData;

    public EUtazasService(List<PassengerData> passengerData) {
        this.passengerData = passengerData;
    }

    public int getNumberOfPassengers() {
        return new HashSet<>(passengerData).size();
    }

    public int getNumberOfNotAllowedPassengers() {
        return (int) passengerData.stream()
                .filter(PassengerData::isTicketNotValid)
                .count();
    }

    public int getStopWithMaxPassenger() {
        Map<Integer, Integer> stopMap = passengerData.stream()
                .collect(Collectors.toMap(PassengerData::getStopNumb, p -> 1, Integer::sum));

        Map.Entry<Integer, Integer> result = stopMap.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);

        if (result != null) {
            return result.getKey();
        } else {
            return 0;
        }
    }

    public Map<PassengerData.DiscountType, Integer> getDiscountStat() {
        return passengerData.stream()
                .filter(PassengerData::isTicketValid)
                .collect(Collectors.toMap(p -> p.getTicketType().getDiscount(), p -> 1, Integer::sum));
    }

    public void createAttentionFile() {
        List<String> result = passengerData.stream()
                .filter(PassengerData::isTicketValid)
                .filter(p -> p.getValidityDate() != null)
                .filter(p -> p.getTicketingDate().plusDays(3).isAfter(p.getValidityDate().atStartOfDay()))
                .map(PassengerData::toString)
                .collect(Collectors.toList());
        new FileManager().writeAttentionsToFile(result);
    }

    private int napokszama(int year1, int month1, int day1, int year2, int month2, int day2) {
        month1 = (month1 + 9) % 12;
        year1 = year1 - month1 / 10;
        int d1 = 365 * year1 + year1 / 4 - year1 / 100 + year1 / 400 +
                (month1 * 306 + 5) / 10 + day1 - 1;
        month2 = (month2 + 9) % 12;
        year2 = year2 - month2 / 10;
        int d2 = 365 * year2 + year2 / 4 - year2 / 100 + year2 / 400 +
                (month2 * 306 + 5) / 10 + day2 - 1;
        return d2 - d1;
    }
}
