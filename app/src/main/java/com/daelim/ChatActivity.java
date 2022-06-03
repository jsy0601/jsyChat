package com.daelim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.daelim.data.ListData;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    WebSocketClient ws;
    private Button bt_submit;
    private EditText et_text;
    private String id, text;
    private ArrayList<ListData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        id = getIntent().getStringExtra("id");
        et_text = findViewById(R.id.et_text);

        try {
            ws = new WebSocketClient(new URI("ws://61.83.168.88:4877")) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.e("!!!", "onOpen");
                }

                @Override
                public void onMessage(String s) {
                    Log.e("!!!", "onMessage s:" + s);
                    bt_submit = findViewById(R.id.bt_submit);
                    bt_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            text = et_text.getText().toString();
                            sendMessage(text);
                            et_text.setText("");
                        }
                    });
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

    public void sendMessage(String text) {
        String timeSpent = "";
        timeSpent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ws.send("CHAT|" + id + "|"+ timeSpent + "|" + text);
        String s = "CHAT|" + id + "|"+ timeSpent + "|" + text;
        String[] strs = s.split("\\|");
        list = new ArrayList<ListData>();
        list.add(new ListData(strs[1], strs[2], strs[3]));
        ListView lv_data = findViewById(R.id.lv_data);
        lv_data.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getLayoutInflater().inflate(R.layout.list_chat, viewGroup, false);

                TextView id = view.findViewById(R.id.tv_id);
                TextView date = view.findViewById(R.id.tv_date);
                TextView text = view.findViewById(R.id.tv_text);

                id.setText(list.get(i).getId());
                date.setText(list.get(i).getDate());
                text.setText(list.get(i).getText());

                return view;
            }
        });
    }
}