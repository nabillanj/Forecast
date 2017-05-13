package com.nabilla.iakforecast;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
        holder.setOnClickListener(new InterfaceOnClickListener() {
            @Override
            public void onItemClick(int selectedPos) {
                selectedPos = selectedPos;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataCuacaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        InterfaceOnClickListener onClickListener;
        TextView tv_max, tv_min;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_max = (TextView) itemView.findViewById(R.id.tv_max);
            tv_min = (TextView) itemView.findViewById(R.id.tv_min);

            itemView.setOnClickListener(this);
        }

        public void setOnClickListener(InterfaceOnClickListener onClickListener){
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("MaxSuhu", tv_max.getText());
            intent.putExtra("MinSuhu", tv_min.getText());
            context.startActivity(intent);
        }
    }
}
