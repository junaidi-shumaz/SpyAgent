package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.dao.AgentsDAO;
import com.model.Mission;
import com.model.User;

import java.util.List;
import com.model.SMSCLASS;
public class ReceiveAnsShowSMS extends AppCompatActivity {

    ListView listsms;
    List<SMSCLASS> sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_ans_show_sms);
    }

    protected void onResume() {
        init();
        super.onResume();

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
    public void init() {

        AgentsDAO dataCon = new AgentsDAO(this);

        Intent intent = getIntent();
        sms = dataCon.readSMS();
        listsms = (ListView) findViewById(R.id.sms_list_view);
        String[] mList = new String[sms.size()];
        for (int i = 0; i < sms.size(); i++) {
            mList[i] = sms.get(i).getName();
            // Log.d("List Mission Name",sms.get(i).getName());

        }
        CustomAdapterSMS customAdapter = new CustomAdapterSMS();
        listsms.setAdapter(customAdapter);
        registerForContextMenu(listsms);

        listsms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ///SMSCLASS str = (SMSCLASS) sms.get(i);
                Intent intent = new Intent(ReceiveAnsShowSMS.this,SmsDetailsPerAgent.class);
                intent.putExtra("smsDetails",String.valueOf(sms.get(i).getNumber()));
                startActivity(intent);
            }
        });

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
            view = getLayoutInflater().inflate(R.layout.sms_custom_list,null);
            TextView level = (TextView) view.findViewById(R.id.custom_action_view_level);
            TextView level1 = (TextView) view.findViewById(R.id.smsTxt);
            TextView dateTime = (TextView) view.findViewById(R.id.datetime);

            level.setText(agentName(i));
            level1.setText(msgStatus(i));
            dateTime.setText(msgDateTime(i));
            return view;
        }
        public String msgDateTime(int i){
            SMSCLASS ag = sms.get(i);
            return ag.getTime();
        }
        public String agentName(int i){
            SMSCLASS ag = sms.get(i);
            return ag.getName();
        }
        public String msgStatus(int i){
            SMSCLASS ag = sms.get(i);

           /// Toast.makeText(getApplicationContext(), "Log Out 2"+ag.getStatus(), Toast.LENGTH_SHORT).show();

            if(ag.getStatus().equals("0"))
                return "New Msg";
            else
                return "";
        }
    }
}
