package com.example.forexsignal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SignalAdapter signalAdapter;
    ProgressDialog progressDialog;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(APIClient.BASE_URL);
        } catch (URISyntaxException e) {
            Toast.makeText(MainActivity.this,"Could not connect",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Forex Signal");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading signals");
        getAllSignalsFromServer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.on("on-new-signal", onNewMessage);
        mSocket.connect();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Gson gson=new Gson();
                    Signal signal=gson.fromJson(args[0].toString(),Signal.class);
                    if(signalAdapter!=null){
                        signalAdapter.addSignal(signal);
                    }
                }
            });
        }
    };

    private void getAllSignalsFromServer() {
        progressDialog.show();
        Call<ArrayList<Signal>> signalCall=APIClient.getClient().create(APIInterface.class).getAllSignals();
        signalCall.enqueue(new Callback<ArrayList<Signal>>() {
            @Override
            public void onResponse(Call<ArrayList<Signal>> call, Response<ArrayList<Signal>> response) {
                progressDialog.hide();
                if(response.isSuccessful()){
                    loadSignalsToAdapter(response.body());
                }else{
                    Toast.makeText(MainActivity.this,"Error fetching signals",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Signal>> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(MainActivity.this,"Network Error fetching signals",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSignalsToAdapter(ArrayList<Signal> signals) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        signalAdapter=new SignalAdapter(signals);
        recyclerView.setAdapter(signalAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("on-new-signal", onNewMessage);
    }
}