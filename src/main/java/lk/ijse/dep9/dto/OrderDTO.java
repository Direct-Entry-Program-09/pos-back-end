package lk.ijse.dep9.dto;

import java.io.Serializable;

public class OrderDTO implements Serializable {
    private String id;
    private String date;
    private String customer_id;

    public OrderDTO() {
    }

    public OrderDTO(String id, String date, String customer_id) {
        this.id = id;
        this.date = date;
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", customer_id='" + customer_id + '\'' +
                '}';
    }
}
