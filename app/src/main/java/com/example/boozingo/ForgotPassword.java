package com.example.boozingo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.boozingo.models.ApiResponse;
import com.example.boozingo.util.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPassword extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView userName, password, confirmPassword;
    Button  buttonUpdatePassword;
    TextInputLayout layoutUserName, layoutPassword, layoutConfirmPassword;

    public static ForgotPassword newInstance() {
        return new ForgotPassword();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment forgot_password.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPassword newInstance(String param1, String param2) {
        ForgotPassword fragment = new ForgotPassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



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
        View view= inflater.inflate(R.layout.fragment_forgot_password, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("tag", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container2,Login.newInstance());
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });

        userName = view.findViewById(R.id.userName);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirm_password);


        buttonUpdatePassword = view.findViewById(R.id.button_updatePassword);


        layoutUserName = view.findViewById(R.id.layout_userName);
        layoutPassword = view.findViewById(R.id.layout_password);
        layoutConfirmPassword = view.findViewById(R.id.layout_confirm_password);

        updatePasswordListener(view);


        return view;
    }

    private void updatePasswordListener(View view){

        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUserName = userName.getText().toString().trim();
                String textPassword = password.getText().toString();
                String textConfirmPassword = confirmPassword.getText().toString();
                if(textUserName==null || textUserName.length()==0){
                    layoutUserName.setError("required");
                    return;
                }
                if(textPassword==null || textPassword.length()==0){
                    layoutPassword.setError("required");
                    return;
                }
                if(textConfirmPassword==null || textConfirmPassword.length()==0){
                    layoutConfirmPassword.setError("required");
                    return;
                }
                if(!textPassword.equals(textConfirmPassword)){
                    layoutConfirmPassword.setError("Password & Confirm Password must match");
                    return;
                }

                buttonUpdatePassword.setEnabled(false);
                //doing api call
                Call<ApiResponse> call = RetrofitClient.getInstance()
                        .getApi()
                        .forgotPassword(textUserName,textPassword,textConfirmPassword);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if(response.code()!=200){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Snackbar.make(view,jObjError.getString("message"), BaseTransientBottomBar.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Snackbar.make(view, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                            }

                            buttonUpdatePassword.setEnabled(true);
                        }
                        else{
                            Snackbar.make(view,response.body().getMessage(),BaseTransientBottomBar.LENGTH_SHORT).show();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container2,Login.newInstance());
                            transaction.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Snackbar.make(view,"Some error occured", BaseTransientBottomBar.LENGTH_SHORT).show();
                        buttonUpdatePassword.setEnabled(true);
                        Log.d("error",t.getMessage());
                    }
                });
            }
        });
    }
}
