package com.example.shumazahamedjunaidi.agents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dao.AgentsDAO;
import com.dao.CameraDAO;
import com.model.Agents;
import com.model.CameraModel;
import com.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.widget.GridView;
import android.widget.Toast;
import android.graphics.Color;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import java.util.Collections;
import android.provider.ContactsContract;
public class Camera_View extends AppCompatActivity {


    List<CameraModel> cam;
    static ArrayList<Integer> counter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera__view);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("camerasms");

        init();
        counter =new ArrayList<Integer>();
    }


    public void init() {
        AgentsDAO dataCon = new AgentsDAO(this);
        cam = dataCon.readPhotos();
        final GridView galleryList = (GridView) findViewById(R.id.menu_form_gallery_list_view);

        galleryList.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Toast.makeText(getApplicationContext(), "item id-"+position, Toast.LENGTH_SHORT).show();

                synchronized (counter) {
                if (counter.contains(position)) {
                    //counter.remove(position);
                    View tv = (View) galleryList.getChildAt(position);
                    tv.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    counter.add(position);
                    View tv = (View) galleryList.getChildAt(position);
                    tv.setBackgroundColor(Color.RED);
                }
            }


            }
        });

        CustomAdapter customAdapter = new CustomAdapter();
        galleryList.setAdapter(customAdapter);
        dataCon.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cameraicons,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.menu_form_ok:
                smsFunction();
                finish();
                break;
            case R.id.done:
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void smsFunction() {

        ArrayList<Uri> uriArrayList = new ArrayList<Uri>();
        Uri contentUri = null;
        String PhotoId = "";
        try {
        for (int i = 0; i < counter.size(); i++) {

        CameraModel ag = cam.get(counter.get(i));
            PhotoId = PhotoId + " Photo " +ag.getId();
        byte[] image = ag.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        //byte[] byteArray = byteArrayOutputStream.toByteArray();



            //PictUtil.saveToCacheFile(bitmap);
            // SaveImage(bitmap);
            //   readFile();
            /// Uri uri = getImageUri(getApplicationContext(),bitmap);


            // String result = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");
            //Uri imageFileUri=Uri.parse("file://"+Environment.getExternalStorageDirectory()+"/cache.png");
            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            if(!cachePath.exists())
            cachePath.mkdirs(); // don't forget to make the directory

            String fileName = String.valueOf(ag.getId())+".png";

            FileOutputStream stream = new FileOutputStream(cachePath + "/"+fileName); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            File imagePath = new File(getApplicationContext().getCacheDir(), "images");
            File newFile = new File(imagePath, fileName);
            contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.shumazahamedjunaidi.agents.fileprovider", newFile);
            uriArrayList.add(contentUri);
        }

            counter.clear();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            ///shareIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "2323232323");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriArrayList);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Please look "+PhotoId+" they are important for your mission.");
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cam.size();
        }

        @Override
        public Object getItem(int i) {
            ///Toast.makeText(getApplicationContext(), "get item-"+i, Toast.LENGTH_SHORT).show();
            return cam.get(i);
        }

        @Override
        public long getItemId(int i) {
            //counter.add(i);

            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //Toast.makeText(getApplicationContext(), "view-"+i, Toast.LENGTH_SHORT).show();

            view = getLayoutInflater().inflate(R.layout.camera_gallery_view, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.custom_action_image);
            TextView nameimg = (TextView) view.findViewById(R.id.custom_action_view_level);


          /*  imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    /// Intent intentToGo = new Intent(MainActivity.this, FormActivity.class);
                    /// startActivity(intentToGo);
                    ImageView imageView1 = (ImageView) view.findViewById(R.id.custom_action_image);
                    imageView1.setBackgroundColor(Color.rgb(255, 255, 255));

                    Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();

                }
            });
*/

            ///imageView.setImageResource(ACTION_IMAGE[i]);
            imageView.setImageBitmap(imageShow(i));
            nameimg.setText(idShow(i));
            ///TextView textView = (TextView) findViewById(R.id.textView3);
            ///textView.setText(cam.get(i).getName());
            return view;
        }


        public Bitmap imageShow(int i) {
            CameraModel ag = cam.get(i);
            byte[] image = ag.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            return bitmap;
        }
        public String idShow(int i) {
            CameraModel ag = cam.get(i);
            int id = ag.getId();
            return String.valueOf(id);
        }
    }
}