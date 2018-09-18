package com.example.vishambar.myapplication;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncTasksUsingThreadPoolAndSerialExecutors extends Activity implements View.OnClickListener {

    private static String TAG = "MyApp";
    private Button startBtn, stopByBooleanBtn, stopByCancelBtn;
    private TextView textView, textView2;
    private int counter1, counter2;

    private AsyncTask<String, Integer, Object> myAsyncTask, myAsyncTask2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_text);
        textView2 = findViewById(R.id.tv_text2);
        startBtn = findViewById(R.id.btn_start);
        stopByBooleanBtn = findViewById(R.id.btn_stop_by_boolean);
        stopByCancelBtn = findViewById(R.id.btn_stop_cancel);

        startBtn.setOnClickListener(this);
        stopByBooleanBtn.setOnClickListener(this);
        stopByCancelBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                counter1 = 0;
                counter2 = 0;
                myAsyncTask = new MyAsyncTask(AsyncTasksUsingThreadPoolAndSerialExecutors.this);
                myAsyncTask2 = new MyAsyncTask2(AsyncTasksUsingThreadPoolAndSerialExecutors.this);
                String[] params = new String[]{"url0", "url1", "url2"};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    myAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                    myAsyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
//                    myAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
//                    myAsyncTask2.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
                } else {
                    myAsyncTask.execute(params);
                    myAsyncTask2.execute(params);
                }
                break;
            case R.id.btn_stop_by_boolean:

                break;
            case R.id.btn_stop_cancel:
                myAsyncTask.cancel(true);
                myAsyncTask2.cancel(true);
                break;
            default:
                break;
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Object> {
        public MyAsyncTask(Context context) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... strings) {
            String url = strings[0];

            while (counter1 < 15) {
                try {
                    Thread.sleep(1000);
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
            textView.setText(values[0] + "");
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    private class MyAsyncTask2 extends AsyncTask<String, Integer, Object> {
        public MyAsyncTask2(Context context) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... strings) {
            String url = strings[0];

            while (counter2 < 15) {
                try {
                    Thread.sleep(1000);
                    counter2++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(counter2);

                if (isCancelled()) {
                    break;
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView2.setText(values[0] + "");
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
