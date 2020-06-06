package com.example.boozingo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boozingo.R;
import com.example.boozingo.util.City;
import com.example.boozingo.util.RetrofitClient;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScrollCityAdapter extends RecyclerView.Adapter<ScrollCityAdapter.ScrollCityViewHolder>  {

    private ArrayList<City> cityList;
    private Context mContext;
    private OnItemClick onItemClick;

    public ScrollCityAdapter(ArrayList<City> cityList, Context mContext, OnItemClick onItemClick) {
        this.cityList = cityList;
        this.mContext = mContext;
        this.onItemClick = onItemClick;
    }


    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ScrollCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.circle_city_holder, parent, false);
        return new ScrollCityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollCityViewHolder holder, int position) {
            final City city = this.cityList.get(position);
            holder.setCityName(city.getCityName());
            holder.setImage(city.getCityIcon());
    }

    @Override
    public int getItemCount() {
        return (this.cityList==null)?0:this.cityList.size();
    }

    public interface OnItemClick {
        void setCity(int pos);
    }

    public class ScrollCityViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView cityName;

        public ScrollCityViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.city_image);
            cityName = itemView.findViewById(R.id.city_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.setCity(getAdapterPosition());
                }
            });
        }

        public void setImage(String image) {
            Picasso.get().load(RetrofitClient.getBase_Url()+image).noFade().into(this.image);
        }

        public void setCityName(String cityName) {
            this.cityName.setText(cityName);
        }
    }
}
