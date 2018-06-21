package com.example.shumazahamedjunaidi.agents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dao.UserDAO;
import com.model.User;

import static android.widget.Toast.LENGTH_SHORT;

public class UserRegisterActivity extends AppCompatActivity {

    private FormHelper helper;
    private UserDAO userdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        Button saveButton = (Button) findViewById(R.id.reg_save_button);
        helper = new FormHelper(this);
        userdao = new UserDAO(this);


        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                User user = helper.helperUser();
              ///  if(user.getUserEmail().matches("") || user.getUserEmail().matches("") || user.getPassword().matches("") || user.getPassword().matches("")){
                //    Toast.makeText(UserRegisterActivity.this, "Provide Valid Input", LENGTH_SHORT).show();

                //}else{
                    userdao.dbinsert(user);
                 //   Toast.makeText(UserRegisterActivity.this, "User "+ user.getUserEmail() +"Created", LENGTH_SHORT).show();
                    finish();
                ///}

            }
        });
    }
}
