package com.example.shumazahamedjunaidi.agents;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductGuest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_guest);

        String[] products = {"PRODUCTA","PRODUCTB","PRODUCTC","PRODUCTD","PRODUCTE"};

        ListView productList = (ListView) findViewById(R.id.discount_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,products);
        productList.setAdapter(adapter);
        Button backButton = (Button) findViewById(R.id.back_button);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFEF4444")));

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /// Intent intentToGo = new Intent(MainActivity.this, FormActivity.class);
                /// startActivity(intentToGo);
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
