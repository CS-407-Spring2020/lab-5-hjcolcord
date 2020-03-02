package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    int noteId = -1;

    public void onClickSave(View view) {
        EditText textField = (EditText) findViewById(R.id.editNoteText);
        String userContent = textField.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);
        DBHelper dbHelper;
        dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteId == -1) {
            title = "NOTE_" + Main2Activity.notes.size() + 1;
            dbHelper.saveNotes(username, title, userContent, date);
            System.out.println(username);
            System.out.println(title);
            System.out.println(userContent);
            System.out.println(date);
        } else {
            title = "NOTE_" + (noteId + 1);
            dbHelper.updateNotes(title, date, userContent, username);
        }
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        EditText textField = (EditText) findViewById(R.id.editNoteText);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteid", -1);

        if(noteId != -1) {
            Note note = Main2Activity.notes.get(noteId);
            String noteContent = note.getContent();
            textField.setText(noteContent);
        }
    }
}
