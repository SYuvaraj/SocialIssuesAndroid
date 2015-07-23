package com.example.yuvaraj.bangaloregarbagegrab2;

/**
 * Created by Yuvaraj on 17/07/15.
 */
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class report_issue extends ActionBarActivity implements View.OnClickListener{

    ImageView photo_icon,viewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_issue);

        photo_icon=(ImageView)findViewById(R.id.photo_icon);
        photo_icon.setOnClickListener(this);

    }//onCreate

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.photo_icon:
                Intent capturePhoto = new Intent(report_issue.this, captured_image.class);
                startActivity(capturePhoto);
                break;
            default:
                break;

        }
    }//onclick ends

}//report issue
