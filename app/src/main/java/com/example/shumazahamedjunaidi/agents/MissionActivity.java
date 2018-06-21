package com.example.shumazahamedjunaidi.agents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dao.AgentsDAO;
import com.model.Mission;
import com.model.User;

import java.util.Calendar;

public class MissionActivity extends AppCompatActivity {
    FormHelperMission formHelperMission;
    public static int recordId;
    public static int missionID;
    int year,month,day;
    static final int DIALOG_ID = 0;
    TextView dateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        formHelperMission = new FormHelperMission(this);
        recordId = 0;
        missionID = 0;
        Intent intent = getIntent();
        Mission user = (Mission) intent.getSerializableExtra("missionSelect");
        if(user!=null){

            formHelperMission.fillForm(user);
            missionID = user.getId();
            Log.d("MissionActivity name",user.getUserName());
            Log.d("MissionActivity date",user.getDate());

        }else{
            Intent intentRec = getIntent();
            int userID = (int) intentRec.getSerializableExtra("userID");
            recordId = userID;
        }

        dateView = (TextView) findViewById(R.id.dateText);
        Button saveButton = (Button) findViewById(R.id.missionDateButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
                /// Intent intentToGo = new Intent(MainActivity.this, FormActivity.class);
                /// startActivity(intentToGo);
                ///Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
    }
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, datepickerListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int yearVal, int monthVal, int dayVal) {
                year = yearVal + 1;
                month = monthVal;
                day = dayVal;
            dateView.setText(year+"/"+month+"/"+day);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.savemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.menu_form_ok:
                AgentsDAO dataCon = new AgentsDAO(this);
                dataCon.dbinsertMission(formHelperMission.helperUser());
                dataCon.close();
                Toast.makeText(MissionActivity.this, "Mission Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
