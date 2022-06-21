package playwright.apitest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import model.BookingData;
import model.BookingDate;
import model.BookingInfo;
import model.Token;
import org.apache.hc.core5.http.HttpStatus;
import org.openqa.selenium.json.TypeToken;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.*;

import java.util.*;

public class APITest {

    private Playwright playwright;
    private APIRequestContext context;
    private BookingInfo bookingInfo;

    private final static String EP_BOOKING = "/booking";
    private final static String EP_BOOKING_BY_ID = "/booking/%s";
    private static final String EP_AUTH = "/auth";
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    private final Map<String, String> defaultHeaders = Map.of("Content-Type", "application/json", "Accept", "application/json");

    @Test
    public void getBookingByID() {
        APIResponse responseBookingIDList = context.get(EP_BOOKING);
        Assert.assertTrue(responseBookingIDList.ok());
        List<BookingInfo> bookingIDS = JsonUtil.getObjects(responseBookingIDList.text(), new TypeToken<List<BookingInfo>>() {
        }.getType());
        int randomID = bookingIDS.get(new Random().nextInt(bookingIDS.size())).getBookingid();
        APIResponse responseBookingByID = context.get(String.format(EP_BOOKING_BY_ID, randomID));
        Assert.assertTrue(responseBookingByID.ok());
        BookingData booking = JsonUtil.getObject(responseBookingByID.text(), BookingData.class);
        System.out.println(booking);
    }

    @Test
    public void getBookingIdsTest() {
        APIResponse response = context.get(EP_BOOKING);
        Assert.assertTrue(response.ok());
        List<BookingInfo> bookings = JsonUtil.getObjects(response.text(), new TypeToken<BookingInfo>() {
        }.getType());
        List<Integer> bookingIds = bookings.stream().map(BookingInfo::getBookingid).toList();
        bookingIds.stream().limit(10).forEach(x -> System.out.println("Booking ID: " + x));
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
        APIResponse response = context.post(EP_BOOKING, RequestOptions.create().setData(json));
        Assert.assertTrue(response.ok());
        bookingInfo = JsonUtil.getObject(response.text(), BookingInfo.class);
        Assert.assertEquals(bookingInfo.getBooking(), booking);
        System.out.println(bookingInfo);
    }

    @Test(dependsOnMethods = {"getTokenTest", "postBooking"})
    public void updateBooking() {
        defaultHeaders.put("Cookie", String.format("token=%s", getAuthToken().getToken()));
        context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URL)
                .setExtraHTTPHeaders(defaultHeaders));
        bookingInfo.getBooking().setFirstname("Jack");
        bookingInfo.getBooking().setLastname("Smith");
        String json = new Gson().toJson(bookingInfo.getBooking());
        APIResponse response = context.put(String.format(EP_BOOKING_BY_ID, bookingInfo.getBookingid()), RequestOptions.create().setData(json));
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
        APIResponse response = context.patch(String.format(EP_BOOKING_BY_ID, bookingInfo.getBookingid()), RequestOptions.create().setData(json));
        Assert.assertTrue(response.ok());
        BookingData responseBooking = JsonUtil.getObject(response.text(), BookingData.class);
        Assert.assertEquals(responseBooking, bookingInfo.getBooking());
        System.out.println(responseBooking);
    }

    @Test(dependsOnMethods = {"getTokenTest", "postBooking", "updateBooking"})
    public void deleteBooking() {
        APIResponse response = context.delete(String.format(EP_BOOKING_BY_ID, bookingInfo.getBookingid()));
        Assert.assertTrue(response.ok());
        Assert.assertEquals(response.text(), "Created");
        response = context.get(String.format(EP_BOOKING_BY_ID, bookingInfo.getBookingid()));
        Assert.assertEquals(response.status(), HttpStatus.SC_NOT_FOUND);
        Assert.assertEquals(response.text(), "Not Found");
    }

    public Token getAuthToken() {
        Map<String, String> data = new HashMap<>();
        data.put("username", Config.getValue("APIUsername"));
        data.put("password", Config.getValue("APIPassword"));
        APIResponse response = context.post(EP_AUTH, RequestOptions.create().setData(data));
        Assert.assertTrue(response.ok());
        return JsonUtil.getObject(response.text(), Token.class);
    }

    @BeforeTest
    public void initiateAPI() {
        playwright = Playwright.create();
        context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(Config.getValue("baseAPIUrl"))
                .setExtraHTTPHeaders(defaultHeaders));
    }

    @AfterTest
    public void terminateAPIConnection() {
        if (Objects.nonNull(playwright)) {
            playwright.close();
            if (Objects.nonNull(context)) {
                context.dispose();
            }
        }
        context = null;
        playwright = null;
    }
}
