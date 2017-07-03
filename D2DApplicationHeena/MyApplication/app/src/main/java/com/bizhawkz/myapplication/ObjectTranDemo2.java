package com.bizhawkz.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Heena on 3/14/2017.
 */
public class ObjectTranDemo2 extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView mTextView = new TextView(this);
        Book mBook = (Book)getIntent().getParcelableExtra(MainActivity.PAR_KEY);
        mTextView.setText("Book name is: " + mBook.getBookName()+"/n"+
                "Author is: " + mBook.getAuthor() + "/n" +
                "PublishTime is: " + mBook.getPublishTime());
        setContentView(mTextView);
    }
}
