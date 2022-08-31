package com.example.todoapp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<MainData> arrayList;
    private Context mContext;
    private DBHelper mDBHelper;

    public MainAdapter(ArrayList<MainData> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MainAdapter.ViewHolder holder, int position) {
        holder.tv_todo.setText(arrayList.get(position).getTv_todo());
        holder.tv_memo.setText(arrayList.get(position).getTv_memo());
        holder.tv_writeDate.setText(arrayList.get(position).getTv_writeDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_todo, tv_memo, tv_writeDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_todo = (TextView) itemView.findViewById(R.id.tv_todo);
            tv_memo = (TextView) itemView.findViewById(R.id.tv_memo);
            tv_writeDate = (TextView) itemView.findViewById(R.id.tv_writeDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition();
                    MainData todoItem = arrayList.get(curPos);

                    String[] strChoiceItems = {"취소", "삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("삭제");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0) {

                            }
                            else if(which == 1) {
                                String beforeTime = todoItem.getTv_writeDate();
                                mDBHelper.deleteTodo(beforeTime);

                                arrayList.remove(curPos);
                                notifyItemRemoved(curPos);
                                Toast.makeText(mContext, "데이터 삭제됨", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    public void addItem(MainData _item) {
        arrayList.add(0, _item);
        notifyItemInserted(0);
    }
}
