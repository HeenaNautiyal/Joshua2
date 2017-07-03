package com.bizhawkz.parsabledata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Heena on 3/15/2017.
 */
public class Student implements Parcelable {

    String mSName;
    int mSAge;
    String mSAddress;
    String mSCourse;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Storing the Student data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSName);
        dest.writeInt(mSAge);
        dest.writeString(mSAddress);
        dest.writeString(mSCourse);
    }

    /**
     * A constructor that initializes the Student object
     **/
    public Student(String sName, int sAge, String sAddress, String sCourse){
        this.mSName = sName;
        this.mSAge = sAge;
        this.mSAddress = sAddress;
        this.mSCourse = sCourse;
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Student(Parcel in){
        this.mSName = in.readString();
        this.mSAge = in.readInt();
        this.mSAddress = in.readString();
        this.mSCourse = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {

        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
