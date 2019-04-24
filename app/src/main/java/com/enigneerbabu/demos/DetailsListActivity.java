package com.enigneerbabu.demos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enigneerbabu.demos.adapter.ImageAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.enigneerbabu.demos.volley.Const.URLBASE;


public class DetailsListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rv_service_list;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list);
        mContext = this;
        initViews();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(DetailsListActivity.this);
        mViewPager.setAdapter(adapterView);

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        rv_service_list = (RecyclerView) findViewById(R.id.recycler);



        Map<String, String> params = new HashMap<>();
        params.put("action", "providerServices");
        params.put("type", "2");
        getservicelistname(params);
    }

    private void getservicelistname(final Map<String,String> params) {
        final ProgressDialog pdialog = new ProgressDialog(mContext);
        pdialog.setCancelable(true);
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLBASE,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        Log.e("CouponResponse", "====>" + response);

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson  = builder.create();




                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //displaying the error in toast if occurrs
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                pdialog.dismiss();
                Log.e("SENDINGesponse", "--->" + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

}
