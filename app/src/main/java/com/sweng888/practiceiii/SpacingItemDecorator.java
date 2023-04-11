package com.sweng888.practiceiii;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/*RecyclerView Item Decoration that lays out spacing between items displayed in RecyclerView and
* between items and parent RecyclerView bounds*/
public class SpacingItemDecorator extends RecyclerView.ItemDecoration {
    private int spacing;

    public SpacingItemDecorator(int spacing){
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,RecyclerView.State state){
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;

        //Add top margin only for the first item to avoid double spacing between items
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.top = spacing;
        } else{
            outRect.top = 0;
        }
    }
}
