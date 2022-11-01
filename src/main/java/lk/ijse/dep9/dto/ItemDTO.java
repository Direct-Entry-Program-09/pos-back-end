package lk.ijse.dep9.dto;

import java.io.Serializable;

public class ItemDTO implements Serializable {
    private String code;
    private String description;
    private double unit_price;
    private int stock;

    public ItemDTO() {
    }

    public ItemDTO(String code, String description, double unit_price, int stock) {
        this.code = code;
        this.description = description;
        this.unit_price = unit_price;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unit_price=" + unit_price +
                ", stock=" + stock +
                '}';
    }
}
