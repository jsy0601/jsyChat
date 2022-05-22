package com.daelim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    WebSocketClient ws;
    private EditText et_text;
    private Button bt_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        et_text = findViewById(R.id.et_text);

        String id = getIntent().getStringExtra("id");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowTime = sdf.format(now);

        try {
            ws = new WebSocketClient(new URI("ws://61.83.168.88:4877")) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.e("!!!", "onOpen");
                    bt_submit = findViewById(R.id.bt_submit);
                    bt_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = et_text.getText().toString();
                            ws.send("CHAT|" + id + "|"+ nowTime + "|" +text);
                        }
                    });
                }

                @Override
                public void onMessage(String s) {
                    Log.e("!!!", "onMessage s:" + s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.e("!!!", "onClose : " + s);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("!!!", "onError");
                    e.printStackTrace();
                }
            };

            ws.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}