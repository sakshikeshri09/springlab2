package org.cap.entities;

import java.util.List;
import java.util.Set;

public class Customer {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String password;

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    private String favoriteProduct;

    public String getFavoriteProduct(){
        return favoriteProduct;
    }

    public void setFavoriteProduct(String product){
        this.favoriteProduct=product;
    }

}
