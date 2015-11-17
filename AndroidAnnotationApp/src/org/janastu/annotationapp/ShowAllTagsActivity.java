package org.janastu.annotationapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import org.janastu.annotationapp.rest.AudioAnnotationFile;
import org.janastu.annotationapp.rest.PostTagOperation;
import org.janastu.annotationapp.tag.TagDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Graphics-User on 11/17/2015.
 */


public class ShowAllTagsActivity extends Activity implements SearchView.OnQueryTextListener {
    private static String LOGGER = "ShowAllTagsActivity";
    private SearchView mSearchView;
    private ListView mListView;
    Context context;
    String selectedTag;

    private final List<String> mStrings = new TagDictionary().getTagsDirectory();
    private String tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        context = this;
        setContentView(R.layout.showalltagactivity);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mStrings));
        mListView.setTextFilterEnabled(true);
        mSearchView.setSubmitButtonEnabled(true);
        LinearLayout linearLayoutOfSearchView = (LinearLayout) mSearchView.getChildAt(0);
        Button yourButton = new Button(this); // and do whatever to your button

        yourButton.setText("SEARCH");
        linearLayoutOfSearchView.addView(yourButton);

        yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
///selectedTag
                Log.d(LOGGER,"findinfg"+selectedTag);
                Intent intent = new Intent(context , ShowFilteredAnnotationActivity.class);
                Bundle b = new Bundle();
                b.putString ("searchTag", selectedTag ); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                context.startActivity(intent);
            }
        });


        setupSearchView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                //  Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                // mSearchView.set.setFilterText(item.toString());

                mSearchView.setQuery(item, false);
            }
        });



    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }

        selectedTag = newText;
        return true;
    }

    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }
}
