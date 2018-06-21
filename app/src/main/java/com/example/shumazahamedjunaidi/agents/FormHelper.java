package com.example.shumazahamedjunaidi.agents;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.EditText;
import android.widget.ImageView;

import com.model.User;

import java.io.ByteArrayOutputStream;
import android.graphics.BitmapFactory;
/**
 * Created by shumazahamedjunaidi on 8/6/17.
 */

public class FormHelper {

    private final EditText userName;
    private final EditText level;
    private final EditText agency;
    private final EditText website;
    private final EditText country;
    private final EditText phone;
    private final EditText address;
    private final ImageView imageView;
    User user;
    FormHelper(Object loginActivity){
        //if(loginActivity instanceof AddAgents)
        {
            AddAgents logActivity = (AddAgents) loginActivity;
            userName = (EditText) logActivity.findViewById(R.id.add_agent_name);
            level = (EditText) logActivity.findViewById(R.id.add_agent_level);
            website = (EditText) logActivity.findViewById(R.id.add_agent_website);
            country = (EditText) logActivity.findViewById(R.id.add_agent_country);
            phone = (EditText) logActivity.findViewById(R.id.add_agent_phone);
            address = (EditText) logActivity.findViewById(R.id.add_agent_address);
            agency = (EditText) logActivity.findViewById(R.id.add_agent_gency);
            imageView = (ImageView) logActivity.findViewById(R.id.add_agent_imageView);

            user = new User();
        }/*else {
            LoginActivity logActivity = (LoginActivity) loginActivity;
            userName = (EditText) logActivity.findViewById(R.id.main_user_email);
            password = (EditText) logActivity.findViewById(R.id.main_password);
        }*/

    }

    public User helperUser(){

        user.setUserName(userName.getText().toString());
        user.setLevel(level.getText().toString());
        user.setWebsite(website.getText().toString());
        user.setCountry(country.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setAddress(address.getText().toString());
        user.setAgency(agency.getText().toString());
        user.setImageView(imageViewToByteArray(imageView));
        return user;
    }
    private byte[] imageViewToByteArray(ImageView imageview){
        Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    public void fillForm(User user) {
        userName.setText(user.getUserName());
        level.setText(user.getLevel());
        website.setText(user.getWebsite());
        country.setText(user.getCountry());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
        agency.setText(user.getAgency());
        setImgBitMap(user.getImageView());
    }

    public void setImgBitMap(byte[] arr){
        Bitmap bmp = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        imageView.setImageBitmap(bmp);
    }
}
