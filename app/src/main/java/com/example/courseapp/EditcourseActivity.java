package com.example.courseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditcourseActivity extends AppCompatActivity {
    private Button Update,delete;
    private CourseRVModel courseRVModel;
    private TextInputEditText courseName, coursePriceEdit,courseSuitedFor,courseimgEdit,courseLink,courseDescription;
    private ProgressBar lodingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcourse);
        courseName = findViewById(R.id.idEditCourseName);
        coursePriceEdit = findViewById(R.id.idEditCoursePrice);
        courseSuitedFor = findViewById(R.id.idEditSuitedFor);
        courseimgEdit = findViewById(R.id.idEditImageLink);
        courseDescription = findViewById(R.id.idTILCourseDescription);
        courseLink = findViewById(R.id.idTILCourseLink);
        Update = findViewById(R.id.idBtnUpdate);
        delete = findViewById(R.id.idBtnDelete);
        lodingPB = findViewById(R.id.idPBloding);
        firebaseDatabase = FirebaseDatabase.getInstance();

        courseRVModel = getIntent().getParcelableExtra("course");
        if (courseRVModel != null) {
            courseName.setText(courseRVModel.getCoursename());
            coursePriceEdit.setText(courseRVModel.getCourseprice());
            courseSuitedFor.setText(courseRVModel.getCourseSuitedFor());
            courseDescription.setText(courseRVModel.getCourseDescription());
            courseimgEdit.setText(courseRVModel.getCourseImage());
            courseLink.setText(courseRVModel.getCourseLink());
            courseId = courseRVModel.getCourseid();
        }

        databaseReference = firebaseDatabase.getReference("Courses").child(courseId);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lodingPB.setVisibility(View.VISIBLE);
                String courseprices = coursePriceEdit.getText().toString();
                String courseNames = courseName.getText().toString();
                String courseDescrip = courseDescription.getText().toString();
                String courseimage = courseimgEdit.getText().toString();
                String courseFor = courseSuitedFor.getText().toString();
                String courselink = courseLink.getText().toString();
                courseId = courseNames;

                Map<String, Object> map = new HashMap<>();
                map.put("coursename", courseNames);
                map.put("courseDescription", courseDescrip);
                map.put("courseSuitedFor", courseFor);
                map.put("courseImage", courseimage);
                map.put("courseLink", courselink);
                map.put("courseid", courseId);
                map.put("courseprice", courseprices);

                databaseReference.addValueEventListener((new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lodingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditcourseActivity.this, "Course Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditcourseActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditcourseActivity.this, "Failed to Updated Course", Toast.LENGTH_SHORT).show();
                    }
                }));
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });

    }
    public void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(this, "Course Delete", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditcourseActivity.this,MainActivity.class));
    }

}