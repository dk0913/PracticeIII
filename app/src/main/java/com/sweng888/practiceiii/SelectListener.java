package com.sweng888.practiceiii;

import model.Product;
/*Custom listener interface for obtaining the product model that the user has tapped within a
* RecyclerView*/
public interface SelectListener {
    void onItemClicked(Product product);
}
