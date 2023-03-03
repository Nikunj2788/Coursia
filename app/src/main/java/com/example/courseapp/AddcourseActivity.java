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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddcourseActivity extends AppCompatActivity {
    private TextInputEditText courseName, coursePriceEdit,courseSuitedFor,courseimgEdit,courseLink,courseDescription;
    private Button addcourse;
    private ProgressBar lodingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseName = findViewById(R.id.idEditCourseName);
        coursePriceEdit= findViewById(R.id.idEditCoursePrice);
        courseSuitedFor = findViewById(R.id.idEditSuitedFor);
        courseimgEdit = findViewById(R.id.idEditImageLink);
        courseDescription = findViewById(R.id.idTILCourseDescription);
        addcourse = findViewById(R.id.idBtnADD);
        lodingPB = findViewById(R.id.idPBloding);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        addcourse.setOnClickListener(new View.OnClickListener() {
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
                CourseRVModel courseRVModel = new CourseRVModel(courseNames,courseDescrip,courseFor,courseimage,courselink,courseId,courseprices);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lodingPB.setVisibility(View.GONE);
                        databaseReference.child(courseId).setValue(courseRVModel);
                        Toast.makeText(AddcourseActivity.this, "Course Addes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddcourseActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddcourseActivity.this, "Error is"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}