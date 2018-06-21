package com.example.shumazahamedjunaidi.agents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        String[] students = {"Marcos","John","Alfred"};

        studentList = (ListView) findViewById(R.id.students_lists);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,students);
        studentList.setAdapter(adapter);
        registerForContextMenu(studentList);
        Button saveButton = (Button) findViewById(R.id.student_add);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               /// Intent intentToGo = new Intent(MainActivity.this, FormActivity.class);
               /// startActivity(intentToGo);
               Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String str = (String) studentList.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this,FormActivity.class);
                intent.putExtra("student",str);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final String str = (String) studentList.getItemAtPosition(info.position);
        MenuItem itemWeb = menu.add("website");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String site = "http://www.google.com";
        intent.setData(Uri.parse(site));
        itemWeb.setIntent(intent);


        MenuItem itemsms = menu.add("sms");
        Intent intentsms = new Intent(Intent.ACTION_VIEW);

        intentsms.setData(Uri.parse("sms:6475445568"));
        itemsms.setIntent(intentsms);

        MenuItem itemcall = menu.add("Call");
        itemcall.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);
                }else{
                    Intent intentcall = new Intent(Intent.ACTION_CALL);
                    intentcall.setData(Uri.parse("tel:6475445568"));
                    startActivity(intentcall);
                }

                return false;
            }
        });
                super.onCreateContextMenu(menu, v, menuInfo);
    }
}
