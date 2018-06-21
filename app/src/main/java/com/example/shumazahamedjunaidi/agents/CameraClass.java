package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dao.AgentsDAO;
import com.dao.CameraDAO;

import java.io.ByteArrayOutputStream;
import com.dao.AgentsDAO;
import com.model.User;

public class CameraClass extends AppCompatActivity {
    ImageView imageView;
    EditText name;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_class);
        imageView = (ImageView) findViewById(R.id.cameraimage);
       // name = (EditText) findViewById(R.id.photographername);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("cameraprofile");

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,123);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.menu_form_ok:


                AgentsDAO dataCon = new AgentsDAO(this);
                dataCon.dbinsertPhotos(imageViewToByteArray(imageView));
                dataCon.close();
                Toast.makeText(CameraClass.this, "Photo Saved", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.camera);
                ///finish();
                break;
            case R.id.menu_form_gallery:
                Intent intent = new Intent(CameraClass.this,Camera_View.class);
                intent.putExtra("camerasms",user);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private byte[] imageViewToByteArray(ImageView imageview){
        Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }
}
