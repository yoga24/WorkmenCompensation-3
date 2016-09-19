package com.cyorg.uiic.workmencompensationrainbow3.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cyorg.uiic.workmencompensationrainbow3.R;
import com.cyorg.uiic.workmencompensationrainbow3.adapter.CustomPdfGenerator;
import com.cyorg.uiic.workmencompensationrainbow3.adapter.WcRecyclerAdapter;
import com.cyorg.uiic.workmencompensationrainbow3.utils.CommonUtils;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SummaryActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView summInsurerName;
    private TextView summRate;
    private TextView summDiscount;
    private RecyclerView summRecyclerView;

    private FloatingActionButton summFab;

    private WcRecyclerAdapter recyclerAdapter;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        toolbar = (Toolbar) findViewById(R.id.summ_toolbar);
        summInsurerName = (TextView) findViewById(R.id.summ_text_insurer);
        summRate = (TextView) findViewById(R.id.summ_text_rate);
        summDiscount = (TextView) findViewById(R.id.summ_text_discount);
        summRecyclerView = (RecyclerView) findViewById(R.id.summ_recycler);
        summFab = (FloatingActionButton) findViewById(R.id.summ_fab);

        summInsurerName.setText(CommonUtils.insurerName);
        summRate.setText(String.valueOf(CommonUtils.rate));
        summDiscount.setText(String.valueOf(CommonUtils.discount));

        setSupportActionBar(toolbar);

        summFab.setOnClickListener(this);

        recyclerAdapter = new WcRecyclerAdapter();
        summRecyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager reLayoutManager = new LinearLayoutManager(this);
        reLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        summRecyclerView.setLayoutManager(reLayoutManager);

        summRecyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar.inflateMenu(R.menu.menu_toolbar_summ);
        //Read the share menu item
        MenuItem shareItem = menu.findItem(R.id.summ_tb_share);
        //Assign EasyShareAction to SHARE MenuItem
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        //Custom File to write share history
        mShareActionProvider.setShareHistoryFileName("custom_share_history.xml");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.summ_tb_share:
                CommonUtils.finalizeCalculation();
                mShareActionProvider.setShareIntent(createShareIntent());
                break;

            case R.id.summ_tb_delete:
                Toast.makeText(this, "Deletion in progress", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SingleEntryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(SummaryActivity.this, "Action Not Supported", Toast.LENGTH_SHORT).show();
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + CustomPdfGenerator.generatePdf(this.getApplicationContext())));
        return shareIntent;
    }
}
