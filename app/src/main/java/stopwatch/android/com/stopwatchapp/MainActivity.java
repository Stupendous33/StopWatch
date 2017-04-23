package stopwatch.android.com.stopwatchapp;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     TextView save_time;
     Button  start,reset,pause,save;
     long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

     Handler handler;

     int Seconds, Minutes, MilliSeconds ;

      ListView listView ;

      String[] ListElements = new String[] {  };

      List<String> ListElementsArrayList ;

      ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_time=(TextView)findViewById(R.id.save_t);
        start=(Button)findViewById(R.id.start_time);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        reset=(Button)findViewById(R.id.reset_timer);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                save_time.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });
        pause=(Button)findViewById(R.id.pause_timer);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });
        save=(Button)findViewById(R.id.save_timer);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(save_time.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });



        handler = new Handler() ;
            listView = (ListView) findViewById(R.id.saved_list);
            ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
            adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    ListElementsArrayList
            );


            listView.setAdapter(adapter);






    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            save_time.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

    }
