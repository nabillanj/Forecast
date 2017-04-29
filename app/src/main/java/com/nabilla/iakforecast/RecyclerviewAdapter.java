package com.nabilla.iakforecast;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{

    private Context context;
    private List<DataCuaca> dataCuacaList;

    public RecyclerviewAdapter(Context context, List<DataCuaca> dataCuacaList) {
        this.context = context;
        this.dataCuacaList = dataCuacaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false);
        ViewHolder holder = new ViewHolder(inflater);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerviewAdapter.ViewHolder holder, int position) {
        holder.tv_max.setText("Maks suhu : "+dataCuacaList.get(position).getMax().toString());
        holder.tv_min.setText("Min suhu : "+dataCuacaList.get(position).getMin().toString());
    }

    @Override
    public int getItemCount() {
        return dataCuacaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_max, tv_min;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_max = (TextView) itemView.findViewById(R.id.tv_max);
            tv_min = (TextView) itemView.findViewById(R.id.tv_min);
        }
    }
}
