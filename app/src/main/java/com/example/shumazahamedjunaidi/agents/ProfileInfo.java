package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import com.dao.AgentsDAO;
import com.model.User;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.Manifest;
import android.widget.Toast;

public class ProfileInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("profile");
        Log.d("name",user.getUserName());
        ImageView imageView = (ImageView) findViewById(R.id.add_agent_imageView);
        TextView name = (TextView) findViewById(R.id.nameTxt);
        TextView level = (TextView) findViewById(R.id.levelTxt);
        TextView agency = (TextView) findViewById(R.id.agencyTxt);
        TextView web = (TextView) findViewById(R.id.websiteTxt);
        TextView country = (TextView) findViewById(R.id.countryTxt);
        TextView phone = (TextView) findViewById(R.id.phoneTxt);
        TextView address = (TextView) findViewById(R.id.addressTxt);

        byte[] image = user.getImageView();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imageView.setImageBitmap(bitmap);

        name.setText(user.getUserName());
        level.setText(user.getLevel());
        agency.setText(user.getAgency());
        web.setText(user.getWebsite());
        country.setText(user.getCountry());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());

        ImageView cam = (ImageView) findViewById(R.id.cam);
        cam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentInfo = new Intent(ProfileInfo.this,CameraClass.class);
                intentInfo.putExtra("cameraprofile",user);
                startActivity(intentInfo);
            }
        });


        ImageView sms = (ImageView) findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("smsto:" + user.getPhone());

                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                smsIntent.putExtra("sms_body", "Hello "+user.getUserName());

                startActivity(smsIntent);
            }
        });


        ImageView info = (ImageView) findViewById(R.id.call);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(ProfileInfo.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProfileInfo.this,new String[]{Manifest.permission.CALL_PHONE},123);

                }else{
                    Intent intent1 = new Intent(Intent.ACTION_CALL);
                    intent1.setData(Uri.parse("tel:"+user.getPhone()));
                    startActivity(intent1);
                }

            }
        });

        ImageView globe = (ImageView) findViewById(R.id.map);
        globe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse("geo:0,0?q="+user.getAddress()));

                startActivity(mapIntent);
            }
        });
        ImageView site = (ImageView) findViewById(R.id.site);
        site.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String site = user.getWebsite();
                if(!(user.getWebsite()).startsWith("http://")){
                    site = "http://"+ user.getWebsite();
                }
                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse(site));

                startActivity(mapIntent);
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