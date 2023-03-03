package com.example.courseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CourseRVAdapter.CourseClickInterface {
    private RecyclerView courseRv;
    private ProgressBar loadingPB;
    private FloatingActionButton addAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CourseRVModel> courseRVModelArrayList;
    private RelativeLayout homeRL;
    private CourseRVAdapter courseRVAdapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseRv = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idPBloding);
        addAB = findViewById(R.id.idADDFB);
        homeRL = findViewById(R.id.idRLBSheet);
        firebaseDatabase = firebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference  =firebaseDatabase.getReference("Courses");
        courseRVModelArrayList = new ArrayList<>();
        courseRVAdapter = new CourseRVAdapter(courseRVModelArrayList,this,this);
        courseRv.setLayoutManager(new LinearLayoutManager(this));
        courseRv.setAdapter(courseRVAdapter);
        addAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddcourseActivity.class));
            }
        });
        getAllCourses();
    }
private void getAllCourses(){
        courseRVModelArrayList.clear();
        databaseReference.addChildEventListener((new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                courseRVModelArrayList.add(snapshot.getValue(CourseRVModel.class));
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
}
    @Override
    public void onCourseClick(int position) {
        displayButtonSheet(courseRVModelArrayList.get(position));
    }

    private void displayButtonSheet(CourseRVModel courseRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout,homeRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        TextView courseNameTv = layout.findViewById(R.id.idTVcoursename);
        TextView courseDescrip = layout.findViewById(R.id.idTVDescription);
        TextView courseSuitedFor = layout.findViewById(R.id.idTVSuitedFor);
        TextView coursePrice = layout.findViewById(R.id.idTVprice);
        ImageView courseImage = layout.findViewById(R.id.TVcourseimg);
        Button editbtn = layout.findViewById(R.id.ideditbtn);
        Button viewdetail = layout.findViewById(R.id.idviewdetailbtn);


        courseNameTv.setText(courseRVModel.getCoursename());
        courseDescrip.setText(courseRVModel.getCourseDescription());
        courseSuitedFor.setText(courseRVModel.getCourseSuitedFor());
        coursePrice.setText(courseRVModel.getCourseprice());
        Picasso.get().load(courseRVModel.getCourseImage()).into(courseImage);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,EditcourseActivity.class);
                i.putExtra("courses",courseRVModel);
                startActivity(i);
            }
        });

        viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(courseRVModel.getCourseLink()));
                startActivity(i);
            }
        });
    }

    public Boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public Boolean onOptionItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogout:
                Toast.makeText(this, "UserLogOut", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return
                        super.onOptionsItemSelected(item);
        }
    }
}