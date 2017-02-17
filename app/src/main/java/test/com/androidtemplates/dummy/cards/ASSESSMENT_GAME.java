package test.com.androidtemplates.dummy.cards;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.HashMap;
import java.util.TreeMap;

import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.fragments.NonScrollableTemplate;
import test.com.androidtemplates.dummy.pojo.Entity;
import test.com.androidtemplates.dummy.pojo.EntityOption;
import test.com.androidtemplates.dummy.util.AssementUtil;
import test.com.androidtemplates.dummy.util.AudioPlayerUtil;
import test.com.androidtemplates.util.CustomLayout;


/**
 * Created by Sumanth on 11/23/2016.
 */
public class ASSESSMENT_GAME extends Fragment implements View.OnDragListener, View.OnTouchListener, View.OnClickListener {

    private Context context;
    int resolutionX = 1000;
    int resolutionY = 1000;

    int xCount = 4;
    int yCount = 5;

    float gridWidth, gridHeight;

    private RelativeLayout verticalLayout;
    private Entity card_entity;
    private TreeMap<Integer, Integer> nextEntityMap;

    private HashMap<Integer, CustomLayout> dropZoneViews = new HashMap<>();
    private HashMap<CustomLayout, Boolean> dargZoneViews = new HashMap<>();
    private AssementUtil assementUtil;
    private HashMap<CustomLayout, EntityOption> entityOptionHashMap = new HashMap<>();
    private RelativeLayout main_content;
    private CustomLayout main_layout;

    private Runnable runnable;

    private DialogFragmentWindow dialogWindow;
    private View draggedView;

    private boolean isdragDropped = false;
    private LovelyCustomDialog customDialog;

    private HashMap<Integer, AudioPlayerUtil> playerUtilHashMap = new HashMap<>();
    private ImageButton score_button;
    private DialogScoreWindow dialogScoreWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getContext();
        View view = inflater.inflate(R.layout.assessment_game, container, false);
        main_layout = (CustomLayout) view.findViewById(R.id.main_layout);
        main_content = (RelativeLayout) view.findViewById(R.id.main_content);
        score_button = (ImageButton) view.findViewById(R.id.score_button);

        assementUtil = new AssementUtil(context, null);
        nextEntityMap = new TreeMap<>();
        customDialog = new LovelyCustomDialog(context);

        if (NonScrollableTemplate.evaluateString != null && NonScrollableTemplate.evaluateString.getIntegerIntegerHashMap() != null && NonScrollableTemplate.evaluateString.getIntegerIntegerHashMap().size() > 0) {
            score_button.setVisibility(View.VISIBLE);
            score_button.setOnClickListener(this);
        }

        try {

            Bundle bundle = getArguments();

            card_entity = (Entity) bundle.getSerializable("CARDS");
            resolutionX = bundle.getInt("display_width");
            resolutionY = bundle.getInt("display_height");
            nextEntityMap = (TreeMap<Integer, Integer>) bundle.getSerializable("nextEntityMap");

            gridWidth = resolutionX / xCount;
            gridHeight = resolutionY / yCount;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (card_entity != null) {
            playerUtilHashMap = new HashMap<>();

            for (Integer key : card_entity.getOptions().keySet()) {
                playerUtilHashMap.put(card_entity.getOptions().get(key).getId(), new AudioPlayerUtil(context, card_entity.getOptions().get(key).getMediaUrl()));
            }


            createGridLayout();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {

            }

        } catch (Exception e) {
        }
    }

