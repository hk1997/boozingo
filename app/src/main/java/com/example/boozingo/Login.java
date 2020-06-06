package com.example.boozingo;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.boozingo.models.ApiResponse;
import com.example.boozingo.util.Auth;
import com.example.boozingo.util.RetrofitClient;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Login extends Fragment {

    TextView userName, password,forgotPassword;
    Button buttonSignIn, buttonSignUp;
    TextInputLayout layoutUserName, layoutPassword;


    public static Login newInstance() {
        return new Login();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        userName = view.findViewById(R.id.userName);
        password = view.findViewById(R.id.password);
        forgotPassword= view.findViewById(R.id.forgot_password);

        buttonSignIn = view.findViewById(R.id.button_signIn);
        buttonSignUp = view.findViewById(R.id.button_signUp);

        layoutUserName = view.findViewById(R.id.layout_userName);
        layoutPassword = view.findViewById(R.id.layout_password);

        signInListener(view);
        signUpListener(view);
        forgotPasswordListener(view);

        return view;
    }

    private void signInListener(View view){

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUserName = userName.getText().toString().trim();
                String textPassword = password.getText().toString();
                if(textUserName==null || textUserName.length()==0){
                    layoutUserName.setError("required");
                    return;
                }
                if(textPassword==null || textPassword.length()==0){
                    layoutPassword.setError("required");
                    return;
                }

                buttonSignIn.setEnabled(false);
                //doing api call
                Call<ApiResponse> call = RetrofitClient.getInstance().getApi().login(textUserName,textPassword);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        String yo="";
                        if(response.code()!=200){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Snackbar.make(view,jObjError.getString("message"), BaseTransientBottomBar.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Snackbar.make(view, e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                            }

                            buttonSignIn.setEnabled(true);
                        }
                        else{
                            String token = response.body().getData().get("token").toString();
                            token = token.substring(1,token.length()-1);
                            Auth.setAccessToken(getContext(),token);
                            Log.d("token","token="+ Auth.getAccessToken(getContext()));
                            Intent i = new Intent(getContext(),MainActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Snackbar.make(view,"Some error occured", BaseTransientBottomBar.LENGTH_SHORT).show();
                        buttonSignIn.setEnabled(true);
                        Log.d("error",t.getMessage());
                    }
                });
            }
        });
    }

    private void signUpListener(View view){
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),SignUp.class);
                startActivity(i);
            }
        });

    }

    private void forgotPasswordListener(View view){
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container2,ForgotPassword.newInstance());
                transaction.commit();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
