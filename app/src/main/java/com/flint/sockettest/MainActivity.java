package com.flint.sockettest;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity {

    public void establishTCP() throws IOException {
        Log.d("TCP", "Client: Connecting...");
        Socket socket = new Socket("192.168.0.100", 3333);
        String message = "The message is sent from my android.";
        TextView textView = (TextView) findViewById(R.id.TextView);
        textView.setText("Server Infomation:\nhost: " + socket.getInetAddress().getHostAddress() + '\n'
                + "hostname: " + socket.getInetAddress().getHostName() + "\nport: " + 3333);
        try {
            Log.d("TCP", "Client: Sending: '" + message + "'");
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream()), true);
            out.println(message);
        } catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        } finally {
            socket.close();
            Toast.makeText(this, "the message was sent successfully. ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 详见StrictMode文档，防止出现 android.os.NetworkOnMainThreadException 异常
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //自行修改布局文件名

        Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    establishTCP();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

}
