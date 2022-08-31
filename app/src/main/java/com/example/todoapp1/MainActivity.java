package com.example.todoapp1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btn_add;
    private ArrayList<MainData> arrayList;
    private DBHelper mDBHelper;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_date = (TextView)findViewById(R.id.tv_date);
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd 오늘은");
        String getTime = simpleDate.format(mDate);
        tv_date.setText(getTime);

        setInit();
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.rv);
        btn_add = findViewById(R.id.btn_add);
        arrayList = new ArrayList<>();

        loadRecentDB();

        btn_add.setOnClickListener(new View.OnClickListener() {
            EditText et_todo = (EditText)findViewById(R.id.et_todo);
            EditText et_memo = (EditText)findViewById(R.id.et_memo);

            @Override
            public void onClick(View v) {
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
                MainData item = new MainData();

                if (et_todo.getText().toString().equals("") || et_todo.getText() == null) {
                    Toast.makeText(MainActivity.this, "할 일을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    mDBHelper.InsertTodo(et_todo.getText().toString(), et_memo.getText().toString(), currentTime);

                    item.setTv_todo(et_todo.getText().toString());
                    item.setTv_memo(et_memo.getText().toString());

                    item.setTv_writeDate(currentTime);

                    mAdapter.addItem(item);

                    recyclerView.smoothScrollToPosition(0);
                    Toast.makeText(MainActivity.this, "추가", Toast.LENGTH_SHORT).show();
                }

                et_todo.setText(null);
                et_memo.setText(null);
            }
        });
    }

    private void loadRecentDB() {
        arrayList = mDBHelper.getTodoList();
        if (mAdapter == null) {
            mAdapter = new MainAdapter(arrayList, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(mAdapter);
        }
    }
}