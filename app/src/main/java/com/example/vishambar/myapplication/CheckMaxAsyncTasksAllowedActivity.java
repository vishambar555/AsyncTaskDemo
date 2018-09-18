package com.example.vishambar.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class CheckMaxAsyncTasksAllowedActivity extends Activity implements View.OnClickListener {

    private static String TAG = "MyApp";
    private Button startBtn, stopByCancelBtn;
    private TextView tvDescription;
    private EditText etNoOfAsyncTasks;

    private AsyncTask<String, Integer, Object>[] myAsyncTask;
    private int noOfAsyncTasks;

    private RadioGroup radioGroup;
    private RadioButton rbThreadPoolExecutor, rbSerialExecutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_main_2);
        tvDescription = findViewById(R.id.tv_text);
        etNoOfAsyncTasks = findViewById(R.id.et_no_of_async_tasks);
        radioGroup = findViewById(R.id.rg);
        rbThreadPoolExecutor = findViewById(R.id.rb_thread_pool);
        rbSerialExecutor = findViewById(R.id.rb_serial);

        startBtn = findViewById(R.id.btn_start);
        stopByCancelBtn = findViewById(R.id.btn_stop_cancel);

        startBtn.setOnClickListener(this);
        stopByCancelBtn.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
              getNoOfAsyncTasks();

                myAsyncTask = new AsyncTask[noOfAsyncTasks];

                for (int i = 0; i < myAsyncTask.length; i++) {
                    myAsyncTask[i] = new MyAsyncTask(CheckMaxAsyncTasksAllowedActivity.this, i);

                    String[] params = new String[]{"url0", "url1", "url2"};
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        myAsyncTask[i].executeOnExecutor(getExecutor(), params);

                    } else {
                        myAsyncTask[i].execute(params);

                    }
                }


                break;
            case R.id.btn_stop_by_boolean:

                break;
            case R.id.btn_stop_cancel:
                for (int i = 0; i < myAsyncTask.length; i++) {
                    myAsyncTask[i].cancel(true);
                }
                break;
            default:
                break;
        }
    }

    private int getNoOfAsyncTasks() {
        if (etNoOfAsyncTasks.getText().toString().isEmpty()) {
            noOfAsyncTasks = 1;
        } else {
            noOfAsyncTasks = Integer.parseInt(etNoOfAsyncTasks.getText().toString());
        }
        return noOfAsyncTasks;
    }

    private Executor getExecutor() {
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(id);
        if (radioButton.getText().toString().equals("THREAD_POOL_EXECUTOR")) {
           return AsyncTask.THREAD_POOL_EXECUTOR;
        } else if (radioButton.getText().toString().equals("SERIAL_EXECUTOR")) {
            return AsyncTask.SERIAL_EXECUTOR;
        }else{
            return AsyncTask.THREAD_POOL_EXECUTOR;
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Object> {
        int noOfAsyncTask;

        public MyAsyncTask(Context context, int noOfAsyncTasks) {
            Log.d(TAG, "No of AsyncTasks" + noOfAsyncTasks);
            this.noOfAsyncTask = noOfAsyncTasks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... strings) {
            String url = strings[0];
            int counter1 = 0;
            while (counter1 < 15) {
                try {
                    Thread.sleep(100);
                    counter1++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(counter1);

                if (isCancelled()) {
                    break;
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvDescription.setText("AsyncTask no is " + noOfAsyncTask + " and counter is" + values[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }


}
