package com.festeam.win7.testsails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    // 1.
    /* private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.107:1337");
        } catch (URISyntaxException e) {}
    } */
    // 2.
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // 1.

                // 2.
                Log.e("XXX", socket.connected()+"");
                try {
                    JSONObject data = new JSONObject();
                    data.put("staff_id", 1);

                    JSONObject obj = new JSONObject();
                    obj.put("url", "/find_all_monitoring");
                    obj.put("method", "get");
                    obj.put("data", data);
                    socket.emit("get", obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /* try {
                    JSONObject obj = new JSONObject();
                    obj.put("staff_id", 1);
                    socket.emit("/find_all_monitoring", obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                } */

                // 3.
                // Log.e("XXX1", socket.id()+"");
            }
        });

        Log.e("XXX", "GO...");

        // 1.

        // 2.
        try {
            // socket = IO.socket("http://192.168.0.107:1337"+"?__sails_io_sdk_version=0.11.0");
            // socket = IO.socket("http://104.131.8.98:1337");
            // socket = IO.socket("http://hiledperu.com:9040");
            IO.Options opts = new IO.Options();
            // opts.forceNew = true;
            opts.reconnection = true;
            opts.transports = new String[] {"websocket"};
            opts.query = "__sails_io_sdk_version=0.11.0&__sails_io_sdk_platform=android&__sails_io_sdk_language=java";


            // socket = IO.socket("http://192.168.1.7:1337");
            // socket = IO.socket("https://socket-io-chat.now.sh/");
            // socket = IO.socket("http://104.131.8.98:1337");
            socket = IO.socket("http://104.131.8.98:1337", opts);

            socket.on(Socket.EVENT_CONNECT,onConnect);
            socket.on(Socket.EVENT_RECONNECT,onReConnect);
            socket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            socket.on("refresh_all_monitoring", onRefreshAllMonitoring);

            socket.connect();
        } catch (URISyntaxException e) {
            Log.e("XXX", "Err");
            e.printStackTrace();
        }
        // 3.
        /* try {
            socket = new Socket("http://192.168.1.7:1337");
            socket.on(Socket.EVENT_OPEN, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    socket.send("hi");
                    // socket.close();
                }
            });
            socket.open();
        } catch (URISyntaxException e) {
            Log.e("XXX", "Err");
            e.printStackTrace();
        } */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        socket.disconnect();
        socket.off("new message", onRefreshAllMonitoring);
    }

    /***/
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("onconnect", "...");
                }
            });
        }
    };

    private Emitter.Listener onReConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("onreconnect", "...");
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("onDisconnect", "...");
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("onconnecterror", "...");
                }
            });
        }
    };

    private Emitter.Listener onRefreshAllMonitoring = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("XXX", args[0] + "");
                }
            });
        }
    };

}
