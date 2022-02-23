package spring.dto;

public class ServiceDTO {
    private String status;
    private String result;

    public String getStatus() {
        return status;
    }

    public ServiceDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ServiceDTO setResult(String result) {
        this.result = result;
        return this;
    }
}
