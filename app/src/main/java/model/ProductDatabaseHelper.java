package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import java.util.ArrayList;
import java.util.List;

import model.Product;
/*ProductDatabaseHelper class extends SQLiteOpenHelper to manage all communication between app
* and SQLite Database*/
public class ProductDatabaseHelper extends SQLiteOpenHelper {
    /*declares a Database having a table with product information including ID, name, description,
    * seller, price and a picture of the product*/
    private static final String DATABASE_NAME = "product_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SELLER = "seller";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PICTURE = "picture";

    //constructor creating a SQLiteOpenHelper in a passed context
    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*onCreate method trigger when SQLiteDatabase is created, executes a SQL query creating the
    * table of pet food product information*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CREATE_Products_Tables = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_SELLER + " TEXT," +
                KEY_PRICE + " DECIMAL," +
                KEY_PICTURE + " TEXT" +
                ")";
        db.execSQL(QUERY_CREATE_Products_Tables);
    }
    //method defining what to do when the database version is updated, empty for now
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*populates table of pet food products using ContentValue objects with each column value for the
    * product, inserts 5 entries (rows) of pet food products*/
    public void populateProductDatabase() {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(KEY_NAME, "NATURAL BALANCE BISCUITS DUCK & POTATO 28OZ");
        values1.put(KEY_DESCRIPTION, "Ingredients: " +
                "Potatoes, Duck, Potato Protein, Cane Molasses, Canola Oil (Mixed Tocopherols Used " +
                "As A Preservative), Natural Flavor, Rosemary Extract, Citric Acid (Used As A " +
                "Preservative), Mixed Tocopherols (Used As A Preservative). ");
        values1.put(KEY_SELLER, "Pickering Valley Feed and Farm");
        values1.put(KEY_PRICE, 13.99);
        values1.put(KEY_PICTURE, "https://cdn.shoplightspeed.com/shops/632954/files/23967530/1600x2048x1/natural-balance-pet-foods-inc-natural-balance-bisc.jpg");
        database.insert(TABLE_PRODUCTS, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(KEY_NAME, "FROMM DOG FROMMBALAYA BEEF & RICE STEW CAN 12.5OZ CASE OF 12");
        values2.put(KEY_DESCRIPTION, "Ingredients: " +
                "Beef, Beef Broth, Pork Broth, Pork, Potatoes, Carrots, White Rice, Peas, Dried Egg " +
                "Product, Xanthan Gum, Salt, Salmon Oil, Minerals, Potassium Chloride, Vitamins.");
        values2.put(KEY_SELLER, "Pickering Valley Feed and Farm");
        values2.put(KEY_PRICE, 49.80);
        values2.put(KEY_PICTURE, "https://cdn.shoplightspeed.com/shops/632954/files/23898620/1600x2048x1/fromm-family-foods-llc-fromm-dog-frommbalaya-beef.jpg");
        database.insert(TABLE_PRODUCTS, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(KEY_NAME, "BOCCES BAKERY DOG SOFT CHEWY SANTA S'MORES 6OZ");
        values3.put(KEY_DESCRIPTION, "Ingredients: Oat Flour, Peanut Butter, Coconut Glycerin, Rolled " +
                "Oats, Molasses, Flaxseed, Vanilla, Carob Chips, Citric Acid (Preservative)");
        values3.put(KEY_SELLER, "Pickering Valley Feed and Farm");
        values3.put(KEY_PRICE, 5.99);
        values3.put(KEY_PICTURE, "https://cdn.shoplightspeed.com/shops/632954/files/27438190/1600x2048x1/bocces-bakery-dog-soft-chewy-santa-smores-6oz.jpg");
        database.insert(TABLE_PRODUCTS, null, values3);

        ContentValues values4 = new ContentValues();
        values4.put(KEY_NAME, "ZUKES MINI NATURALS PEANUT BUTTER & OATS 6OZ");
        values4.put(KEY_DESCRIPTION, "Ingredients: " +
                "Peanut Butter, Barley, Rice, Oats, Malted Barley Extract, Vegetable Glycerin, " +
                "Tapioca Starch Modified, Cherries, Potato Protein, Dried Cultured Whey Product, " +
                "Sunflower Lecithin, Salt, Phosphoric Acid, Turmeric Spice, Vitamin E Supplement, " +
                "Zinc Proteinate, Citric Acid for freshness, Mixed Tocopherols for freshness, " +
                "Rosemary Extract.");
        values4.put(KEY_SELLER, "Pickering Valley Feed and Farm");
        values4.put(KEY_PRICE, 7.99);
        values4.put(KEY_PICTURE, "https://cdn.shoplightspeed.com/shops/632954/files/24037668/1600x2048x1/zukes-perform-pet-nutrition-zukes-mini-naturals-pe.jpg");
        database.insert(TABLE_PRODUCTS, null, values4);

        ContentValues values5 = new ContentValues();
        values5.put(KEY_NAME, "LANCASTER MEAT CO. ALLIGATOR MINI MEATBALLS 4OZ");
        values5.put(KEY_DESCRIPTION, "Ingredients:\n" +
                "Alligator, Chicken, Chickpeas, Molasses, Glycerin, Honey, Dried Cultured Skim Milk, " +
                "Salt, Natural Smoke Flavor, Citric Acid, Rosemary Extract, Mixed Tocopherols " +
                "(A Preservative)");
        values5.put(KEY_SELLER, "Pickering Valley Feed and Farm");
        values5.put(KEY_PRICE, 9.99);
        values5.put(KEY_PICTURE, "https://cdn.shoplightspeed.com/shops/632954/files/27300144/1600x2048x1/lancaster-meat-co-alligator-mini-meatballs-4oz.jpg");
        database.insert(TABLE_PRODUCTS, null, values5);

        database.close();
    }

    /*Uses a Cursor to create a list of Product DMOs for each entry in TABLE_PRODUCTS */
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1),//Name
                        cursor.getString(2),//Description
                        cursor.getString(3),//Seller
                        cursor.getDouble(4),//Price
                        cursor.getString(5)//Image URL
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return productList;
    }

    /*Uses a Cursor to create a list of Product DMOs for each entry in TABLE_PRODUCTS matching a passed
    * category, currently the seller of the product */
    public List<Product> getProductsByCategory (String category){
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + KEY_SELLER + " = ?";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{category});

        if (cursor.moveToFirst()){
            do {
                Product product = new Product(
                        cursor.getString(1), // NAME
                        cursor.getString(2), // DESCRIPTION
                        cursor.getString(3), // SELLER
                        cursor.getDouble(4), // PRICE
                        cursor.getString(5) // IMAGE URL
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return  productList;
    }
}

