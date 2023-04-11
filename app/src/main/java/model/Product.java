package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
/*Product is the data model for the pet food products stored in the database*/
public class Product implements Serializable {
    //declares data model fields for each column in database table
    private String name;
    private String description;
    private String seller;
    private Double price;
    private String imageURI;

    //regular constructor setting each data model field with passed in values
    public Product(String name, String description, String seller, Double price, String imageURI){
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.imageURI = imageURI;
    }
    /*constructor used by databaseHelper which constructs the object from a parcel containing the
    product information for an entry in the database table */
    public Product(Parcel in) throws URISyntaxException {
        name = in.readString();
        description = in.readString();
        seller = in.readString();
        price = in.readDouble();
        imageURI = in.readString();
    }
    //getters and setters for all fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
