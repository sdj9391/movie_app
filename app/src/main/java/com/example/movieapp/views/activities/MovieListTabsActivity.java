package com.example.movieapp.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.movieapp.R;
import com.example.movieapp.views.fragments.FavouriteMoviesFragment;
import com.example.movieapp.views.fragments.PopularMoviesFragment;
import com.example.movieapp.views.fragments.TopRatedMoviesFragment;
import com.google.android.material.tabs.TabLayout;

public class MovieListTabsActivity extends AppCompatActivity {

    private static final String TAG = "MovieListTabsActivity";

    private static final int TAB_POPULAR = 0;
    private static final int TAB_TOP_RATED = 1;
    private static final int TAB_FAVOURITE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        String[] titles = getResources().getStringArray(R.array.cases_tabs);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(this
                .getSupportFragmentManager(), titles);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
        private String[] titles;

        FragmentPagerAdapter(FragmentManager fragmentManager,
                             String[] titles) {
            super(fragmentManager);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int itemId) {
            Fragment fragment;

            switch (itemId) {
                case TAB_POPULAR:
                    fragment = new PopularMoviesFragment();
                    break;

                case TAB_TOP_RATED:
                    fragment = new TopRatedMoviesFragment();
                    break;

                case TAB_FAVOURITE:
                    fragment = new FavouriteMoviesFragment();
                    break;

                default:
                    Log.e(TAG, "Unknown item id.");
                    fragment = new Fragment();
                    break;
            }

            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}