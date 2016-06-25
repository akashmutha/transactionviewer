package com.badoo.transactionviewer.ui.Homepage;

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
import android.widget.Toast;

import com.badoo.transactionviewer.R;
import com.badoo.transactionviewer.data.files.service.ReadRatesService;
import com.badoo.transactionviewer.data.files.service.ReadTransactionsService;
import com.badoo.transactionviewer.data.files.service.ReadTransactionsServiceImpl;
import com.badoo.transactionviewer.model.AllTransactions;
import com.badoo.transactionviewer.model.ConversionMatrix;
import com.badoo.transactionviewer.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 25/06/16.
 */
public class HomePageFragment extends Fragment {

    private RelativeLayout mainLayout;
    private ProgressBar progressBar;
    private RecyclerView rvProductList;
    private HomePageProductsAdapter homePageProductsAdapter;
    private static ReadTransactionsService readTransactionsService;
    private ConversionMatrix conversionMatrix;
    private static ReadRatesService readRatesService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainLayout = (RelativeLayout) inflater.inflate(R.layout.products_home, container, false);
        return mainLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = (ProgressBar) mainLayout.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        InflateRecyclerProductView();
    }

    private void InflateRecyclerProductView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvProductList = (RecyclerView) mainLayout.findViewById(R.id.rvProducts);
        homePageProductsAdapter = new HomePageProductsAdapter(null);
        rvProductList.setLayoutManager(mLayoutManager);
        rvProductList.setItemAnimator(new DefaultItemAnimator());
        rvProductList.setAdapter(homePageProductsAdapter);
        try {
            // these both task execute on the background threads (differnt)
            fetchAndShowProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchAndShowProducts() throws IOException {
        if(readTransactionsService == null){
            readTransactionsService = new ReadTransactionsServiceImpl();
        }
        InputStream fileInputStream = getActivity().getAssets().open(Constants.TRANSACTION_FILE_NAME);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
        readTransactionsService.readTransactions(bufferedReader)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<AllTransactions>() {
                    @Override
                    public void call(AllTransactions allTransactions) {
                        updateView(allTransactions);
                    }
                });
    }

    private void updateView(final AllTransactions allTransactions){
        if(getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (allTransactions != null) {
                        homePageProductsAdapter.setCountForTransaction(allTransactions.getCountForTransaction());
                        homePageProductsAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "There are no Transaction Specified", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
