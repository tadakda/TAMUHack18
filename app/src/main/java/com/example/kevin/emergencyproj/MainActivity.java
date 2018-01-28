package com.example.kevin.emergencyproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import android.util.Log;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String[] KEY_WORDS = {"earthquake"};
    private static final double SEARCH_RADIUS = 15;

    private double currentLat = -90, currentLong = 38;

    private boolean earthquakeFilter;
    private boolean floodingFilter;
    private boolean wildfireFilter;
    private boolean tornadoFilter;
    private boolean blizzardFilter;
    private boolean landslideFilter;

    @BindView(R.id.menu_earthquake) MenuItem earthquakeMenu;
    @BindView(R.id.menu_flooding) MenuItem floodingMenu;
    @BindView(R.id.menu_wildfire) MenuItem wildfireMenu;
    @BindView(R.id.menu_tornado) MenuItem tornadoMenu;
    @BindView(R.id.menu_blizzard) MenuItem blizzardMenu;
    @BindView(R.id.menu_landslide) MenuItem landslideMenu;

    private TwitterStream twitterStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTwitterStream();
    }

    interface TwitterAuth {
        String CONSUMER_KEY = "1rnXwetkZ2z0K7RHeTmj5iXak";
        String CONSUMER_SECRET = "6U13PEUn8toF50LBeMhf7TRffIWMmZVRysLzswdwtkXWIUIagM";
        String ACCESS_TOKEN = "854167713681375232-ruF3NOVDc3EVKZkCMUzGcLWDRMFhljr";
        String ACCESS_TOKEN_SECRET = "T6RsmfVZ7cj0H5MoLB9At4dfUNhL7OPw8sCTOP9wzUryY";
    }

    private void initTwitterStream() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(TwitterAuth.CONSUMER_KEY)
                .setOAuthConsumerSecret(TwitterAuth.CONSUMER_SECRET)
                .setOAuthAccessToken(TwitterAuth.ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(TwitterAuth.ACCESS_TOKEN_SECRET);

        twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        twitterStream.addListener(new StatusListener() {
            public void onStatus(Status status) {
                Log.e("Tweet received: ", status.getText() + " " + status.getGeoLocation().toString());
            }

            @Override
            public void onException(Exception ex) {}

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {}

            @Override
            public void onStallWarning(StallWarning warning) {}
        });

        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(KEY_WORDS);
        tweetFilterQuery.locations(
                new double[]{currentLat - SEARCH_RADIUS, currentLong - SEARCH_RADIUS},
                new double[]{currentLat + SEARCH_RADIUS, currentLong + SEARCH_RADIUS});
        tweetFilterQuery.language("en");
        twitterStream.filter(tweetFilterQuery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_earthquake:
                // earthquake was selected
                item.setChecked(!item.isChecked());
                earthquakeFilter = item.isChecked();
                break;
            case R.id.menu_flooding:
                // flooding was selected
                item.setChecked(!item.isChecked());
                floodingFilter = item.isChecked();
                break;
            case R.id.menu_wildfire:
                // wildfire was selected
                item.setChecked(!item.isChecked());
                wildfireFilter = item.isChecked();
                break;
            case R.id.menu_tornado:
                // tornado was selected
                item.setChecked(!item.isChecked());
                tornadoFilter = item.isChecked();
                break;
            case R.id.menu_blizzard:
                // blizzard was selected
                item.setChecked(!item.isChecked());
                blizzardFilter = item.isChecked();
                break;
            case R.id.menu_landslide:
                // landslide was selected
                item.setChecked(!item.isChecked());
                landslideFilter = item.isChecked();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        item.setActionView(new View(this));
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
        return false;
    }

    private boolean isChecked = false;

    public boolean onEarthquake(Menu menu) {
        earthquakeMenu.setChecked(isChecked);
        return true;
    }
    public boolean onFlooding(Menu menu) {
        floodingMenu.setChecked(isChecked);
        return true;
    }
    public boolean onWildfire(Menu menu) {
        wildfireMenu.setChecked(isChecked);
        return true;
    }
    public boolean onTornado(Menu menu) {
        tornadoMenu.setChecked(isChecked);
        return true;
    }
    public boolean onBlizzard(Menu menu) {
        blizzardMenu.setChecked(isChecked);
        return true;
    }
    public boolean onLandslide(Menu menu) {
        landslideMenu.setChecked(isChecked);
        return true;
    }

}

