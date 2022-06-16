package model;

import com.google.gson.annotations.Expose;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookingData {

    @Expose
    private String firstname;
    @Expose
    private String lastname;
    private Integer totalprice;
    private boolean depositpaid;
    private BookingDate bookingdates;
    private String additionalneeds;
}
