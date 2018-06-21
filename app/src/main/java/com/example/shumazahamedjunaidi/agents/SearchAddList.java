package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SearchAddList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_list);
        TextView listAgents= (TextView) findViewById(R.id.page2_list_text);
        listAgents.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intentToGo = new Intent(SearchAddList.this, welcome_agents.class);
                    startActivity(intentToGo);

                }
                return false;
            }
        });


        TextView addAgents= (TextView) findViewById(R.id.page2_add_text);
        addAgents.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intentToGo = new Intent(SearchAddList.this, AddAgents.class);
                    startActivity(intentToGo);

                }
                return false;
            }
        });
    }
}
