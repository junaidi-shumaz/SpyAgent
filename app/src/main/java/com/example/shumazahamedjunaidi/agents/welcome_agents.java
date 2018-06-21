package com.example.shumazahamedjunaidi.agents;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.content.FileProvider;
import com.dao.AgentsDAO;
import com.model.Agents;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import android.os.Environment;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;
import android.provider.MediaStore;
import android.content.ContentResolver;
import static java.io.File.createTempFile;
import android.content.res.Resources;
import java.io.PrintWriter;
import java.util.Random;
import android.provider.MediaStore.Images;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.graphics.Bitmap.CompressFormat;
import com.model.User;
/**
 * Created by shumazahamedjunaidi on 8/9/17.
 */

public class welcome_agents extends AppCompatActivity {

    int[] ACTION_IMAGE = {R.drawable.agentfemale,R.drawable.search,R.drawable.add,R.drawable.list,R.drawable.search,R.drawable.add};
    String[] ACTION_NAME ={"Agent List","Agent Search","Agent Add","Agent List","Agent Search","Agent Add"};
    String[] LEVEL_NAME ={"Agent List","Agent Search","Agent Add","Agent List","Agent Search","Agent Add"};
    List<User> agents;
    ListView welcomeList;
    private BroadcastReceiver receiver;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.smsdone,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){

            case R.id.menu_form_gallery:
                Intent intent = new Intent(welcome_agents.this,ReceiveAnsShowSMS.class);
                startActivity(intent);

                break;
            case R.id.menu_form_ok:
                finish();

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final User ag = (User) welcomeList.getItemAtPosition(info.position);



        MenuItem itemmission = menu.add("Mission");
        itemmission.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent = new Intent(welcome_agents.this,ListMission.class);
                intent.putExtra("mission",ag);
                startActivity(intent);


                return false;
            }
        });

        MenuItem itemInfo = menu.add("Profile Info");
        itemInfo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intentInfo = new Intent(welcome_agents.this,ProfileInfo.class);
                intentInfo.putExtra("profile",ag);
                startActivity(intentInfo);


                return false;
            }
        });

        MenuItem itemDelete = menu.add("Delete");
        itemDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AgentsDAO dataCon = new AgentsDAO(welcome_agents.this);
                dataCon.deleteRecord(ag);
                Toast.makeText(getApplicationContext(), "Record Deleted", Toast.LENGTH_SHORT).show();
                onResume();
                return false;
            }
        });


  /*
        MenuItem itemsms = menu.add("sms");
        itemsms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                {

                    byte[] image = ag.getImageView();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                    // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    //bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    //byte[] byteArray = byteArrayOutputStream.toByteArray();

                    try {
                        String fname = "Image.txt";

                        //PictUtil.saveToCacheFile(bitmap);
                        // SaveImage(bitmap);
                        //   readFile();
                        /// Uri uri = getImageUri(getApplicationContext(),bitmap);



                        // String result = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");
                        //Uri imageFileUri=Uri.parse("file://"+Environment.getExternalStorageDirectory()+"/cache.png");
                        File cachePath = new File(getApplicationContext().getCacheDir(), "images");
                        cachePath.mkdirs(); // don't forget to make the directory
                        FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        stream.close();


                        File imagePath = new File(getApplicationContext().getCacheDir(), "images");
                        File newFile = new File(imagePath, "image.png");
                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.shumazahamedjunaidi.agents.fileprovider", newFile);
                        ArrayList<Uri> uriArrayList = new ArrayList<Uri>();
                        uriArrayList.add(contentUri);
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                        shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));

                        shareIntent.putExtra(Intent.EXTRA_STREAM, uriArrayList);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "New if you are sending text");
                        startActivity(Intent.createChooser(shareIntent, "Choose an app"));

                        // Uri imageFileUri = Uri.parse(PictUtil.getCacheFilename());
                        Toast.makeText(getApplicationContext(), "Log new"+contentUri.getPath(), Toast.LENGTH_SHORT).show();

                        Uri phototUri = Uri.parse("android.resource://com.example.shumazahamedjunaidi.agents/drawable/"+R.drawable.agentfemale);
                        Uri phototUri1 = Uri.parse("android.resource://com.example.shumazahamedjunaidi.agents/drawable/"+R.drawable.lamb1);

                        //Intent sharingIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                        //uriArrayList.add(imageFileUri);
                        //uriArrayList.add(phototUri1);
                        //sharingIntent.setType("image/*");
                        //sharingIntent.putExtra(Intent.EXTRA_STREAM, uriArrayList);

                        //sharingIntent.putExtra(Intent.EXTRA_TEXT, "N ew if you are sending text");

                        //startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                        //startActivity(sharingIntent);


             /*       String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/saved_images/Image.jpg");

                     String localAbsoluteFilePath = myDir.getAbsolutePath();
                            //saveImageLocally(bitmap);
                    Toast.makeText(getApplicationContext(), "Log Out 1"+localAbsoluteFilePath, Toast.LENGTH_SHORT).show();



                    Toast.makeText(getApplicationContext(), "Log Out 2 "+localAbsoluteFilePath, Toast.LENGTH_SHORT).show();



                    if (localAbsoluteFilePath!=null && localAbsoluteFilePath!="") {


                        Uri phototUri = Uri.parse(localAbsoluteFilePath);
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                        ///Uri uri = Uri.fromFile(file);
                        sharingIntent.putExtra("address", "1213123123");
                        sharingIntent.putExtra("sms_body", "N ew if you are sending text");
                        //sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
                        //sharingIntent.setData(phototUri);
                        sharingIntent.setType("image/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, phototUri);
                        //startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                        startActivity(sharingIntent);
                    }

/*
                 ///   File tempDir = Environment.getExternalStorageDirectory();
                  //  tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
                    //tempDir.mkdir();
                   // Toast.makeText(getApplicationContext(), "Log Out 1", Toast.LENGTH_SHORT).show();

                    ///File tempFile = File.createTempFile("tmp", ".png", outputDir);
                    //Toast.makeText(getApplicationContext(), "Log Out 3", Toast.LENGTH_SHORT).show();

                    //write the bytes in file
                    File outputDir = Utils.getAlbumStorageDir(Environment.DIRECTORY_DOWNLOADS);

                    File tempFile = new File(outputDir.getAbsolutePath()+"/img.png");

                    FileOutputStream fos = new FileOutputStream(tempFile);
                    Toast.makeText(getApplicationContext(), "Log Out 1"+tempFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.close();
                    //fos.write(byteArray);
                    //fos.flush();
                    //fos.close();

                    //  Toast.makeText(getApplicationContext(), "Log Out 2", Toast.LENGTH_SHORT).show();

                    //Intent sendIntent = new Intent(Intent.ACTION_SEND);
                //    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                 //   sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");

                  //  sendIntent.putExtra("address", "1213123123");
                   /// sendIntent.putExtra("sms_body", "if you are sending text");

                   // sendIntent.setData(Uri.parse("sms:6475445568"));
                   // sendIntent.setData(Uri.parse("sms_body:fffffff"));
                    //sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile)); // imageUri set previously

                    //sendIntent.setType("image/png");

                    ///startActivity(sendIntent);


                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                    Uri screenshotUri = Uri.parse(tempFile.getAbsolutePath());

                    sharingIntent.putExtra("address", "1213123123");
                    sharingIntent.putExtra("sms_body", "N ew if you are sending text");
                    //sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
                    //sharingIntent.setData(screenshotUri);
                    sharingIntent.setType("image/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                    //startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                    startActivity(sharingIntent);

                   // Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });
        */
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionagents);
        init();


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();


    }

    public void init(){
        AgentsDAO dataCon = new AgentsDAO(this);
        agents = dataCon.readAgent();

        welcomeList = (ListView) findViewById(R.id.welcome_list_view);
        registerForContextMenu(welcomeList);
        CustomAdapter customAdapter = new CustomAdapter();
        welcomeList.setAdapter(customAdapter);
        welcomeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) agents.get(i);
                Intent intent = new Intent(welcome_agents.this,AddAgents.class);
                Log.d("name",user.getUserName());
                Log.d("level",user.getLevel());
                intent.putExtra("user",user);
                startActivity(intent);
                //finish();
            }
        });

        dataCon.close();
    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return agents.size();
        }

        @Override
        public Object getItem(int i) {
            return agents.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.welcome_custom_listview,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.custom_action_image);
            //TextView name = (TextView) view.findViewById(R.id.custom_action_view_name);
            TextView level = (TextView) view.findViewById(R.id.custom_action_view_level);

            ///imageView.setImageResource(ACTION_IMAGE[i]);
            imageView.setImageBitmap(imageShow(i));
            //name.setText(ACTION_NAME[i]);
            level.setText(agentName(i));
            return view;
        }


        public Bitmap imageShow(int i){
            User ag = agents.get(i);
            byte[] image = ag.getImageView();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            return bitmap;
        }
        public String agentName(int i){
            User ag = agents.get(i);
            return ag.getUserName();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, 1);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==1){//response for SMS permission request
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //What to do if User allowed SMS permission
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.provider.Telephony.SMS_RECEIVED");
                //Extends BroadcastReceiver
                receiver = new SmsReceiver();
                registerReceiver(receiver,filter);
            }else{
                //What to do if user disallowed requested SMS permission
            }
        }
    }
}


class PermissionManager {
    //A method that can be called from any Activity, to check for specific permission
    public static void check(Activity activity, String permission, int requestCode){
        //If requested permission isn't Granted yet
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
        }
    }
}


