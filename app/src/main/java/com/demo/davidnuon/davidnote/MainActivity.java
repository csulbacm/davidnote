package com.demo.davidnuon.davidnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

    public static final int NOTES_SAVED = 0x10;
    public static final int NOTES_NEW = 0x20;

    public static final int NOTES_EDITOR = 0x01;
    ListView mNotesList;
    ArrayAdapter<Note> mNotesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotesList = (ListView) findViewById(R.id.act_main_notesList);
        mNotesListAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        mNotesListAdapter.add(new Note("This is a title"));
        mNotesListAdapter.add(new Note("This is a title 1"));
        mNotesListAdapter.add(new Note("This is a title 2"));
        mNotesList.setAdapter(mNotesListAdapter);

        mNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter theAdapter = (ArrayAdapter) adapterView.getAdapter();
                Note selectedNote = (Note) theAdapter.getItem(i);
                Intent intent = new Intent(MainActivity.this, NoteEditActivity.class);
                intent.putExtra("body", selectedNote.getBody());
                intent.putExtra("title", selectedNote.getTitle());
                intent.putExtra("id", i);
                startActivityForResult(intent, NOTES_EDITOR);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NOTES_EDITOR && resultCode == NOTES_SAVED) {
            Note note = mNotesListAdapter.getItem(data.getIntExtra("id", -1));
            note.setTitle(data.getStringExtra("title"));
            note.setBody(data.getStringExtra("body"));
            mNotesListAdapter.notifyDataSetChanged();
        }

        if(requestCode == NOTES_EDITOR && resultCode == NOTES_NEW) {
            Note note = new Note("");
            note.setTitle(data.getStringExtra("title"));
            note.setBody(data.getStringExtra("body"));
            mNotesListAdapter.add(note);
            mNotesListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            newNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void newNote() {
        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra("isNew", true);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
}
