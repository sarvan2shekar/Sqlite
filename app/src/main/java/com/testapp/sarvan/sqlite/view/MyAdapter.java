package com.testapp.sarvan.sqlite.view;

import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.testapp.sarvan.sqlite.R;
import com.testapp.sarvan.sqlite.model.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarva on 17-08-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<Employee> employeeList = new ArrayList<Employee>();

    public MyAdapter(List<Employee> employeeList1) {
        employeeList.addAll(employeeList1);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
//        holder.currentEmp = employeeList.get(position);
        holder.mTextView.setText(employeeList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Employee currentEmp;

        CardView cardView;
        ImageButton ib;
        ViewGroup.LayoutParams lp;
        int minHeight, expandedHeight;

        public ViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.card_view);
            ib = v.findViewById(R.id.expand_card);
            expandedHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                minHeight = cardView.getMinimumHeight();
            } else {
                final float scale = v.getResources().getDisplayMetrics().density;
                minHeight = (int) (65 * scale + 0.5f);
            }
            mTextView = v.findViewById(R.id.textViewList);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lp = cardView.getLayoutParams();
                    if (ib.getVisibility() == View.VISIBLE) {
                        lp.height = expandedHeight;
                        cardView.setLayoutParams(lp);
                        ib.setVisibility(View.INVISIBLE);
                    } else {
                        lp.height = minHeight;
                        cardView.setLayoutParams(lp);
                        ib.setVisibility(View.VISIBLE);
                    }
                    System.out.println("Clicked: " + ((TextView) view).getText());
                }
            });
        }
    }
}
