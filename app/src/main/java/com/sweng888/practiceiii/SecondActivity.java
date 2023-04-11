package com.sweng888.practiceiii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Product;
import model.ProductAdapter;
/*SecondActivity displays list of pet food products to user and user can tap to add to cart and
 * tap a button that takes them to the second activity for viewing their cart. Implements a
 * custom listener interface that handles user tapping items within RecyclerView*/
public class SecondActivity extends AppCompatActivity {
    //Declares all necessary variables
    private Button mEmailButton;
    private RecyclerView mRecyclerView;
    private ArrayList<Product> cartProducts;
    private ProductAdapter productAdapter;
    private RecyclerView.LayoutManager layoutManager;

    /*onCreate method triggers all necessary initialization and sets click listener for email button*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        System.out.println("Second Activity Starting...");

        /*initializes and wires the the android View objects to corresponding to UI layout objects
         * so that the user can interact with them*/
        mEmailButton = findViewById(R.id.email_button);
        mRecyclerView = findViewById(R.id.recycler_view_second);
        cartProducts = new ArrayList<Product>();

        /*Gets intent passed from MainActivity and gets each product in the cart passed as extras
        * and adds them to the list of products in the cart*/
        Intent intent = getIntent();
        int numItems = intent.getIntExtra("number_of_items", 1);
        for(int i = 1; i<=numItems; i++){
            cartProducts.add((Product)intent.getSerializableExtra("selected_product_"+
                    Integer.toString(i)));
        }
        /* Creates and sets adapter, decoration, and layout management for RecyclerView displaying
         * list of cart products in RecyclerView to the user*/
        productAdapter = new ProductAdapter(this, cartProducts);
        mRecyclerView.setAdapter(productAdapter);
        mRecyclerView.addItemDecoration(new SpacingItemDecorator(0));
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);

        /*setting onClickListener for email button to use implicit intent to start email app of user's
         choice and send an email with cart items and total cost*/
        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
                Toast.makeText(SecondActivity.this," Email Sent and Cart Will Be Cleared",Toast.LENGTH_SHORT).show();
                cartProducts = null;
                ProductAdapter emptyAdapter = new ProductAdapter(SecondActivity.this, cartProducts);
                mRecyclerView.setAdapter(emptyAdapter);
            }
        });


    }

    /*creates Implicit intent to create an email with specified email, subject, and message body and
    * starts and email activity using createChooser so user can pick which email app to use*/
    private void sendEmail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        String email = "sweng888mobileapps@gmail.com";
        String subject  = "Cart Items "+ LocalDate.now().toString();
        String message = makeEmailMessage();

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        // set type of intent
        intent.setType("*/*");

        if(intent.resolveActivity(getPackageManager())!= null){
            // startActivity with intent with chooser as Email client using createChooser function
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        }

    }
    /*helps the sendEmail() message to concatenate email message body together and calculate total
    * price of items in cart*/
    private String makeEmailMessage(){
        String message = "Cart Items:\n";
        Double total = 0.00;

        for(Product product: cartProducts){
            message = message + "Name: " + product.getName() +"\n"+
                    "Price: $" + product.getPrice().toString()+"\n";
            total = total+product.getPrice();
        }

        return message + "Total: \n$"+total.toString();
    }
}