package com.demo.davidnuon.davidnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NoteEditActivity extends Activity {

    EditText mTitleField;
    EditText mBodyField;
    Button mConfirm;
    int mID;
    boolean mIsNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit_activity);

        mTitleField = (EditText) findViewById(R.id.act_note_titleText);
        mBodyField = (EditText) findViewById(R.id.act_note_bodyText);
        mConfirm = (Button) findViewById(R.id.act_note_OK);

        Bundle getBundle = getIntent().getExtras();
        mTitleField.setText(getBundle.getString("title"));
        mBodyField.setText(getBundle.getString("body"));
        mID = getBundle.getInt("id", -1);
        mIsNew = getBundle.getBoolean("isNew", false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_edit_activiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirm(View view) {
        Intent intent = new Intent();
        intent.putExtra("body", mBodyField.getText().toString());
        intent.putExtra("title", mTitleField.getText().toString());
        intent.putExtra("id", mID);

        if(mIsNew) {
            setResult(MainActivity.NOTES_NEW, intent);
        } else {
            setResult(MainActivity.NOTES_SAVED, intent);
        }

        finish();
    }
}
