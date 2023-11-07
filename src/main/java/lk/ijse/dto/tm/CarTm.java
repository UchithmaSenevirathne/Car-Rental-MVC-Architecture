package lk.ijse.dto.tm;

public class CarTm {
    String carNo;
    String brand;

    public CarTm(){}
    public CarTm(String carNo, String brand) {
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
        return "CarTm{" +
                "carNo='" + carNo + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
