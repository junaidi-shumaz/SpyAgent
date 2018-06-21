package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dao.AgentsDAO;
import com.model.SMSCLASS;
import com.model.User;

import java.util.List;

public class SmsDetailsPerAgent extends AppCompatActivity {
    ListView listsms;
    List<String> sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_details_per_agent);
        AgentsDAO dataCon = new AgentsDAO(this);

        Intent intent = getIntent();
        String number  = (String) intent.getSerializableExtra("smsDetails");

        sms = dataCon.readComplteSMS(number);
        listsms = (ListView) findViewById(R.id.sms_list_view);
        CustomAdapterSMS customAdapter = new CustomAdapterSMS();
        listsms.setAdapter(customAdapter);
        registerForContextMenu(listsms);

        ///ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,sms);
        ///listsms.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.backbutton,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){

            case R.id.menu_form_ok:
                finish();

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    class CustomAdapterSMS extends BaseAdapter {

        @Override
        public int getCount() {
            return sms.size();
        }

        @Override
        public Object getItem(int i) {
            return sms.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.detailedmessage,null);
            TextView level = (TextView) view.findViewById(R.id.smsMsg);

            level.setText(agentName(i));

            return view;
        }
        public String agentName(int i){
            String ag = sms.get(i);
            return ag;
        }

    }
}
