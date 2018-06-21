package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import com.dao.UserDAO;
import com.model.User;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;
import android.graphics.Color;
import static android.widget.Toast.LENGTH_SHORT;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;

public class LoginActivity extends AppCompatActivity {
    private FormHelper helperobj;
    private UserDAO userdaobj;
    public static User userobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        Button loginButton = (Button) findViewById(R.id.main_login_button);
        TextView textView = (TextView) findViewById(R.id.main_login_newuser);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        TextView main_app_user = (TextView) findViewById(R.id.main_app_user);
        main_app_user.setPaintFlags(main_app_user.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView main_login_guest = (TextView) findViewById(R.id.main_login_guest);
        main_login_guest.setPaintFlags(main_login_guest.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFEF4444")));

        helperobj = new FormHelper(this);
        userdaobj = new UserDAO(this);

        loginButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            userobj = helperobj.helperUser();
            if(userdaobj.validate(userobj)) {
                Intent intentToGo = new Intent(LoginActivity.this, ProductsWithDiscount.class);
                startActivity(intentToGo);
            }else{
                Toast.makeText(getApplicationContext(), "Invalid User Account", Toast.LENGTH_SHORT).show();
                onResume();
            }
            }
        });

        TextView newUser= (TextView) findViewById(R.id.main_login_newuser);
        newUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intentToGo = new Intent(LoginActivity.this, UserRegisterActivity.class);
                    startActivity(intentToGo);

                }
                return false;
            }
        });

        TextView guest= (TextView) findViewById(R.id.main_login_guest);
        guest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            Intent intentToGo = new Intent(LoginActivity.this, ProductGuest.class);
                            startActivity(intentToGo);
                        }


                return false;
            }
        });

    }
 /*   public void txt_msg(View view){
        View newUser = (View) findViewById(R.id.main_login_newuser);
        newUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentToGo = new Intent(LoginActivity.this, UserRegisterActivity.class);
                startActivity(intentToGo);
            }
        });
        View guest = (View) findViewById(R.id.main_login_guest);
        guest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentToGo = new Intent(LoginActivity.this, ProductGuest.class);
                startActivity(intentToGo);
            }
        });
        ///Toast.makeText(getApplicationContext(), "New User Clicked", Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    protected void onResume() {
        EditText email = (EditText) findViewById(R.id.main_user_email);
        EditText pass = (EditText) findViewById(R.id.main_password);
        email.getText().clear();
        pass.getText().clear();
        super.onResume();
    }
}
