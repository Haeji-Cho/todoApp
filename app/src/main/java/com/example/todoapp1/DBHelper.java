package com.example.todoapp1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "todo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터베이스가 생성이 될 때 호출
        //데이터베이스 -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //SELECT문 (할 일 목록을 조회)
    public ArrayList<MainData> getTodoList() {
        ArrayList<MainData> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC", null);
        if (cursor.getCount() != 0) {
            //조회한 데이터가 있을 때
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));

                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                MainData todoItem = new MainData();
                todoItem.setId(id);
                todoItem.setTv_todo(title);
                todoItem.setTv_memo(content);
                todoItem.setTv_writeDate(writeDate);

                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return todoItems;
    }


    //INSERT문 (할 일 목록을 DB에 넣는다.)
    public void InsertTodo(String _title, String _content, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES('"+_title+"', '"+_content+"', '"+_writeDate+"');");
    }

    //DELETE문 (할 일 삭제하기)
    public void deleteTodo(String _beforeDate) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate = '"+_beforeDate+"'");
    }
}
