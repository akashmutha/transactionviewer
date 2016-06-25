package com.badoo.transactionviewer.ui.ProductDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.transactionviewer.R;
import com.badoo.transactionviewer.TransactionViewerApp;
import com.badoo.transactionviewer.data.files.service.ReadTransactionsService;
import com.badoo.transactionviewer.model.Transaction;
import java.util.HashMap;

/**
 * Created by mutha on 25/06/16.
 */
public class ProductDetailsFragment extends Fragment {
    private RelativeLayout mainLayout;
    private ProgressBar progressBar;
    private RecyclerView rvProductList;
    private ProductDetailsAdapter productDetailsAdapter;
    private static ReadTransactionsService readTransactionsService;
    private TextView totalTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainLayout = (RelativeLayout) inflater.inflate(R.layout.products_detail_fragment, container, false);
        return mainLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = (ProgressBar) mainLayout.findViewById(R.id.progressBarProductsDetails);
        progressBar.setVisibility(View.VISIBLE);
        totalTextView = (TextView) mainLayout.findViewById(R.id.totalAmount);
        InflateRecyclerProductView();
    }

    private void InflateRecyclerProductView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvProductList = (RecyclerView) mainLayout.findViewById(R.id.rvProductsDetails);
        if(TransactionViewerApp.getConversionMatrix() == null || TransactionViewerApp.getConversionMatrix().getConversionMap() == null){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Rates file is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        calculateTotalAmountAndUpdate();
        productDetailsAdapter = new ProductDetailsAdapter(((ProductDetailsActivity)getActivity()).getTransactionList());
        rvProductList.setLayoutManager(mLayoutManager);
        rvProductList.setItemAnimator(new DefaultItemAnimator());
        rvProductList.setAdapter(productDetailsAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void calculateTotalAmountAndUpdate(){
        double total = 0.00;
        HashMap<String , String> keyHashMap = new HashMap<>();
        for(Transaction transaction : ((ProductDetailsActivity)getActivity()).getTransactionList()){
            if(transaction.getCurrency().equals("GBP")){
                total += transaction.getAmount();
                continue;
            }
            keyHashMap.put("GBP", transaction.getCurrency());
            total += (transaction.getAmount() * ((Math.round((1/TransactionViewerApp.getConversionMatrix().getConversionMap().get(keyHashMap)) * 100.0)/100.0)));
        }
        totalTextView.setText("Total : £" + String.format("%.2f",total));
    }
}
