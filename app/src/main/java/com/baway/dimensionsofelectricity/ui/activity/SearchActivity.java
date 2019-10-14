package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.MessageEvent;
import com.baway.dimensionsofelectricity.ui.widget.CustomSearchBox;
import com.baway.dimensionsofelectricity.ui.widget.FlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.custom_search_box)
    CustomSearchBox customSearchBox;
    @BindView(R.id.custom_flow_layout)
    FlowLayout customFlowLayout;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Toast.makeText(this, "" + list.toString(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(SearchActivity.this);
            textView.setText(list.get(i));
            textView.setBackgroundResource(R.drawable.search_item_border);
            customFlowLayout.addView(textView);
        }
        customSearchBox.setOnClickSearchBoxListener(new CustomSearchBox.OnClickSearchBoxListener() {
            @Override
            public void searchResult(String content) {
                list.add(content);

                TextView textView = new TextView(SearchActivity.this);
                textView.setText(content);
                textView.setBackgroundResource(R.drawable.search_item_border);
                customFlowLayout.addView(textView);

                MessageEvent event = new MessageEvent();
                event.setContent(content);
                EventBus.getDefault().postSticky(event);
                Intent intent = new Intent(SearchActivity.this, SearchlistingsActivity.class);
                startActivity(intent);
            }

            @Override
            public void search() {

            }

            @Override
            public void moreResult() {

            }
        });
    }
}
