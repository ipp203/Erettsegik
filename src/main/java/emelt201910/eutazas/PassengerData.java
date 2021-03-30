package emelt201910.eutazas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class PassengerData {
    public enum DiscountType {
        FULL_PRICE, DISCOUNT, FREE
    }

    public enum TicketType {

        FEB(DiscountType.FULL_PRICE),
        TAB(DiscountType.DISCOUNT),
        NYB(DiscountType.DISCOUNT),
        NYP(DiscountType.FREE),
        RVS(DiscountType.FREE),
        GYK(DiscountType.FREE),
        JGY(DiscountType.FULL_PRICE);

        private final DiscountType discount;

        TicketType(DiscountType discount) {
            this.discount = discount;
        }

        public DiscountType getDiscount() {
            return discount;
        }
    }

    private final int stopNumb;
    private final LocalDateTime ticketingDate;
    private final String ticketId;
    private final TicketType ticketType;
    private final LocalDate validityDate;
    private final int ticketNumb;

    public PassengerData(int stopNumb, LocalDateTime ticketingDate, String ticketId, TicketType ticketType, LocalDate validityDate, int ticketNumb) {
        this.stopNumb = stopNumb;
        this.ticketingDate = ticketingDate;
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.validityDate = validityDate;
        this.ticketNumb = ticketNumb;
    }

    public int getStopNumb() {
        return stopNumb;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public LocalDateTime getTicketingDate() {
        return ticketingDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public boolean isTicketValid() {
        if (validityDate != null) {
            return ticketingDate.isBefore(validityDate.plusDays(1).atStartOfDay());
        } else {
            return ticketNumb > 0;
        }
    }

    public boolean isTicketNotValid() {
        return !isTicketValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerData that = (PassengerData) o;
        return ticketId.equals(that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }

    @Override
    public String toString() {
        return ticketId + ' ' + validityDate.toString();
    }
}
