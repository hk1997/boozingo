package com.example.boozingo;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.boozingo.adapters.ImageSliderAdapter;
import com.example.boozingo.util.SliderItem;
import com.example.boozingo.util.Summary;
import com.example.boozingo.viewModels.AppModel;
import com.example.boozingo.viewModels.ShopDetailsModel;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopSummaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopSummary.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopSummaryFragment newInstance(String param1, String param2) {
        ShopSummaryFragment fragment = new ShopSummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView shopName, shopAddress, ratingCount;
    private MaterialRatingBar ratingBar;
    private LinearLayout addReview, addPhoto, addBookmark, share;
    private ImageView shareLocation;
    private ProgressWheel progressWheel;

    ShopDetailsModel mModel;
    AppModel appModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shop_summary, container, false);

        mModel = new ViewModelProvider(this).get(ShopDetailsModel.class);
        appModel= new ViewModelProvider(getActivity()).get(AppModel.class);

        progressWheel = new ProgressWheel(getContext());

        setFields(view);
        setImageSlider(view);
        //fetching data
        progressWheel.spin();
        String shopId = "1";
        //shop id changed after pressing back button
        if(mModel.getShopId().getValue()!=null && !mModel.getShopId().getValue().equals(shopId)){
            mModel.getShopId().setValue(shopId);
            mModel.getShopSummary().setValue(null);
            mModel.fetchShopSummary(shopId);
        }
        mModel.getShopId().setValue(shopId);

        mModel.getShopSummary().observe(getViewLifecycleOwner(), new Observer<Summary>() {
            @Override
            public void onChanged(Summary summary) {
                progressWheel.spin();
                setFieldDetails();
            }
        });

        setFieldDetails();

        return view;
    }

    private void setFieldDetails(){
        Summary s= mModel.getShopSummary().getValue();
        if(s==null){
            return;
        }
        progressWheel.stopSpinning();
        shopName.setText(s.getName());
        shopAddress.setText(s.getAddress());
        ratingCount.setText("Ratings: "+s.getRatedBy());
        Float f;
        try{
            f= new Float(s.getAvgRating());
        }
        catch (Exception e){
            f=0f;
        }
        ratingBar.setRating(f);
        ratingBar.setEnabled(false);
    }

    private void setFields(View view){
        shopName = view.findViewById(R.id.shop_name);
        shopAddress= view.findViewById(R.id.shop_address);
        ratingCount = view.findViewById(R.id.rating_count);
        ratingBar = view.findViewById(R.id.material_rating_bar);
        addReview = view.findViewById(R.id.add_review);
        addBookmark = view.findViewById(R.id.add_bookmark);
        addPhoto = view.findViewById(R.id.add_photo);
        share = view.findViewById(R.id.share);
        shareLocation = view.findViewById(R.id.location);
    }

    private void setImageSlider(View view){
        SliderView sliderView = view.findViewById(R.id.imageSlider);

        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext());
        adapter.addItem(new SliderItem("d1","/images/lounge1.jpg"));
        adapter.addItem(new SliderItem("d2","/images/lounge2.jpg"));
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }
}
