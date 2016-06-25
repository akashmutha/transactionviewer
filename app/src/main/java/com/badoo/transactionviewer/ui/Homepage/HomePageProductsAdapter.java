package com.badoo.transactionviewer.ui.Homepage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.transactionviewer.R;
import com.badoo.transactionviewer.model.Transaction;
import com.badoo.transactionviewer.ui.ProductDetails.ProductDetailsActivity;
import com.badoo.transactionviewer.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mutha on 25/06/16.
 */
public class HomePageProductsAdapter extends RecyclerView.Adapter<HomePageProductsAdapter.ProductViewHolder> {

    private List<String> keysTrasactionList;
    private HashMap<String, ArrayList<Transaction>> countForTransaction;

    public HomePageProductsAdapter(HashMap<String, ArrayList<Transaction>> countForTransaction) {
        this.countForTransaction = countForTransaction;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
        return new ProductViewHolder(itemView);
    }

    public HashMap<String, ArrayList<Transaction>> getCountForTransaction() {
        return this.countForTransaction;
    }

    public void setCountForTransaction(HashMap<String, ArrayList<Transaction>> countForTransaction) {
        this.countForTransaction = countForTransaction;
        keysTrasactionList = new ArrayList<>(countForTransaction.keySet());
        Collections.sort(keysTrasactionList);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return keysTrasactionList != null ? keysTrasactionList.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productSku;
        public TextView transactionCount;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productSku = (TextView) itemView.findViewById(R.id.productSku);
            transactionCount = (TextView) itemView.findViewById(R.id.transactionCount);
        }

        public void bind(final int position) {
            productSku.setText(keysTrasactionList.get(position));
            transactionCount.setText(countForTransaction.get(keysTrasactionList.get(position)).size() + " " + "transactions");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    intent.putExtra(Constants.INTENT_FOR_PRODUCT_DETAILS, countForTransaction.get(keysTrasactionList.get(position)));
                    intent.putExtra(Constants.PRODUCT_KEY, keysTrasactionList.get(position));
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
