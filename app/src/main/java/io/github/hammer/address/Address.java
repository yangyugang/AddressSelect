package io.github.hammer.address;

/**
 * Created by Hammer on 6/24/15.
 */
public class Address {

    public Integer addressNo;
    public String name;
    public Integer zipCode;

    public Address() {
    }

    public Address(Integer addressNo, String name, Integer zipCode) {
        this.addressNo = addressNo;
        this.name = name;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressNo=" + addressNo +
                ", name='" + name + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
