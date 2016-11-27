package com.scarnedice.yashj.scarnedice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yashj on 11-08-2016.
 */
public class AboutUs extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AboutUs.this, MainActivity.class);
        startActivity(intent);
    }

}
