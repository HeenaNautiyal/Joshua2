package com.bizhawkz.parsabledata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Heena on 3/15/2017.
 */
public class StudentViewActivity extends AppCompatActivity {

    TextView mTvSName;
    TextView mTvSAge;
    TextView mTvSAddress;
    TextView mTvSCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // Fetching data from a parcelable object passed from MainActivity
        Student student = getIntent().getParcelableExtra("student");

        // Getting reference to TextView tv_sname of the layout file activity_student
        mTvSName = (TextView)findViewById(R.id.tv_sname);

        // Getting reference to TextView tv_sage of the layout file activity_student
        mTvSAge = (TextView) findViewById(R.id.tv_sage);

        // Getting reference to TextView tv_saddress of the layout file activity_student
        mTvSAddress = (TextView) findViewById(R.id.tv_saddress);

        // Getting reference to TextView tv_scourse of the layout file activity_student
        mTvSCourse = (TextView) findViewById(R.id.tv_scourse);

        if(student!=null){
            mTvSName.setText("Name:"+student.mSName);
            mTvSAge.setText("Age:"+Integer.toString(student.mSAge));
            mTvSAddress.setText("Address:"+student.mSAddress);
            mTvSCourse.setText("Course:"+student.mSCourse);
        }
    }
}
