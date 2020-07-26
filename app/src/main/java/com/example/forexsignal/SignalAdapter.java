package com.example.forexsignal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SignalAdapter extends RecyclerView.Adapter<SignalAdapter.SignalViewHolder> {
    ArrayList<Signal> signals;

    public SignalAdapter(ArrayList<Signal> signals) {
        this.signals = signals;
    }

    @NonNull
    @Override
    public SignalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.signal_list_item,parent,false);

        return new SignalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignalViewHolder holder, int position) {
        holder.bind(signals.get(position));
    }

    public class SignalViewHolder extends RecyclerView.ViewHolder{
        TextView signalName;
        TextView tradeId;
        TextView prediction;
        public SignalViewHolder(@NonNull View itemView) {
            super(itemView);
            signalName=itemView.findViewById(R.id.signalName);
            tradeId=itemView.findViewById(R.id.tradeId);
            prediction=itemView.findViewById(R.id.prediction);
        }

        public void bind(Signal signal){
            signalName.setText("Signal Name: "+signal.getSignalName());
            tradeId.setText("Trade ID: "+signal.getTradeId());
            prediction.setText("Prediction: "+signal.getPrediction());
        }
    }

    public void addSignal(Signal signal){
        signals.add(signal);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return signals.size();
    }
}
