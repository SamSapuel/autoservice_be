package cz.cvut.fel.autoserviceIS.model.enums;

public enum OrdersStatus {
    IN_PROCESSING("IN_PROCESSING"),
    REJECTED("REJECTED"),
    ACCEPTED("ACCEPTED"),
    DONE("DONE");

    private final String status;
    OrdersStatus(String status) {
        this.status = status;
    }

}
