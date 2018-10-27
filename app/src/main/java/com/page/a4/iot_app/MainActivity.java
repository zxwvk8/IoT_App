package com.page.a4.iot_app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Socket;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView recieveText;
    public static EditText editTextAddress, editTextPort; //messageText;
    public static Button connectBtn, clearBtn;
    public static Button open_voltGraph, open_voltTemp,
                        open_dustGraph, open_dustTemp;

    Socket socket = null;

    String array[] = new String[10];


    ///////////////////////////////////////////////////
    // DB관련
    String str_id = "";
    String str_password = "";
    String str_name = "";
    String str_gender = "";
    String str_post = "";




    SQLiteDatabase sqlitedb;
    DBManager dbmanager;


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {  // 실행이 끝난후 확인 가능

        }
    };




/////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //앱 기본 스타일 설정
        getSupportActionBar().setElevation(0);








        connectBtn = (Button) findViewById(R.id.buttonConnect);
        clearBtn = (Button) findViewById(R.id.buttonClear);
        editTextAddress = (EditText) findViewById(R.id.addressText);
        editTextPort = (EditText) findViewById(R.id.portText);
        recieveText = (TextView) findViewById(R.id.textViewReciev);
        //messageText = (EditText) findViewById(R.id.messageText);
        open_voltGraph = (Button)findViewById(R.id.button_volt_graph);
        open_dustGraph = (Button)findViewById(R.id.button_dust_graph);


        //connect 버튼 클릭
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RA0# 송신

                recieveText.setText("");
                final String str = (String)getText(R.string.main_sendMessage);

                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                myClientTask.execute();
                //messageText.setText("");
                //messageText.getText();

                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                    @Override
                    public void run() {
                        // 실행할 동작 코딩

                        Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str + "\n수신:" + recieveText.getText().toString(), Toast.LENGTH_SHORT).show();

                        mHandler.sendEmptyMessage(0); // 실행이 끝난후 알림
                    }
                }, 1000);

            }

        });

        //clear 버튼 클릭
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieveText.setText("");
                //messageText.setText("");
            }
        });


        open_voltGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieveText.setText("");
                //RA1# 송신
                final String str = (String)getText(R.string.volt_sendMessage);

                //통신
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                myClientTask.execute();
                //messageText.setText("");
                //messageText.getText();





                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                    @Override
                    public void run() {
                        // 실행할 동작 코딩
                        Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str +"\n수신:" + recieveText.getText().toString(), Toast.LENGTH_SHORT).show();

                        if(recieveText.getText().toString().length() >= 1){
                            //통신 후 그래프액티비티화면이동
                            Intent intent = new Intent(MainActivity.this, Graph1Activity.class);
                            startActivity(intent);
                        }


                        mHandler.sendEmptyMessage(0); // 실행이 끝난후 알림
                    }
                }, 1000);







            }
        });

        open_dustGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieveText.setText("");
                //RA2# 송신
                final String str = (String) getText(R.string.dust_sendMessage);

                //통신
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                myClientTask.execute();
                //messageText.setText("");
                //messageText.getText();


                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str + "\n수신:" + recieveText.getText().toString(), Toast.LENGTH_SHORT).show();

                        if (recieveText.getText().toString().length() >= 1) {
                            //통신 후 그래프액티비티화면이동
                            Intent intent = new Intent(MainActivity.this, Graph2Activity.class);
                            startActivity(intent);
                        }

                        mHandler.sendEmptyMessage(0); // 실행이 끝난후 알림
                    }
                }, 1000);

            }
        });
    }

    //



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
            //Intent intent = new Intent(MainActivity.this, Graph1Activity.class);
            //startActivity(intent);
            Toast.makeText(this, "현재화면 입니다.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_voltGraph) {
            Intent intent = new Intent(getApplicationContext(), Graph1Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_voltTemp) {

        } else if (id == R.id.nav_dustGraph) {
            Intent intent = new Intent(getApplicationContext(), Graph2Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_dustTemp) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
