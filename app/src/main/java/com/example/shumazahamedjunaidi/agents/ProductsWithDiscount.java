package com.example.shumazahamedjunaidi.agents;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import com.dao.UserDAO;

public class ProductsWithDiscount extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_with_discount);
        //View email = (View) findViewById(R.id.app_info);
        TextView email=(TextView)findViewById(R.id.app_info);
        //email.setText(LoginActivity.userobj.getUserEmail());
        //email.setText(LoginActivity.userobj.getUserEmail());
        email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFEF4444")));


        String[] products = {"PRODUCT1","PRODUCT2","PRODUCT3","PRODUCT4","PRODUCT5"};

        ListView productList = (ListView) findViewById(R.id.discount_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,products);
        productList.setAdapter(adapter);
        Button backButton = (Button) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /// Intent intentToGo = new Intent(MainActivity.this, FormActivity.class);
                /// startActivity(intentToGo);
               // Toast.makeText(getApplicationContext(), "Log Out"+LoginActivity.userobj.getUserEmail(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

