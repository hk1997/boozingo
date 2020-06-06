package com.example.boozingo.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boozingo.R;
import com.example.boozingo.adapters.ScrollCityAdapter;
import com.example.boozingo.util.City;
import com.example.boozingo.viewModels.AppModel;

import java.util.ArrayList;

public class CitySlider extends Fragment {

    private CitySliderViewModel mViewModel;
    private AppModel appModel;
    private ScrollCityAdapter cityAdapter;
    private RecyclerView recycler;

    public static CitySlider newInstance() {
        return new CitySlider();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.city_slider_fragment, container, false);
        appModel = new ViewModelProvider(getActivity()).get(AppModel.class);
        mViewModel = new  ViewModelProvider(getActivity()).get(CitySliderViewModel.class);

        recycler = view.findViewById(R.id.city_slider_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        recycler.setLayoutManager(layoutManager);

        cityAdapter= new ScrollCityAdapter(appModel.getCityList().getValue(),getContext(), new ScrollCityAdapter.OnItemClick(){
            @Override
            public void setCity(int pos) {
                City c = appModel.getCityList().getValue().get(pos);
                appModel.getCityName().setValue(c.getCityName());

            }
        });

        appModel.getCityList().observe(getViewLifecycleOwner(), new Observer<ArrayList<City>>() {
            @Override
            public void onChanged(ArrayList<City> cities) {
                cityAdapter.setCityList(cities);
                cityAdapter.notifyDataSetChanged();
            }
        });

        recycler.setAdapter(cityAdapter);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
