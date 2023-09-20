package user;

import common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import product.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<UUID> implements Serializable {
    private String name;
    private String lastname;
    private String password;
    private String email;
    private String phoneNumber;
    private int age;
    private List<Product> basket;
    private double balance;
    private UserType userType;
    private List<Product> boughtProductsHistory;

    public User(UUID uuid, String name, String lastname, String password, String email, String phoneNumber, int age, List<Product> basket, double balance, UserType userType, List<Product> boughtProductsHistory) {
        super(uuid);
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.basket = basket;
        this.balance = balance;
        this.userType = userType;
        this.boughtProductsHistory = boughtProductsHistory;
    }

}
