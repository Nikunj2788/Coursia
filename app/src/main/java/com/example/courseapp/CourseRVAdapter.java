package com.example.courseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    private ArrayList<CourseRVModel> courseRVModelArrayList;
    private Context context;
    int lastpos = -1;
    private CourseClickInterface courseClickInterface;

    public CourseRVAdapter(ArrayList<CourseRVModel> courseRVModelArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.courseRVModelArrayList = courseRVModelArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull

    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {
        CourseRVModel courseRVModel = courseRVModelArrayList.get(position);
        holder.courseNameTv.setText(courseRVModel.getCoursename());
        holder.coursePriveTv.setText(courseRVModel.getCourseprice());
        Picasso.get().load(courseRVModel.getCourseImage()).into(holder.courseTv);
        setaAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseClickInterface.onCourseClick(position);
            }
        });


    }
      private void setaAnimation(View itemView,int position){
        if(position>lastpos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }
      }
    @Override
    public int getItemCount() {
        return courseRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseNameTv,coursePriveTv;
        private ImageView courseTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTv = itemView.findViewById(R.id.idTVcoursename);
            coursePriveTv = itemView.findViewById(R.id.idTVprice);
            courseTv = itemView.findViewById(R.id.idTVcourse);
        }
    }

    public interface CourseClickInterface{
        void onCourseClick(int position);
    }

}
