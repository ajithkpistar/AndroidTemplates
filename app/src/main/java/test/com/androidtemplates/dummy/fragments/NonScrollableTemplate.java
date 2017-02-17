package test.com.androidtemplates.dummy.fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.TreeMap;

import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.adapter.ViewPagerAdapter;
import test.com.androidtemplates.dummy.pojo.Entity;
import test.com.androidtemplates.dummy.pojo.Variable;
import test.com.androidtemplates.dummy.util.AudioPlayerUtil;
import test.com.androidtemplates.dummy.util.EvaluateString;
import test.com.androidtemplates.util.LockableViewPager;


public class NonScrollableTemplate extends Fragment implements LockableViewPager.OnSwipeOutListener {
    private int displayHeight;
    private int displayWidth;

    public static LockableViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Entity> questions;
    private ArrayList<Variable> variables = new ArrayList<>();
    private int ppt_id;
    private TreeMap<Integer, Integer> nextEntityMap;
    private String audioUrl;

    public static AudioPlayerUtil audioPlayerUtil;
    public static EvaluateString evaluateString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.non_scroollable_template, container, false);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        displayHeight = displaymetrics.heightPixels;
        displayWidth = displaymetrics.widthPixels;
        nextEntityMap = new TreeMap<>();


        viewPager = (LockableViewPager) view.findViewById(R.id.view_pager);
        viewPager.setSwipeLocked(true);
        viewPager.setOnSwipeOutListener(this);
        try {
            Bundle bundle = getArguments();
            questions = (ArrayList<Entity>) bundle.getSerializable("questions");
            variables = (ArrayList<Variable>) bundle.getSerializable("variables");
            audioUrl = bundle.getString("audioUrl");

            if (audioUrl != null && !audioUrl.equalsIgnoreCase("")) {
                audioPlayerUtil = new AudioPlayerUtil(getContext(), audioUrl);
            }else if(audioPlayerUtil!=null){
                audioPlayerUtil=null;
            }

            for (int i = 0; i < questions.size(); i++) {
                for (Integer key : questions.get(i).getOptions().keySet()) {
                    nextEntityMap.put(questions.get(i).getId(), i);
                    break;
                }
            }


            evaluateString = new EvaluateString();
            if (variables != null && variables.size() > 0) {
                evaluateString.createValues(variables);
            }


            viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), questions, ppt_id, displayWidth, displayHeight, nextEntityMap, variables);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(0);

            if (audioPlayerUtil != null) {
                audioPlayerUtil.startPlay();
                try {
                    audioPlayerUtil.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            System.out.println("Stopppppppppppppped");
                            audioPlayerUtil.restart();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onSwipeOutAtStart() {

    }

    @Override
    public void onSwipeOutAtEnd() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (audioPlayerUtil != null && !audioPlayerUtil.isChildPlaying() && audioPlayerUtil.getMediaPlayer() != null && !audioPlayerUtil.getMediaPlayer().isPlaying()) {
            audioPlayerUtil.resumePlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (audioPlayerUtil != null && !audioPlayerUtil.isChildPlaying())
            audioPlayerUtil.pausePlay();
    }
}
