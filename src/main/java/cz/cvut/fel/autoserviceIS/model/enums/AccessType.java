package cz.cvut.fel.autoserviceIS.model.enums;

public enum AccessType {
    CUSTOMER_ACCESS("CUSTOMER_ACCESS"),
    EMPLOYEE_ACCESS("EMPLOYEE_ACCESS"),
    ADMIN_ACCESS("ADMIN_ACCESS");
    private final String access;
    AccessType(String access) {
        this.access = access;
    }
}
