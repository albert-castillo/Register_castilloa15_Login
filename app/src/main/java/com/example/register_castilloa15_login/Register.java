package com.example.register_castilloa15_login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    EditText first,last,date,email,pass;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        first=(EditText)findViewById(R.id.firstinfo);
        last=(EditText)findViewById(R.id.lastinfo);
        date=(EditText)findViewById(R.id.birthinfo);
        email=(EditText)findViewById(R.id.emailinfo);
        pass=(EditText)findViewById(R.id.passinfo);
        confirm =(Button)findViewById(R.id.btn_calc);

        //part of the blank TextWatcher variable declairation
        first.addTextChangedListener(loginTextWatcher);
        last.addTextChangedListener(loginTextWatcher);
        date.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);
        pass.addTextChangedListener(loginTextWatcher);

        //part of the date of birth TextWatcher variable declairation
        date.addTextChangedListener(tw);
    }

    //below validates the email address

    private TextWatcher tw = new TextWatcher() {
        private  String current = "";
        private String mmddyyyy = "MMDDYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + mmddyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers and confirms accuracy, fixing it if it isn't
                    int mon  = Integer.parseInt(clean.substring(0,2));
                    int day  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    //sets month and changes month if the number is less then 1 or greater then 12
                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon-1);

                    //sets the year for the line below to work correctly and accounts for leap year
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);


                    //formats the output to look like a date after validating all the numbers
                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",mon, day, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                date.setText(current);
                date.setSelection(sel < current.length() ? sel : current.length());


            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    //below checks for blanks, if it finds any it will leave the button unclickable

    private TextWatcher loginTextWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String test1 = first.getText().toString();
            String test2 = last.getText().toString();
            String test3 = date.getText().toString();
            String test4 = email.getText().toString();
            String test5 = pass.getText().toString();

            confirm.setEnabled(!test1.isEmpty() && !test2.isEmpty()&& !test3.isEmpty()&& !test4.isEmpty()&& !test5.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    //email validation
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void goHome(View view) {
        String test1 = first.getText().toString();
        String test2 = last.getText().toString();
        String test3 = date.getText().toString();
        String test4 = email.getText().toString();
        String test5 = pass.getText().toString();

        //error message if the first name is too short, the maxlength validation is done in xml
        if(test1.length()<=2){
            Toast.makeText(this,"First name must be greater then 2 or less then 31",Toast.LENGTH_LONG).show();
        }

        //error message if the last name is too short, the maxlength validation is done in xml
        else if(test2.length()<=2){
            Toast.makeText(this,"Last name must be greater then 2 or less then 31",Toast.LENGTH_LONG).show();
        }

        //calls the isEmailValid method to validate email
        else if(!isEmailValid(test4)){
            Toast.makeText(this,"Email is Invalid",Toast.LENGTH_LONG).show();
        }

        //Date of Birth doesn't need to be validated because the TextWatcher is forcing the user to enter correct information

        else {

            //if everything validates properly then it forwards info to login screen so user can log in using their email and password
            Intent intent = new Intent(Register.this,LogIn.class);
            intent.putExtra("EMAIL",test4);
            intent.putExtra("PASSWORD",test5);
            startActivity(intent);

            //moves user to the HomeActivity Activity
            startActivity(new Intent(Register.this, HomeActivity.class));

        }
    }
}
