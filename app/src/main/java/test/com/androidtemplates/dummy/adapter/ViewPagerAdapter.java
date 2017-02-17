package test.com.androidtemplates.dummy.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.TreeMap;

import test.com.androidtemplates.dummy.cards.ASSESSMENT_GAME;
import test.com.androidtemplates.dummy.pojo.Entity;
import test.com.androidtemplates.dummy.pojo.Variable;


/**
 * Created by ajith on 10-02-2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Entity> entities;
    private int ppt_id;
    private int displayWidth, displayHeight;
    private TreeMap<Integer, Integer> nextEntityMap;
    private ArrayList<Variable> variables;

    public ViewPagerAdapter(FragmentManager childFragmentManager, ArrayList<Entity> questions, int ppt_id, int displayWidth, int displayHeight, TreeMap<Integer, Integer> nextEntityMap, ArrayList<Variable> variables) {
        super(childFragmentManager);
        this.entities = questions;
        this.ppt_id = ppt_id;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.nextEntityMap = nextEntityMap;
        this.variables = variables;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        final Bundle bundle = new Bundle();


        bundle.putInt("PPT_ID", ppt_id);

        bundle.putInt("display_width", displayWidth);
        bundle.putInt("display_height", displayHeight);

        bundle.putSerializable("CARDS", entities.get(position));
        bundle.putSerializable("nextEntityMap", nextEntityMap);
        bundle.putSerializable("variables", variables);
        fragment = new ASSESSMENT_GAME();


        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


    @Override
    public void destroyItem(View collection, int position, Object o) {
        View view = (View) o;
        ((ViewPager) collection).removeView(view);
        view = null;
    }

}
