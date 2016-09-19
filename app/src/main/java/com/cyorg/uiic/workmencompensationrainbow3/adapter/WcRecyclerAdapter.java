package com.cyorg.uiic.workmencompensationrainbow3.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cyorg.uiic.workmencompensationrainbow3.R;
import com.cyorg.uiic.workmencompensationrainbow3.model.SingleEntryModel;
import com.cyorg.uiic.workmencompensationrainbow3.utils.CommonUtils;

public class WcRecyclerAdapter extends RecyclerView.Adapter<WcRecyclerAdapter.WcViewHolder> {


    @Override
    public WcViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WcViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WcViewHolder holder, int position) {
        SingleEntryModel entryModel = CommonUtils.getDataList().get(position);
        Log.i("tag", "Null Check :: holder - " + holder);
        Log.i("tag", "Null Check :: salary - " + holder.salary);
        holder.salary.setText(Double.toString(entryModel.getSalary()));
        holder.noPersons.setText(String.valueOf(entryModel.getNoPersons()));
        holder.premium.setText(Double.toString(entryModel.getPremium()));
        holder.delCheck.setSelected(entryModel.isDelCheck());
    }

    @Override
    public int getItemCount() {
        return CommonUtils.getDataList().size();
    }

    class WcViewHolder extends RecyclerView.ViewHolder {
        private TextView salary, noPersons, premium;
        private CheckBox delCheck;

        public WcViewHolder(View itemView) {
            super(itemView);
            salary = (TextView) itemView.findViewById(R.id.card_salary);
            Log.i("tag", "Null Check :: salary - " + salary);
            noPersons = (TextView) itemView.findViewById(R.id.card_no_persons);
            premium = (TextView) itemView.findViewById(R.id.card_premium);
            delCheck = (CheckBox) itemView.findViewById(R.id.card_chkbox_del);
        }
    }
}
