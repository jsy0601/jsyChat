package com.daelim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pw;
    private Button bt_login;
    private CheckBox cb_auto;
    private SharedPreferences sp;
    private AlertDialog ad;
    private Boolean b = false; //자동로그인 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        bt_login = findViewById(R.id.bt_login);
        cb_auto = findViewById(R.id.cb_auto);

        sp = getSharedPreferences("text", MODE_PRIVATE);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = sp.edit();
                e.putString("id", et_id.getText().toString());
                e.putString("pw", et_pw.getText().toString());
                e.commit();
                Toast.makeText(LoginActivity.this, et_id.getText().toString()+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                String s = sp.getString("id", "");
                Log.e("!!!", "" + s);
                if (cb_auto.isChecked()) {
                    e.putString("id", et_id.getText().toString());
                    e.putString("pw", et_pw.getText().toString());
                    e.putBoolean("true", true);
                    e.commit();
                }
                Intent i = new Intent(LoginActivity.this, ChatActivity.class);
                i.putExtra("id", et_id.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }
}