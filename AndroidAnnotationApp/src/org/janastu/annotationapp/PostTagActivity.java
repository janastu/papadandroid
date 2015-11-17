package org.janastu.annotationapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;


import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import org.janastu.annotationapp.rest.PostTagOperation;
import org.janastu.annotationapp.tag.TagDictionary;

import java.util.ArrayList;
import java.util.List;

/* Find all files with the TAG*/
/**
 * Shows a list that can be filtered in-place with a SearchView in non-iconified mode.
 */
public class PostTagActivity extends Activity implements SearchView.OnQueryTextListener {
    private static String LOGGER = "PostTagActivity";

    private SearchView mSearchView;
    private ListView mListView;
    String selectedTag;
    private ArrayList<String> mStrings  ;
    private String tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStrings =new TagDictionary().getTagsDirectory();

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.posttagactivity);
//get Id

        Bundle b = getIntent().getExtras();
        tagId          = b.getString("keyID");
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mStrings));
        mListView.setTextFilterEnabled(true);
        mSearchView.setSubmitButtonEnabled(true);



        LinearLayout linearLayoutOfSearchView = (LinearLayout) mSearchView.getChildAt(0);
        Button yourButton = new Button(this); // and do whatever to your button

        yourButton.setText("ADD");
        linearLayoutOfSearchView.addView(yourButton);

        yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
///selectedTag

              //  executeHttpPost
                PostTagOperation postTagOperation = new PostTagOperation();
                postTagOperation.setId(tagId);
                postTagOperation.setTag(selectedTag);
                postTagOperation.execute();
                Toast.makeText(getBaseContext(), selectedTag, Toast.LENGTH_LONG).show();

            }
        });


        setupSearchView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();
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

        Log.d(LOGGER,"onQueryTextChange newText str"+newText);
        selectedTag = newText;
        return true;
    }

    public boolean onQueryTextSubmit(String query)
    {

        Log.d(LOGGER,"onQueryTextSubmit query str"+query);
        return false;
    }
}
