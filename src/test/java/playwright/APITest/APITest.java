package playwright.APITest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import model.BookingData;
import model.BookingDate;
import model.BookingInfo;
import model.Token;
import org.apache.hc.core5.http.HttpStatus;
import org.openqa.selenium.json.TypeToken;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class APITest extends BaseTest {

    private Token token;
    private BookingInfo bookingInfo;

    @Test
    public void getBookingById() {
        APIResponse responseBookingIDList = apiUtil.get(APIEndpoint.BOOKING);
        Assert.assertTrue(responseBookingIDList.ok());
        List<BookingInfo> bookingIDS = JsonUtil.getObjects(responseBookingIDList.text(), new TypeToken<List<BookingInfo>>() {
        }.getType());
        int randomID = bookingIDS.get(new Random().nextInt(bookingIDS.size())).getBookingid();
        APIResponse responseBookingByID = apiUtil.get(APIEndpoint.BOOKING_BY_ID.getFormatted(String.valueOf(randomID)));
        Assert.assertTrue(responseBookingByID.ok());
        BookingData booking = JsonUtil.getObject(responseBookingByID.text(), BookingData.class);
        System.out.println(booking);
    }

    @Test
    public void getBookingIdsTest() {
        APIResponse response = apiUtil.get(APIEndpoint.BOOKING);
        Assert.assertTrue(response.ok());
        List<BookingInfo> bookings = JsonUtil.getObjects(response.text(), new TypeToken<BookingInfo>() {
        }.getType());
        List<Integer> bookingIds = bookings.stream().map(BookingInfo::getBookingid).toList();
        bookingIds.stream().limit(10).forEach(x -> System.out.println("Booking ID: " + x));
    }

    @Test
    public void getTokenTest() {
        Map<String, String> data = new HashMap<>();
        data.put("username", Config.getValue("APIUsername"));
        data.put("password", Config.getValue("APIPassword"));
        APIResponse response = apiUtil.post(APIEndpoint.AUTH, RequestOptions.create().setData(data));
        Assert.assertTrue(response.ok());
        token = JsonUtil.getObject(response.text(), Token.class);
        System.out.println(token);
    }

    @Test
    public void postBooking() {
        String checkInDate = "2018-01-01";
        String checkOutDate = "2019-01-01";
        BookingData booking = new BookingData(
                "Simon",
                "Ilaryionau",
                1500,
                true,
                new BookingDate(checkInDate, checkOutDate),
                "Dinner");
        String json = new Gson().toJson(booking);
        APIResponse response = apiUtil.post(APIEndpoint.BOOKING, json);
        Assert.assertTrue(response.ok());
        bookingInfo = JsonUtil.getObject(response.text(), BookingInfo.class);
        Assert.assertEquals(bookingInfo.getBooking(), booking);
        System.out.println(bookingInfo);
    }

    @Test(dependsOnMethods = {"getTokenTest", "postBooking"})
    public void updateBooking() {
        apiUtil = new APIUtilBuilder(playwright)
                .addHeaders(APIUtil.defaultHeaders)
                .setToken("Cookie", token)
                .setBaseUrl(Config.getValue("baseAPIUrl"))
                .build();
        bookingInfo.getBooking().setFirstname("Jack");
        bookingInfo.getBooking().setLastname("Smith");
        String json = new Gson().toJson(bookingInfo.getBooking());
        APIResponse response = apiUtil.put(APIEndpoint.BOOKING_BY_ID.getFormatted(bookingInfo.getBookingid().toString()), json);
        Assert.assertTrue(response.ok());
        BookingData responseBooking = JsonUtil.getObject(response.text(), BookingData.class);
        Assert.assertEquals(responseBooking, bookingInfo.getBooking());
        System.out.println(responseBooking);
    }

    @Test(dependsOnMethods = {"getTokenTest", "postBooking", "updateBooking"})
    public void partialUpdateBooking() {
        bookingInfo.getBooking().setFirstname("Simon");
        bookingInfo.getBooking().setLastname("Ilaryionau");
        String json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(bookingInfo.getBooking());
        APIResponse response = apiUtil.patch(APIEndpoint.BOOKING_BY_ID.getFormatted(bookingInfo.getBookingid().toString()), json);
        Assert.assertTrue(response.ok());
        BookingData responseBooking = JsonUtil.getObject(response.text(), BookingData.class);
        Assert.assertEquals(responseBooking, bookingInfo.getBooking());
        System.out.println(responseBooking);
    }

    @Test(dependsOnMethods = {"getTokenTest", "postBooking", "updateBooking"})
    public void deleteBooking() {
        APIResponse response = apiUtil.delete(APIEndpoint.BOOKING_BY_ID.getFormatted(bookingInfo.getBookingid().toString()));
        Assert.assertTrue(response.ok());
        Assert.assertEquals(response.text(), "Created");
        response = apiUtil.get(APIEndpoint.BOOKING_BY_ID.getFormatted(bookingInfo.getBooking().toString()));
        Assert.assertEquals(response.status(), HttpStatus.SC_NOT_FOUND);
        Assert.assertEquals(response.text(), "Not Found");
    }
}
