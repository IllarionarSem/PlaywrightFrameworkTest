package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class BookingInfo {

    private final Integer bookingid;
    private final BookingData booking;
}
