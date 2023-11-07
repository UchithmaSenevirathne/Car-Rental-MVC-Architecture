package lk.ijse.dto;

public class CarDto {
    private String carNo;
    private String brand;

    public CarDto(String carNo, String brand) {
        this.carNo = carNo;
        this.brand = brand;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "carNo='" + carNo + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
