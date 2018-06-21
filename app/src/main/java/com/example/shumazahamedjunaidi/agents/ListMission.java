package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dao.AgentsDAO;
import com.model.User;

import java.util.List;
import com.model.Mission;
public class ListMission extends AppCompatActivity {

    ListView listMission;
    List<Mission> missions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mission);
        init();
    }


    protected void onResume() {
        init();
        super.onResume();

    }

    public void init(){

        AgentsDAO dataCon = new AgentsDAO(this);

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("mission");
        missions = dataCon.readMission(user);
        listMission = (ListView) findViewById(R.id.students_lists);
        String[] mList = new String[missions.size()];
        for(int i=0;i<missions.size();i++){
            mList[i] = missions.get(i).getUserName();
            Log.d("List Mission Date",missions.get(i).getDate());
            Log.d("List Mission Name",missions.get(i).getUserName());
            Log.d("List Mission ID",String.valueOf(missions.get(i).getId()));

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,mList);
        listMission.setAdapter(adapter);
        registerForContextMenu(listMission);
        Button saveButton = (Button) findViewById(R.id.student_add);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentToGo = new Intent(ListMission.this, MissionActivity.class);
                intentToGo.putExtra("userID",user.getId());
                startActivity(intentToGo);
                //Toast.makeText(getApplicationContext(), "Log Out"+user.getId(), Toast.LENGTH_SHORT).show();
               // finish();
            }
        });

        listMission.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Mission str = (Mission) missions.get(i);
                Intent intent = new Intent(ListMission.this,MissionActivity.class);
                intent.putExtra("missionSelect",str);
                startActivity(intent);
            }
        });
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
}
