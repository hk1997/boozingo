package com.example.boozingo.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boozingo.R;
import com.example.boozingo.util.RetrofitClient;
import com.example.boozingo.util.Summary;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ShopSummaryAdapter extends RecyclerView.Adapter<ShopSummaryAdapter.ShopSummaryViewHolder> {
    ArrayList<Summary> listShopSummary;
    Context mContext;
    OnItemClick onItemClick;

    public ShopSummaryAdapter(ArrayList<Summary> listShopSummary, Context mContext, OnItemClick onItemClick) {
        this.listShopSummary = listShopSummary;
        this.mContext = mContext;
        this.onItemClick = onItemClick;
    }

    public void setListShopSummary(ArrayList<Summary> listShopSummary) {
        this.listShopSummary = listShopSummary;
    }

    @NonNull
    @Override
    public ShopSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_shop_summary, parent, false);
        return new ShopSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopSummaryViewHolder holder, int position) {
        Summary shopSummary = this.listShopSummary.get(position);
        holder.setShopName(shopSummary.getName());
        holder.setShopImage("/images/lounge3.jpg");
        holder.setShopCost("₹ "+shopSummary.getCost());

        String[] arr=shopSummary.getDetails().split("/");
        ArrayList<String> details = new ArrayList<>(Arrays.asList(arr));
        holder.setShopDetails(details);

        Float f, dist;
        try{
            f= new Float(shopSummary.getAvgRating());
            dist = new Float(shopSummary.getDistance());
            dist = new Float( Math.round(dist*100.0)/100.0);
        }
        catch (Exception e){
            f=0f;
            dist = 0f;
        }
        holder.setRating(f);

        holder.setShopDistance(dist.toString()+" km ");
    }

    @Override
    public int getItemCount() {
        return (listShopSummary==null)?0:listShopSummary.size();
    }

    public interface OnItemClick {
        void selectShop(int pos);
    }

    public class ShopSummaryViewHolder extends RecyclerView.ViewHolder{
        private TextView shopName, shopDistance, shopCost;
        private ImageView shopImage;
        private MaterialRatingBar ratingBar;
        private ChipGroup shopDetails;

        public ShopSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.selectShop(getAdapterPosition());
                }
            });
            shopName= itemView.findViewById(R.id.shop_name);
            shopDistance = itemView.findViewById(R.id.shop_distance);
            shopCost = itemView.findViewById(R.id.shop_cost);
            shopDetails = itemView.findViewById(R.id.shop_details);
            shopImage = itemView.findViewById(R.id.shopImage);
            ratingBar = itemView.findViewById(R.id.material_rating_bar);
        }

        public void setShopName(String name){
            shopName.setText(name);
        }
        public void setShopDistance(String distance){
            shopDistance.setText(distance);
        }

        public void setShopDetails(ArrayList<String> l){
            final float scale = mContext.getResources().getDisplayMetrics().density;
            final float chipHeight = 20;
            for(String s:l){
                Chip c= new Chip(mContext);
                c.setText(s);
                shopDetails.addView(c);
            }
        }

        public void setShopCost(String s){shopCost.setText(s);}
        public void setShopImage(String imageUrl){
            Picasso.get().load(RetrofitClient.getBase_Url()+imageUrl).into(shopImage);
        }
        public void setRating(float d){
            ratingBar.setRating(d);
            ratingBar.setEnabled(false);
        }
    }
}