    public void createGridLayout() {

        assementUtil.setUpCardBG(main_layout, card_entity);


        verticalLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams vertical_param = new LinearLayout.LayoutParams(resolutionX, resolutionY);
        verticalLayout.setLayoutParams(vertical_param);
        verticalLayout.setBackgroundColor(Color.TRANSPARENT);

        dropZoneViews = new HashMap<>();
        dargZoneViews = new HashMap<>();
        boolean clickable = false;
        if (card_entity.getActionType().equalsIgnoreCase("TAP")) {
            clickable = true;
        }

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        try {
            startX = (int) gridWidth * ((Integer.parseInt(card_entity.getGrid().split(",")[0])) % xCount);
            startY = (int) gridHeight * ((Integer.parseInt(card_entity.getGrid().split(",")[1])) % yCount);
            endX = (int) gridWidth * Integer.parseInt(card_entity.getGrid().split(",")[2]);
            endY = (int) gridHeight * Integer.parseInt(card_entity.getGrid().split(",")[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomLayout relativeLayout;
        if (clickable) {
            relativeLayout = createEntities(startX, startY, endX, endY, false, false, false, card_entity.getId() + "");
        } else {
            relativeLayout = createEntities(startX, startY, endX, endY, false, true, clickable, card_entity.getId() + "");
        }

        assementUtil.setupCardBackground(relativeLayout, card_entity);
        assementUtil.setQuestionCardText(relativeLayout, card_entity);

        dropZoneViews.put(card_entity.getId(), relativeLayout);

        verticalLayout.addView(relativeLayout);

        for (Integer key : card_entity.getOptions().keySet()) {

            int opt_startX = 0;
            int opt_startY = 0;
            int opt_endX = 0;
            int opt_endY = 0;
            try {
                opt_startX = (int) gridWidth * ((Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[0])) % xCount);
                opt_startY = (int) gridHeight * ((Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[1])) % yCount);
                opt_endX = (int) gridWidth * Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[2]);
                opt_endY = (int) gridHeight * Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[3]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            CustomLayout customLayout;
            if (clickable) {
                customLayout = createEntities(opt_startX, opt_startY, opt_endX, opt_endY, false, false, true, card_entity.getId() + "");
            } else {
                customLayout = createEntities(opt_startX, opt_startY, opt_endX, opt_endY, true, false, false, card_entity.getId() + "");
            }

            assementUtil.setupOptionCardBackground(customLayout, card_entity.getOptions().get(key));
            assementUtil.setOptionCardText(customLayout, card_entity.getOptions().get(key));
            assementUtil.setupOptionCardBackground(customLayout, card_entity.getOptions().get(key));

            dargZoneViews.put(customLayout, card_entity.getOptions().get(key).isCorrect());
            entityOptionHashMap.put(customLayout, card_entity.getOptions().get(key));

            verticalLayout.addView(customLayout);
        }

        main_content.addView(verticalLayout);
    }


    public CustomLayout createEntities(int startX, int startY, int width, int height, boolean dragable, boolean dragZoneable, boolean clickable, String tag) {

        CustomLayout relativeLayout = new CustomLayout(context);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width, height);
        param.leftMargin = startX;
        param.topMargin = startY;
        relativeLayout.setLayoutParams(param);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            int viewId = View.generateViewId();
            relativeLayout.setId(viewId);
        }


        relativeLayout.setTag(tag);

        TextView textView = new TextView(context);
        textView.setTextColor(Color.TRANSPARENT);
        textView.setText(startX / gridWidth + "_" + startY / gridHeight + "");
        relativeLayout.addView(textView);


        if (dragable) {
            relativeLayout.setOnTouchListener(this);
        }

        if (dragZoneable) {
            relativeLayout.setOnDragListener(this);
        }

        if (clickable) {
            relativeLayout.setOnClickListener(this);
        }

