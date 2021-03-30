package emelt201910.eutazas;

import java.util.List;
import java.util.Map;

public class EUtazas {
    public static void main(String[] args) {
        List<PassengerData> pData = new FileManager().loadFromFile("src/main/resources/emelt201910/eutazas/utasadat.txt");

        EUtazasService service = new EUtazasService(pData);

        System.out.println("--1. feladat--");
        System.out.println("Sorok szama: " + pData.size());

        System.out.println("--2. feladat--");
        System.out.println("Utasok szama: " + service.getNumberOfPassengers());

        System.out.println("--3. feladat--");
        System.out.println("Elutasitott utasok szama: " + service.getNumberOfNotAllowedPassengers());

        System.out.println("--4. feladat--");
        System.out.println("Buszmegallo, ahol a legtobb utas akart felszallni: " + service.getStopWithMaxPassenger());

        System.out.println("--5. feladat--");
        Map<PassengerData.DiscountType, Integer> discounts = service.getDiscountStat();
        System.out.println("Kedvezmennyel felszallok szama: " + discounts.get(PassengerData.DiscountType.DISCOUNT));
        System.out.println("Ingyen felszallok szama: " + discounts.get(PassengerData.DiscountType.FREE));

        service.createAttentionFile();

    }
}
