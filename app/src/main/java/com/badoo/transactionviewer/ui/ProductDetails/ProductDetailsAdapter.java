package com.badoo.transactionviewer.ui.ProductDetails;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.transactionviewer.R;
import com.badoo.transactionviewer.TransactionViewerApp;
import com.badoo.transactionviewer.model.Transaction;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mutha on 25/06/16.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ProductDetailsViewHolder> {

    private List<Transaction> trasactionList;

    @Override
    public ProductDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_amount, parent, false);
        return new ProductDetailsViewHolder(itemView);
    }

    public ProductDetailsAdapter(ArrayList<Transaction> keysTrasactionList) {
        this.trasactionList = keysTrasactionList;

    }

    public List<Transaction> getTrasactionList() {
        return this.trasactionList;
    }

    public void setTrasactionList(List<Transaction> trasactionList) {
        this.trasactionList = trasactionList;
    }

    @Override
    public void onBindViewHolder(ProductDetailsViewHolder holder, int position) {
        holder.realAmount.setText(trasactionList.get(position).getCurrency() + " " + String.format("%.2f", trasactionList.get(position).getAmount()));
        HashMap<String, String> keyHashMap = new HashMap<>();
        keyHashMap.put("GBP", trasactionList.get(position).getCurrency());
        if(!trasactionList.get(position).getCurrency().equals("GBP")) {
            holder.amountInPond.setText("£" + String.format("%.2f",trasactionList.get(position).getAmount() * (Math.round((1/TransactionViewerApp.getConversionMatrix().getConversionMap().get(keyHashMap)) * 100.0)/100.0)));
        } else {
            holder.amountInPond.setText("£" + trasactionList.get(position).getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return trasactionList != null ? trasactionList.size() : 0;
    }

    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder {
        public TextView realAmount;
        public TextView amountInPond;

        public ProductDetailsViewHolder(View itemView) {
            super(itemView);
            realAmount = (TextView) itemView.findViewById(R.id.realAmount);
            amountInPond = (TextView) itemView.findViewById(R.id.amountInPound);
        }
    }
}
