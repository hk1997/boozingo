package com.example.boozingo.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.boozingo.R;
import com.example.boozingo.models.ResponseListCities;
import com.example.boozingo.util.City;
import com.example.boozingo.util.RetrofitClient;
import com.example.boozingo.viewModels.AppModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCity extends Fragment {


    private AppModel appModel;

    public static SearchCity newInstance() {
        return new SearchCity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_city_fragment, container, false);
        appModel = new ViewModelProvider(getActivity()).get(AppModel.class);


        initializeCities(view);
        return view;
    }

    private void initializeCities(View view){
        appModel.getCityList().observe(getViewLifecycleOwner(), new Observer<ArrayList<City>>() {
            @Override
            public void onChanged(ArrayList<City> cities) {
                if(cities!=null){
                    ArrayAdapter<City> adapter =
                            new ArrayAdapter<>(
                                    getContext(),
                                    R.layout.dropdown_menu_popup_item,
                                    cities);
                    AutoCompleteTextView editTextFilledExposedDropdown =
                            view.findViewById(R.id.filled_exposed_dropdown);
                    editTextFilledExposedDropdown.setAdapter(adapter);

                    editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            City selectedCity = appModel.getCityList().getValue().get(position);
                            appModel.getCityName().setValue(selectedCity.getCityName());
                            Log.d("city","city "+selectedCity.getCityName());
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