        return relativeLayout;
    }


    @Override
    public boolean onDrag(View view, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:

                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                break;

            case DragEvent.ACTION_DRAG_ENDED:

            {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (!isdragDropped)
                                draggedView.setVisibility(View.VISIBLE);
                            else
                                draggedView.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }

            case DragEvent.ACTION_DROP:
                try {
                    if (draggedView.getTag().toString().equalsIgnoreCase(view.getTag().toString())) {
                        System.out.println("Droppped");
                        isdragDropped = true;
                        Entity questionCard = null;

                        if (Integer.parseInt(view.getTag().toString()) == card_entity.getId()) {
                            questionCard = card_entity;
                        }

                        evaluateAnswer(draggedView, questionCard);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void evaluateAnswer(View view, Entity questionCard) {

        try {
            for (Integer key : card_entity.getOptions().keySet()) {
                AudioPlayerUtil audioPlayerUtil = playerUtilHashMap.get(card_entity.getOptions().get(key).getId());
                if (audioPlayerUtil != null && audioPlayerUtil.getMediaPlayer() != null && audioPlayerUtil.getMediaPlayer().isPlaying()) {
                    audioPlayerUtil.stop();
                }
            }

            MediaPlayer mediaPlayer = playerUtilHashMap.get(entityOptionHashMap.get(view).getId()).getMediaPlayer();
            if (mediaPlayer != null) {

                if (NonScrollableTemplate.audioPlayerUtil != null) {
                    NonScrollableTemplate.audioPlayerUtil.pausePlay();
                    NonScrollableTemplate.audioPlayerUtil.setChildPlaying(true);
                }
                mediaPlayer.start();


                System.out.println("Started Option audio");
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        System.out.println("Stopppppppppppppped Option audio");
                        if (NonScrollableTemplate.audioPlayerUtil != null) {
                            NonScrollableTemplate.audioPlayerUtil.setChildPlaying(false);
                            NonScrollableTemplate.audioPlayerUtil.resumePlay();
                        }
                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (dargZoneViews.get(view)) {
            showDiloagMessage(questionCard.getCorrectMessage(), true, entityOptionHashMap.get(view));
        } else {
            showDiloagMessage(questionCard.getIncorrectMessage(), false, entityOptionHashMap.get(view));
        }
    }

    private void showDiloagMessage(final String message, boolean action, final EntityOption entityOption) {

        if (entityOption.getEvaluationScript() != null) {
            NonScrollableTemplate.evaluateString.evaluateExpression(entityOption.getEvaluationScript());
        }

        View view; // Creating an instance for View Object
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.message_dilog, null);

        String title;

        @ColorRes int color;
        if (action) {
            title = "Awesome";
            color = R.color.success_color;
        } else {
            title = "Its' Okie ,No Worry!!";
            color = R.color.failure_color;
        }

        if(message!=null && !message.equalsIgnoreCase("")) {
            customDialog.setView(view)
                    .setTopColorRes(color)
                    .setTitle(title)
                    .setMessage(message)
                    .setIcon(R.drawable.ic_assignment_white_36dp)
                    .show();

            new CountDownTimer(1000, 500) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    if(message!=null && !message.equalsIgnoreCase("")) {
                        customDialog.dismiss();
                    }
                    updateUI(entityOption);
                }
            }.start();
        }else{
            updateUI(entityOption);
        }
    }


    private void updateUI(final EntityOption entityOption) {

        if (entityOption.getCards().size() > 0) {
            dialogWindow = new DialogFragmentWindow();
            Bundle bundle = new Bundle();
            bundle.putInt("displayWidth", resolutionX);
            bundle.putInt("displayHeight", resolutionY);
            bundle.putSerializable("entity_options", entityOption);
            dialogWindow.setArguments(bundle);
            dialogWindow.show(getChildFragmentManager(), "");
            final Handler uiHandler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (dialogWindow != null && dialogWindow.isVisible()) {
                            System.out.println("visible");
                            uiHandler.postDelayed(runnable, 1000);
                        } else {
                            System.out.println("not visible");
                            NonScrollableTemplate.viewPager.setCurrentItem(nextEntityMap.get(entityOption.getNextEntity()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            uiHandler.postDelayed(runnable, 1500);
        } else {
            try {
                NonScrollableTemplate.viewPager.setCurrentItem(nextEntityMap.get(entityOption.getNextEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            try {

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                draggedView = view;
                isdragDropped = false;
                view.setVisibility(View.INVISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == score_button.getId()) {
            dialogScoreWindow = new DialogScoreWindow();

            Bundle bundle = new Bundle();
            bundle.putInt("displayWidth", resolutionX);
            bundle.putInt("displayHeight", resolutionY);
            bundle.putSerializable("variables", NonScrollableTemplate.evaluateString.getIntegerIntegerHashMap());
            dialogScoreWindow.setArguments(bundle);

            dialogScoreWindow.show(getChildFragmentManager(), "");

        } else {

            try {
                Entity questionCard = null;

                if (Integer.parseInt(view.getTag().toString()) == card_entity.getId()) {
                    questionCard = card_entity;
                }

                evaluateAnswer(view, questionCard);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        for (Integer key : card_entity.getOptions().keySet()) {
            AudioPlayerUtil audioPlayerUtil = playerUtilHashMap.get(card_entity.getOptions().get(key).getId());
            if (audioPlayerUtil.isChildPlaying()) {
                audioPlayerUtil.setChildPlaying(false);
                audioPlayerUtil.resumePlay();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialogWindow != null) {
            dialogWindow.dismiss();
        }

        for (Integer key : card_entity.getOptions().keySet()) {
            AudioPlayerUtil audioPlayerUtil = playerUtilHashMap.get(card_entity.getOptions().get(key).getId());
            if (audioPlayerUtil != null && audioPlayerUtil.getMediaPlayer() != null && audioPlayerUtil.getMediaPlayer().isPlaying()) {
                audioPlayerUtil.setChildPlaying(true);
                audioPlayerUtil.pausePlay();
            }
        }

    }
}
