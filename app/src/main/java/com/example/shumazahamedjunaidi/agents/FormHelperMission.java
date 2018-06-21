package com.example.shumazahamedjunaidi.agents;

import android.widget.EditText;
import android.widget.ImageView;

import com.model.Mission;
import com.model.User;
import android.widget.TextView;
/**
 * Created by shumazahamedjunaidi on 8/15/17.
 */

public class FormHelperMission {
    private final EditText userName;
    private final TextView date;
    private final EditText status;
    Mission user;
    FormHelperMission(Object loginActivity){
        //if(loginActivity instanceof AddAgents)
        {
            MissionActivity logActivity = (MissionActivity) loginActivity;
            userName = (EditText) logActivity.findViewById(R.id.MissionName);
            date = (TextView) logActivity.findViewById(R.id.dateText);
            status = (EditText) logActivity.findViewById(R.id.missionStatus);

            user = new Mission();
        }

    }
    public Mission helperUser(){

        user.setUserName(userName.getText().toString());
        user.setDate(date.getText().toString());
        user.setStatus(status.getText().toString());
         return user;
    }
    public void fillForm(Mission user) {
        userName.setText(user.getUserName());
        date.setText(user.getDate());
        status.setText(user.getStatus());


    }
}
