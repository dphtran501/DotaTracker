package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText mSteamIdEditText;
    Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSteamIdEditText = (EditText) findViewById(R.id.steamIDEditText);
        mRegisterButton = (Button) findViewById(R.id.submitButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });
    }

    private void submitButton() {

        long steamId = Long.parseLong(mSteamIdEditText.getText().toString());
        boolean success = HTTPRequestService.postUserID(steamId);

        if (success) {
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("currentUserId", steamId);
            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to retrieve user information", Toast.LENGTH_SHORT).show();
        }

    }
}
