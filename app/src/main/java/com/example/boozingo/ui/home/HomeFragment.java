package com.example.boozingo.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boozingo.R;
import com.example.boozingo.adapters.ShopSummaryAdapter;
import com.example.boozingo.ui.CitySlider;
import com.example.boozingo.ui.SearchCity;
import com.example.boozingo.util.Summary;
import com.example.boozingo.util.ShopType;
import com.example.boozingo.viewModels.AppModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private AppModel appModel;
    private  HomeViewModel homeViewModel;
    private RecyclerView recycler;
    private ShopSummaryAdapter shopSummaryAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        appModel = new ViewModelProvider(getActivity()).get(AppModel.class);
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        setFragments(view);
        initializeShopType(view);
        setSummaryList(view);

        //observing changes to shop list
        homeViewModel.getShopSummaryList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Summary>>() {
            @Override
            public void onChanged(ArrayList<Summary> shopSummaries) {
                if(shopSummaries!=null){
                    shopSummaryAdapter.setListShopSummary(shopSummaries);
                    shopSummaryAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    private void initializeShopType(View view){
        appModel.getShopTypeList().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShopType>>() {
            @Override
            public void onChanged(ArrayList<ShopType> shopTypes) {
                if(shopTypes!=null){
                    ArrayAdapter<ShopType> adapter =
                            new ArrayAdapter<>(
                                    getContext(),
                                    R.layout.dropdown_menu_popup_item,
                                    shopTypes);
                    AutoCompleteTextView editTextFilledExposedDropdown =
                            view.findViewById(R.id.filled_exposed_dropdown2);
                    editTextFilledExposedDropdown.setAdapter(adapter);

                    editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ShopType selectedShop = appModel.getShopTypeList().getValue().get(position);
                            homeViewModel.getShopType().setValue(selectedShop);
                            Log.d("shopType","shopType "+selectedShop.getLabel());
                        }
                    });
                }

            }
        });
    }

    private  void setSummaryList(View view){
        recycler = view.findViewById(R.id.home_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        shopSummaryAdapter = new ShopSummaryAdapter(homeViewModel.getShopSummaryList().getValue(), getContext(), new ShopSummaryAdapter.OnItemClick() {
            @Override
            public void selectShop(int pos) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_shopDetails);
            }
        });
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(shopSummaryAdapter);
    }


    private  void setFragments(View view){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container1, SearchCity.newInstance());
        transaction.replace(R.id.container2, CitySlider.newInstance());
        transaction.commit();
    }

}
