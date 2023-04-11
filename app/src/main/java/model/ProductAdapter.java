package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sweng888.practiceiii.MainActivity;
import com.sweng888.practiceiii.R;
import com.sweng888.practiceiii.SelectListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/*ProductAdapter class adapts a list of pet food Product data model objects to be displayed and selected in
* a RecyclerView */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;

    private View.OnClickListener onClickListener;
    private SelectListener listener;
    private Context context;
    /*Constructor for ProductAdapter with a listener for user selecting items used in MainActivity*/
    public ProductAdapter(Context context,List<Product> products, SelectListener listener){
        this.context = context;
        this.products = products;
        this.listener = listener;
    }
    /*Constructor for ProductAdapter without a listener for user selecting items used in SecondActivity*/
    public ProductAdapter(Context context,List<Product> products){
        this.context = context;
        this.products = products;
    }
    /*inflates product_item layout when ViewHolder is created for displaying each individual product
    * in RecyclerView*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }
    /*binds each view in product_item layout to the corresponding field in the Product data model
    * object*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameTextView.setText("Name: "+ product.getName());
        holder.descriptionTextView.setText("Description: "+product.getDescription());
        holder.sellerTextView.setText("Seller: "+product.getSeller());
        holder.priceTextView.setText("Price: $"+String.valueOf(product.getPrice()));
        /*uses helper method below to set set the Bitmap for the item's imageView with the URL stored
        * in the Product data model object*/
        setImageBitmap(product.getImageURI(),holder);
        /*sets click listener for user tapping on a CardView container to get the item at the
        Recyclerview position and the product in the products list at that position*/
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    listener.onItemClicked(products.get(holder.getAdapterPosition()));
            }
        });
    }
    //returns the size of the products list displayed in the RecyclerView
    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        }else{
            return 0;
        }
    }
    //declares and wires up each view for a Product item
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView,descriptionTextView,sellerTextView, priceTextView;
        public ImageView imageView;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.TextView_Name);
            descriptionTextView = itemView.findViewById(R.id.TextView_Description);
            sellerTextView = itemView.findViewById(R.id.TextView_Seller);
            priceTextView = itemView.findViewById(R.id.TextView_Price);
            imageView = itemView.findViewById(R.id.ImageView);
            cardView = itemView.findViewById(R.id.main_container);
        }
    }
    /*helper method for handling setting the Bitmap of the imageView of the ViewHolder for the item to a
    Bitmap created by retrieving the image on a different thread at the stored URL for the product image*/
    private void setImageBitmap(String imageURL, ViewHolder holder){
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    InputStream inputStream = new URL(imageURL).openStream();
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageView.setImageBitmap(image);
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
