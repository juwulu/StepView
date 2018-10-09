package com.jwl.stepview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jwl.library.StepView;

public class MainActivity extends AppCompatActivity {

    private StepView mSV3;
    private StepView mSV2;
    private StepView mSV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSV3 = (StepView) findViewById(R.id.view3);
        mSV2 = (StepView) findViewById(R.id.view2);
        mSV1 = (StepView) findViewById(R.id.view1);
    }

    public void click(View view) {
        mSV3.setBottomText(new String[]{"一","二","三","四","五"});
        mSV2.setBottomText(new String[]{"壹","贰","叁","肆"});
        mSV1.setBottomText(new String[]{"one","two","three"});
    }
}
