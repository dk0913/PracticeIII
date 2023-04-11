package com.sweng888.practiceiii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.ProductAdapter;
import model.ProductDatabaseHelper;
/*MainActivity displays list of pet food products to user and user can tap to add to cart and
* tap a button that takes them to the second activity for viewing their cart. Implements a
* custom listener interface that handles user tapping items within RecyclerView*/
public class MainActivity extends AppCompatActivity implements SelectListener {
    //declaring all necessary variables
    private RecyclerView mRecyclerView;
    private ProductDatabaseHelper databaseHelper;
    private ProductAdapter productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button mViewCartButton;
    private ArrayList<Product> cartList;

    /*onCreate method triggers all necessary initialization and sets click listener for button*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Main Activity Starting...");
        /*initializes and wires the the android View objects to corresponding to UI layout objects
         * so that the user can interact with them and databaseHelper and cartList objects*/
        mRecyclerView = findViewById(R.id.recycler_view_main);
        databaseHelper = new ProductDatabaseHelper(this);
        mViewCartButton = findViewById(R.id.view_cart_Button);
        cartList = new ArrayList<Product>();

        /*Clears and populates all pet food product information stored in the database*/
        this.deleteDatabase("product_database");
        List<Product> products = databaseHelper.getAllProducts();
        if(products.isEmpty()){
            databaseHelper.populateProductDatabase();
            products = databaseHelper.getAllProducts();
        }
        /* Creates and sets adapter, decoration, and layout management for RecyclerView displaying
        * list of selectable pet food products to the user*/
        productAdapter = new ProductAdapter(this,products, this);
        mRecyclerView.setAdapter(productAdapter);
        mRecyclerView.addItemDecoration(new SpacingItemDecorator(0));
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);

        /*setting onClickListener for View Cart button to use explicit intent to start SecondActivity and
        pass all products stored in the cartList for Second Activity to display*/
        mViewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // Passing the data to the
                // SecondActivity
                int i = 1;
                for (Product productInList:cartList){

                    intent.putExtra("selected_product_"+ Integer.toString(i),productInList);
                    i++;
                }
                intent.putExtra("number_of_items",i-1);
                startActivity(intent);
            }
        });
    }
    /*Overriding onItemClicked method in SelectListener interface to add the product tapped by the
    * user to the cart and show a Toast indicating that the item has been added to the cart*/
    @Override
    public void onItemClicked(Product product) {
        cartList.add(product);
        Toast.makeText(this,product.getName()+" has been added to cart!",Toast.LENGTH_SHORT).show();
    }
}