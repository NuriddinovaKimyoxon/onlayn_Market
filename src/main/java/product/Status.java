package product;

public enum Status {
    START("START"), BEING_DELIVERED("BEING_DELIVERED"),DELIVERED("DELIVERED");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
