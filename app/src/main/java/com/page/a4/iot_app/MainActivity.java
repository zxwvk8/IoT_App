package com.page.a4.iot_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.net.Socket;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView recieveText, tv_switch;
    public static com.beardedhen.androidbootstrap.BootstrapEditText editTextAddress, editTextPort; //messageText;

    public static com.beardedhen.androidbootstrap.BootstrapButton open_voltGraph, open_voltTemp,
                        open_dustGraph, open_dustTemp;
    public static Switch airCleaner;
    public static com.beardedhen.androidbootstrap.BootstrapButton Start_AC, Stop_AC;



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
        Fabric.with(this, new Crashlytics());
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










        editTextAddress = (com.beardedhen.androidbootstrap.BootstrapEditText) findViewById(R.id.addressText);
        editTextPort = (com.beardedhen.androidbootstrap.BootstrapEditText) findViewById(R.id.portText);
        recieveText = (TextView) findViewById(R.id.textViewReciev);
        //messageText = (EditText) findViewById(R.id.messageText);
        open_voltGraph = (com.beardedhen.androidbootstrap.BootstrapButton) findViewById(R.id.button_volt_graph);
        open_dustGraph = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.button_dust_graph);
        airCleaner = (Switch)findViewById(R.id.airCleaner_switch);
        airCleaner.setChecked(false);
        Start_AC = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.start_AC);
        Stop_AC = (com.beardedhen.androidbootstrap.BootstrapButton)findViewById(R.id.stop_AC);
        tv_switch = (TextView)findViewById(R.id.tv_switch);




        open_voltGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieveText.setText("");
                //RA1# 송신
                final String str = (String)getText(R.string.volt_sendMessage_1m);

                //통신
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                myClientTask.execute();
                //messageText.setText("");
                //messageText.getText();





                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                    @Override
                    public void run() {
                        // 실행할 동작 코딩
                        //Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str +"\n수신:" + recieveText.getText().toString(), Toast.LENGTH_SHORT).show();

                        if(recieveText.getText().toString().length() >= 1){
                            //통신 후 그래프액티비티화면이동
                            Intent intent = new Intent(MainActivity.this, Graph1Activity.class);
                            startActivity(intent);
                        }


                        mHandler.sendEmptyMessage(0); // 실행이 끝난후 알림
                    }
                }, 2000);







            }
        });

        open_dustGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieveText.setText("");
                //RA2# 송신
                final String str = (String) getText(R.string.dust_sendMessage_1m);

                //통신
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                myClientTask.execute();
                //messageText.setText("");
                //messageText.getText();


                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                    @Override
                    public void run() {

                        //Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str + "\n수신:" + recieveText.getText().toString(), Toast.LENGTH_SHORT).show();

                        if (recieveText.getText().toString().length() >= 1) {
                            //통신 후 그래프액티비티화면이동
                            Intent intent = new Intent(MainActivity.this, Graph2Activity.class);
                            startActivity(intent);
                        }

                        mHandler.sendEmptyMessage(0); // 실행이 끝난후 알림
                    }
                }, 2000);

            }
        });



        airCleaner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(airCleaner.isChecked()){


                    //OFF!! MANU
                    final String str = "Manu#";

                    MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                    myClientTask.execute();

                    //airCleaner.toggle();
                    tv_switch.setText("공기청정기 수동");
                    //airCleaner.setTextOn("공기청정기 수-동");
                    //airCleaner.setVisibility(View.VISIBLE);

                    //버튼 enable~~~~~~~~~~~~~~~
                    Start_AC.setEnabled(true);
                    Stop_AC.setEnabled(true);



                } else {

                    //ON!! AUTO!!
                    //통신
                    final String str = "Auto#";

                    MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str);
                    myClientTask.execute();
                    //messageText.setText("");
                    //messageText.getText();

                    //airCleaner.toggle();
                    tv_switch.setText("공기청정기 자동");
                    //airCleaner.setVisibility(View.VISIBLE);



                    //버튼 disable~~~~~~~~~~~~~~~
                    Start_AC.setEnabled(false);
                    Stop_AC.setEnabled(false);
                }
            }
        });



        Start_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_startAC = "Start_AC";
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str_startAC);
                myClientTask.execute();
                //Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str_startAC, Toast.LENGTH_SHORT).show();

            }
        });

        Stop_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_stopAC = "Close_AC";
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), str_stopAC);
                myClientTask.execute();
                //Toast.makeText(MainActivity.this, "연결테스트\n송신:" + str_stopAC, Toast.LENGTH_SHORT).show();

            }
        });



    }

    //

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }



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
            //테스트 텍스트값
            //recieveText.setText("RA0#");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();     //닫기
                }
            });
            alert.setMessage("A4 Team\n송준호\n양진열\n유석원\n임정연");
            alert.show();




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

            if(recieveText.getText().toString().length() >= 3){
                Intent intent = new Intent(getApplicationContext(), Graph1Activity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "메인화면에서 그래프 버튼을 눌러 데이터값을 받으세요", Toast.LENGTH_SHORT).show();
            }




        } else if (id == R.id.nav_dustGraph) {
            if(recieveText.getText().toString().length() >= 3) {
                Intent intent = new Intent(getApplicationContext(), Graph2Activity.class);
                startActivity(intent);
            } else {
            Toast.makeText(this, "메인화면에서 그래프 버튼을 눌러 데이터값을 받으세요", Toast.LENGTH_SHORT).show();
        }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
