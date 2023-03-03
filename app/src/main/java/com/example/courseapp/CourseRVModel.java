package com.example.courseapp;


import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModel implements Parcelable {
    private String coursename;
    private String courseDescription;
    private String courseSuitedFor;
    private String courseImage;
    private String courseLink;
    private String courseid;
    private String courseprice;

    public CourseRVModel(){

    }

    public CourseRVModel(String coursename, String courseDescription, String courseSuitedFor, String courseImage, String courseLink, String courseid, String courseprice) {

        this.coursename = coursename;
        this.courseDescription = courseDescription;
        this.courseSuitedFor = courseSuitedFor;
        this.courseImage = courseImage;
        this.courseLink = courseLink;
        this.courseid = courseid;
        this.courseprice = courseprice;
    }

    protected CourseRVModel(Parcel in) {
        coursename = in.readString();
        courseDescription = in.readString();
        courseSuitedFor = in.readString();
        courseImage = in.readString();
        courseLink = in.readString();
        courseid = in.readString();
        courseprice = in.readString();
    }

    public static final Creator<CourseRVModel> CREATOR = new Creator<CourseRVModel>() {
        @Override
        public CourseRVModel createFromParcel(Parcel in) {
            return new CourseRVModel(in);
        }

        @Override
        public CourseRVModel[] newArray(int size) {
            return new CourseRVModel[size];
        }
    };

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseSuitedFor() {
        return courseSuitedFor;
    }

    public void setCourseSuitedFor(String courseSuitedFor) {
        this.courseSuitedFor = courseSuitedFor;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCourseprice() {
        return courseprice;
    }

    public void setCourseprice(String courseprice) {
        this.courseprice = courseprice;
    }

    @Override
    public String toString() {
        return "CourseRVModel{" +
                "coursename='" + coursename + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseSuitedFor='" + courseSuitedFor + '\'' +
                ", courseImage='" + courseImage + '\'' +
                ", courseLink='" + courseLink + '\'' +
                ", courseid='" + courseid + '\'' +
                ", courseprice='" + courseprice + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(coursename);
        parcel.writeString(courseDescription);
        parcel.writeString(courseSuitedFor);
        parcel.writeString(courseImage);
        parcel.writeString(courseLink);
        parcel.writeString(courseid);
        parcel.writeString(courseprice);
    }
}
