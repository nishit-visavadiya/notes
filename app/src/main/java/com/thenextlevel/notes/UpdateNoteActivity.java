package com.thenextlevel.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {

    String id;
    EditText title, description;
    Button updateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        updateNote = findViewById(R.id.updateNote);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");

        updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {

                    DatabaseHelper databaseHelper = new DatabaseHelper(UpdateNoteActivity.this);
                    databaseHelper.updateNote(title.getText().toString(), description.getText().toString(), id);

                    Intent intent1 = new Intent(UpdateNoteActivity.this,MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    finish();

                } else {
                    Toast.makeText(UpdateNoteActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}