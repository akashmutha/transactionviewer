package com.badoo.transactionviewer.ui.ProductDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.badoo.transactionviewer.R;
import com.badoo.transactionviewer.model.Transaction;
import com.badoo.transactionviewer.util.Constants;

import java.util.ArrayList;

/**
 * Created by mutha on 25/06/16.
 */
public class ProductDetailsActivity extends AppCompatActivity {

    private ArrayList<Transaction> allTransactions;
    private String productKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        if(getIntent() != null){
            allTransactions = (ArrayList)getIntent().getSerializableExtra(Constants.INTENT_FOR_PRODUCT_DETAILS);
            productKey = (String)getIntent().getExtras().getString(Constants.PRODUCT_KEY);
        }
        getSupportActionBar().setTitle("Transaction for " + productKey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    public ArrayList<Transaction> getTransactionList(){
        return this.allTransactions;
    }
}
