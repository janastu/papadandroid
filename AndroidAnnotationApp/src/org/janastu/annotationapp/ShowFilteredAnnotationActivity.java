package org.janastu.annotationapp;

import android.app.Activity;
import android.os.Bundle;

        import android.view.Menu;

        import java.util.ArrayList;
        import java.util.List;


        import android.content.Context;

        import android.widget.ListView;

import org.janastu.annotationapp.rest.AudioAnnotationFile;
import org.janastu.annotationapp.rest.JsonRestData;
import org.janastu.annotationapp.rest.RestStationDetails;
import org.janastu.annotationapp.tag.TagDictionary;

public class ShowFilteredAnnotationActivity extends Activity {

    ListView lv;
    Context context;
    private static String LOGGER = "ShowFilteredAnnotationActivity";
    RestStationDetails longOperation;

    JsonRestData user;
    ArrayList prgmName;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfilteredannotation_view);
        context=this;


          Bundle b = getIntent().getExtras();
          String selectedTag = b.getString("searchTag");
          final ListView lv1 = (ListView) findViewById(R.id.listView);
          List<AudioAnnotationFile> annotFiles =  TagDictionary.findTag(selectedTag);
          lv1.setAdapter(new AnnotationFileAdaptor(ShowFilteredAnnotationActivity.this, annotFiles));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}