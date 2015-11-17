package org.janastu.annotationapp;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.codehaus.jackson.map.ObjectMapper;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import org.janastu.annotationapp.rest.*;
import org.janastu.annotationapp.tag.TagDictionary;

public class CardViewActivity extends Activity {

  //  private String[] drawerListViewItems ={"http://da.pantoto.org/api/files","http://da.pantoto.org/api/stn/radioactive"};

    private List<String> drawerListViewItems = new ArrayList<String> ();
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    StationRestData stationRestData;

    ListView lv;
    Context context;
    private static String LOGGER = "CardViewActivity";
   // RestStationDetails longOperation;
    RestStationDetailsAsyncTask restStationDetailsAsyncTask;
    TagDictionary tagDictionary;
    Button  showTag;
    JsonRestData user;
    String stationName;
    String stationUrl;
    ArrayList prgmName;
    CardViewActivity activityRef;
    RestStationDetails stationDetails;
    public static int [] prgmImages={R.drawable.emma,R.drawable.lavery,R.drawable.lillie,R.drawable.emma,R.drawable.lavery,R.drawable.lillie,R.drawable.emma,R.drawable.lavery };
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        context=this;
        activityRef  = this;
        stationDetails = new RestStationDetails();

        lv=(ListView) findViewById(R.id.listView);
       // showTag = (Button) findViewById(R.id.showTagsButton);
        //Show tag button
       // showTag

       // drawerListViewItems = getResources().getStringArray(R.array.items);
        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        //


        // 2. App Icon
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // 2.1 create ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );

        // 2.2 Set actionBarDrawerToggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        // 2.3 enable and show "up" arrow
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // just styling option
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        restStationDetailsAsyncTask = new RestStationDetailsAsyncTask();
        restStationDetailsAsyncTask.execute();

        drawerListView.setOnItemClickListener(new DrawerItemClickListener());


    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/

    ////

    class RestStationDetailsAsyncTask extends AsyncTask<String, Void, String> {
        String g = null;

        private final ProgressDialog dialog = new ProgressDialog(CardViewActivity.this);






        @Override
        protected String doInBackground(String... params) {
            List<Station> result = new ArrayList<Station>();

            try {
                g = SimpleHttpClient.executeHttpGet("http://da.pantoto.org/station.json");

                // make sure the quotes are escaped

                Log.d(LOGGER, "STATION DETAILS"+g);
                ObjectMapper mapper = new ObjectMapper();

                stationRestData = mapper.readValue(g,
                        StationRestData.class);




// get the Stri
            } catch (Exception e) {

                Log.e(LOGGER, "  EXCEPTION GETTING     "+e
                );
                e.printStackTrace();
            }


            return "Executed";
        }


    @Override
    protected void onPostExecute(String res ) {
            dialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    List <Station > stations  = stationRestData.getStations();
                     for(Station t : stations)
                     {
                         Log.d(LOGGER, "Adding"+ t);
                         drawerListViewItems.add( t.getStore());
                     }
                      drawerListView.setAdapter(new ArrayAdapter<String>(activityRef,
                                    R.layout.drawer_listview_item, drawerListViewItems));

                }
            });

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    ////

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<AudioAnnotationFile>> {
        private final ProgressDialog dialog = new ProgressDialog(CardViewActivity.this);
        String g = null;
        JsonRestData user;
        @Override
        protected void onPostExecute(List<AudioAnnotationFile> result) {

            final   List<AudioAnnotationFile> result1 = result;
            dialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    final ListView lv1 = (ListView) findViewById(R.id.listView);
                  lv1.setAdapter(new CustomAdapter(CardViewActivity.this, user.getFiles()));
                }
            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading Annotations...");
            dialog.show();
        }

        @Override
        protected List<AudioAnnotationFile> doInBackground(String... params) {
            List<AudioAnnotationFile> result = new ArrayList<AudioAnnotationFile>();

            try {
                g = SimpleHttpClient.executeHttpGet(stationUrl);
                Log.d(LOGGER, " DATA FROM SERVER    "+g );
                // make sure the quotes are escaped
                ObjectMapper mapper = new ObjectMapper();

                user = mapper.readValue(g,
                        JsonRestData.class);

                tagDictionary = new TagDictionary(user);

                Log.d(LOGGER, "  SIZE OF THE ARAY    "
                        + user.getFiles().size());

                result = user.getFiles();


            } catch (Exception e) {

                Log.e(LOGGER, "  EXCEPTION GETTING     "
                );
                e.printStackTrace();
            }


                return result;
            }


    }

    // ADDED FOR OPTION MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    ////ADDED FOR NAVIGATION MENU


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId())
        {




            case R.id.menu_search:
                Toast.makeText(CardViewActivity.this, "Search is Selected", Toast.LENGTH_SHORT).show();
                // Show Activity that displays all tagsDirectory;

                Intent intent = new Intent(context , ShowAllTagsActivity.class);

                context.startActivity(intent);

                return true;

            case R.id.menu_share:
                Toast.makeText(CardViewActivity.this, "Share is Selected", Toast.LENGTH_SHORT).show();
                return true;



            case R.id.menu_preferences:
                Toast.makeText(CardViewActivity.this, "Preferences is Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

     //   return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(CardViewActivity.this, ((TextView)view).getText(), Toast.LENGTH_LONG).show();

            stationUrl =   ((TextView)view).getText().toString();
            AsyncListViewLoader aloader = new AsyncListViewLoader();
            aloader.execute();


            drawerLayout.closeDrawer(drawerListView);

        }
    }


}