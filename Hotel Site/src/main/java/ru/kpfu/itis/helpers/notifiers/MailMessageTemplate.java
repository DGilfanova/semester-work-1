package ru.kpfu.itis.helpers.notifiers;

public class MailMessageTemplate {

    public String createAddBookingText(String bookingId) {
        StringBuilder text = new StringBuilder();
        text.append("Запрос на бронирование. id бронирования ")
                .append(bookingId)
                .append(".");
        return text.toString();
    }

    public String createAddSuggestionText(String orderId) {
        StringBuilder text = new StringBuilder();
        text.append("Запрос на заказ. id заказа ")
                .append(orderId)
                .append(".");
        return text.toString();
    }

    public String createUpdateSuggestionText(String orderId) {
        StringBuilder text = new StringBuilder();
        text.append("Запрос на изменение заказа. id заказа ")
                .append(orderId)
                .append(".");
        return text.toString();
    }

    public String createUserBookingText(String reservationNumber) {
        StringBuilder text = new StringBuilder();
        text.append("Добро пожаловать! Номер вашего бронирования: ")
                .append(reservationNumber)
                .append(". Он необходим для просмотра вашего бронирования. Никому не показывайте!");
        return text.toString();
    }
}
