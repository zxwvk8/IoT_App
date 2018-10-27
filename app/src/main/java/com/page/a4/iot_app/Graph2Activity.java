package com.page.a4.iot_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Graph2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String recieveText;
    public static String str[] = new String[10];

    ArrayList<Entry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph2);
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


        /////////////////////////////////////////////////////////////////////////////////
        //그래프


        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        //MyClientTask myClientTask = new MyClientTask(MainActivity.editTextAddress.getText().toString(), Integer.valueOf(MainActivity.editTextPort.getText().toString()), MainActivity.messageText.getText().toString());
        //myClientTask.execute();
        recieveText = MainActivity.recieveText.getText().toString();

        str = recieveText.split("#");


        Log.d("그래프", recieveText);



        //------------------------------------------------------------------------------------------
        //코드

        for(int i=0; i<str.length ; i++){
            entries.add(new Entry(10f+ Float.valueOf(str[i]), i));
        }



        //-----------------실제 값 ArrayList /높이/
        /*

        for(int i=0; i< ; i++){
            entries.add(new Entry(10f+i, i));
        }


        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(16f, 6));
        entries.add(new Entry(5f, 7));
        entries.add(new Entry(3f, 8));
        entries.add(new Entry(7f, 10));
        entries.add(new Entry(9f, 11));
        */

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        //-------------------상단축 리스트  /상단가로/
        ArrayList<String> labels = new ArrayList<String>();


        for(int i = 0; i<str.length ; i++){
            labels.add((i+1) + "일");

        }
        /*


        labels.add("5월");
        labels.add("6월");
        labels.add("7월");
        labels.add("8월");
        labels.add("9월");
        labels.add("10월");
        labels.add("11월");
        labels.add("12월");
        */

        LineData data = new LineData(labels, dataset);

        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/

        lineChart.setData(data);
        lineChart.animateY(5000);










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
        getMenuInflater().inflate(R.menu.graph2, menu);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
            //Intent intent = new Intent(MainActivity.this, Graph1Activity.class);
            //startActivity(intent);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_voltGraph) {
            Intent intent = new Intent(getApplicationContext(), Graph1Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_voltTemp) {

        } else if (id == R.id.nav_dustGraph) {
            Toast.makeText(this, "현재화면 입니다.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_dustTemp) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
