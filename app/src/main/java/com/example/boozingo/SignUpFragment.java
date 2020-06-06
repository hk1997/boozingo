package com.example.boozingo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boozingo.models.ApiResponse;
import com.example.boozingo.util.Auth;
import com.example.boozingo.util.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    TextView firstName, lastName, userName, password, confirmPassword,email,phone,dob;
    TextInputLayout layoutFirstName, layoutLastName, layoutUserName,layoutDob,
            layoutPassword, layoutConfirmPassword
            ,layoutEmail,layoutPhone,layoutGender;
    Button signUp;
    String fName,lName,pAssword,cPassword,pHone,eMail,uName,textDob,textGender;
    MutableLiveData<Boolean> uniquePhone, uniqueEmail, uniqueUserName, allChecked;
    boolean enteredDob=false, registerCall=false;
    // gender components;
    String[] genders= new String[]{"male","female","other"};

    //date picker components
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    public SignUpFragment() {
        // Required empty public constructor
        uniqueEmail = new MutableLiveData<>();
        uniquePhone = new MutableLiveData<>();
        uniqueUserName = new MutableLiveData<>();
        allChecked = new MutableLiveData<>();
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        userName = view.findViewById(R.id.userName);
        confirmPassword = view.findViewById(R.id.confirm_password);
        password = view.findViewById(R.id.password);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        dob = view.findViewById(R.id.dob);

        layoutFirstName = view.findViewById(R.id.layout_firstName);
        layoutLastName = view.findViewById(R.id.layout_lastName);
        layoutUserName = view.findViewById(R.id.layout_userName);
        layoutPassword = view.findViewById(R.id.layout_password);
        layoutConfirmPassword  = view.findViewById(R.id.layout_confirmPassword);
        layoutEmail = view.findViewById(R.id.layout_email);
        layoutPhone = view.findViewById(R.id.layout_phone);
        layoutDob = view.findViewById(R.id.layout_dob);
        layoutGender = view.findViewById(R.id.layout_gender);

        signUp = view.findViewById(R.id.button_signUp);
        setSignUpListener();
        setOnChangeListener();
        setOnAllChecksCompleteLister();
        setDatePickerPopup();
        //setting dropdown menu for gender
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        genders);
        AutoCompleteTextView editTextFilledExposedDropdown =
                view.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textGender = genders[position];
            }
        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
        textDob = sdf.format(myCalendar.getTime());
        enteredDob=true;
    }

    private void setDatePickerPopup(){
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //function that calls register method when all checks are complete
    private  void setOnAllChecksCompleteLister(){
        allChecked.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean && !registerCall){
                    Call<ApiResponse> call = RetrofitClient.getInstance()
                            .getApi().register(fName,lName,uName,pHone,eMail,pAssword,cPassword,textDob,textGender);
                    registerCall = true;
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if(response.code()!=200){
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Snackbar.make(getView(),jObjError.getString("message"),BaseTransientBottomBar.LENGTH_SHORT).show();
                                    registerCall = false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                                String token = response.body().getData().get("token").toString();
                                token = token.substring(1,token.length()-1);
                                Auth.setAccessToken(getContext(),token);
                                Log.d("token","token="+ Auth.getAccessToken(getContext()));
                                Intent i= new Intent(getActivity(),MainActivity.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Snackbar.make(getView(),"Some error occured", BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean checkEmpty(String text, TextInputLayout layout){
        if(text==null || text.equals("")){
            layout.setError("required");
            return false;
        }
        else{
            layout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateInput(){

        boolean valid=true;
        Log.d("fas",fName);
        valid = valid && checkEmpty(fName,layoutFirstName);
        valid = valid && checkEmpty(lName,layoutLastName);
        valid = valid && checkEmpty(uName,layoutUserName);
        valid = valid && checkEmpty(eMail,layoutEmail);
        valid = valid && checkEmpty(pHone,layoutPhone);
        valid = valid && checkEmpty(pAssword,layoutPassword);
        valid = valid && checkEmpty(cPassword,layoutConfirmPassword);


        if(valid){
            if(password.length()<8){
                layoutPassword.setError("Minimum password length is 8");
            }
            else if(!pAssword.equals(cPassword)){
                layoutConfirmPassword.setError("Password & Confirm Password must match");
                valid=false;
            }
            else{
                layoutPassword.setErrorEnabled(false);
                layoutConfirmPassword.setErrorEnabled(false);
            }

            if(pHone.length()!=10){
                layoutPhone.setError("Invalid Phone Number");
            }
            else{
                layoutPhone.setErrorEnabled(false);
            }

        }
        if(!enteredDob){
            layoutDob.setError("DOB is required");
            valid=false;
        }
        else{
            layoutDob.setErrorEnabled(false);
        }

        if(textGender==null || textGender.equals("")){
            layoutGender.setError("required");
        }
        else{
            layoutGender.setErrorEnabled(false);
        }



        return valid;
    }

    void checkUniqueUserName(String textUserName){
        Call<ApiResponse> call = RetrofitClient.getInstance().getApi().checkUserName(textUserName);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.code()!=200){
                    uniqueUserName.setValue(new Boolean(false));
                }
                else{
                    uniqueUserName.setValue(new Boolean(true));
                    checkAllSet();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Snackbar.make(getView(),"Some error occured", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

    }

    private  void checkUniqueEmail(String textEmail){
        Call<ApiResponse> call = RetrofitClient.getInstance().getApi().checkEmail(textEmail);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.code()!=200){
                    uniqueEmail.setValue(new Boolean(false));
                }
                else{
                    uniqueEmail.setValue(new Boolean(true));
                    checkAllSet();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Snackbar.make(getView(),"Some error occured", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private  void checkUniquePhone(String textPhone){
        Call<ApiResponse> call = RetrofitClient.getInstance().getApi().checkPhone(textPhone);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.code()!=200){
                    uniquePhone.setValue(new Boolean(false));
                }
                else{
                    uniquePhone.setValue(new Boolean(true));
                    checkAllSet();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Snackbar.make(getView(),"Some error occured", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

    }

    private void setOnChangeListener(){
        uniqueUserName.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    layoutUserName.setError("Already Exists");
                    signUp.setEnabled(true);
                }
                else{
                    checkAllSet();
                    layoutUserName.setErrorEnabled(false);
                }
            }
        });

        uniqueEmail.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    layoutEmail.setError("Already Exists");
                    signUp.setEnabled(true);
                }
                else{
                    layoutEmail.setErrorEnabled(false);
                    checkAllSet();
                }
            }
        });

        uniquePhone.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    layoutPhone.setError("Already Exists");
                    signUp.setEnabled(true);
                }
                else{
                    layoutPhone.setErrorEnabled(false);
                    checkAllSet();
                }
            }
        });
    }

    void setSignUpListener(){

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp.setEnabled(false);
                fName = firstName.getText().toString().trim();
                lName = lastName.getText().toString().trim();
                uName = userName.getText().toString().trim();
                eMail = email.getText().toString().trim();
                pHone = phone.getText().toString().trim();
                pAssword = password.getText().toString().trim();
                cPassword = confirmPassword.getText().toString().trim();


                if(validateInput()){
                    checkUniqueUserName(uName);
                    checkUniqueEmail(eMail);
                    checkUniquePhone(pHone);
                }
                else{
                    signUp.setEnabled(true);
                }
            }
        });

    }

    //function to check if all values are set
    private void checkAllSet(){
        if(uniqueUserName.getValue()!=null && uniqueUserName.getValue() &&
            uniquePhone.getValue()!=null && uniquePhone.getValue() && uniqueEmail.getValue()!=null
                && uniqueEmail.getValue() ){
            allChecked.setValue(new Boolean(true));
        }
    }
}
