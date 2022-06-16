package utils;

public enum APIEndpoint {

    AUTH("/auth"),
    BOOKING("/booking"),
    BOOKING_BY_ID("/booking/%s");

    private String text;

    APIEndpoint(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public APIEndpoint getFormatted(String... values) {
        this.text = String.format(text, values);
        return this;
    }
}
