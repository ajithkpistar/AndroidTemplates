package test.com.androidtemplates.dummy.fragments;


import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.ArrayList;
import java.util.HashMap;

import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.cards.DialogFragmentWindow;
import test.com.androidtemplates.dummy.pojo.Entity;
import test.com.androidtemplates.dummy.pojo.EntityOption;
import test.com.androidtemplates.dummy.pojo.PPT;
import test.com.androidtemplates.dummy.pojo.Variable;
import test.com.androidtemplates.dummy.util.AssementUtil;
import test.com.androidtemplates.dummy.util.AudioPlayerUtil;
import test.com.androidtemplates.dummy.util.EvaluateString;
import test.com.androidtemplates.util.CustomLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScroollabelTemplate extends Fragment implements View.OnDragListener, View.OnTouchListener, View.OnClickListener {
    Context context;
    int displayHeight;
    int displayWidth;

    int resolutionX = 1000;
    int resolutionY = 1000;

    int xCount = 4;
    int yCount = 4;

    float gridWidth, gridHeight;

    private HorizontalScrollView horizontalview;
    private CustomLayout main_content;


    private HashMap<Integer, CustomLayout> dropZoneViews = new HashMap<>();
    private HashMap<CustomLayout, Boolean> dargZoneViews = new HashMap<>();
    private HashMap<CustomLayout, EntityOption> entityOptionHashMap = new HashMap<>();

    private RelativeLayout verticalLayout;
    private PPT presentation_canvas;
    private AssementUtil assementUtil;
    private View draggedView;
    private boolean isdragDropped = false;
    private LovelyCustomDialog customDialog;
    int scrollbleLeft, scrollableRight;
    private DialogFragmentWindow dialogWindow;
    private Runnable runnable;


    private ArrayList<Variable> variables = new ArrayList<>();
    private EvaluateString evaluateString;
    private AudioPlayerUtil presenetationAudioUtil;

    private HashMap<Integer, AudioPlayerUtil> playerUtilHashMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scroolbale_template, container, false);
        context = getContext();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        displayHeight = displaymetrics.heightPixels;
        displayWidth = displaymetrics.widthPixels;


        horizontalview = (HorizontalScrollView) view.findViewById(R.id.horizontalview);
        main_content = (CustomLayout) view.findViewById(R.id.main_content);
        customDialog = new LovelyCustomDialog(context);

        try {
            Bundle bundle;
            if (getArguments() != null) {
                bundle = getArguments();
                presentation_canvas = (PPT) bundle.getSerializable("presentation");
                assementUtil = new AssementUtil(context, presentation_canvas);
                evaluateString = new EvaluateString();

                if (presentation_canvas.getVariables() != null && presentation_canvas.getVariables().size() > 0) {
                    variables = presentation_canvas.getVariables();
                    evaluateString.createValues(variables);
                }

                if (presentation_canvas.getAudioUrl() != null && !presentation_canvas.getAudioUrl().equalsIgnoreCase("")) {
                    presenetationAudioUtil = new AudioPlayerUtil(getContext(), presentation_canvas.getAudioUrl());

                    presenetationAudioUtil.startPlay();
                    try {
                        presenetationAudioUtil.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                System.out.println("Stopppppppppppppped");
                                presenetationAudioUtil.restart();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                setupView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    private void setupView() {

        xCount = presentation_canvas.getGridX();
        yCount = presentation_canvas.getGridY();


        if (xCount <= 4) {
            resolutionX = displayWidth;
        } else {
            resolutionX = xCount * (displayWidth / 4);
        }

        if (yCount <= 5) {
            resolutionY = displayHeight;
        } else {
            resolutionY = yCount * (displayHeight / 5);
        }


        assementUtil.setupBackground(main_content, resolutionX, resolutionY);

        gridWidth = resolutionX / xCount;
        gridHeight = resolutionY / yCount;

        System.out.println(displayWidth + " :width height: " + displayHeight + " resolutionX-------" + resolutionX + " resolutionY----" + resolutionY + " xCount---" + xCount + "yCount---" + yCount);
        createGridLayout();
    }


    public void createGridLayout() {

        verticalLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams vertical_param = new LinearLayout.LayoutParams(resolutionX, resolutionY);
        verticalLayout.setLayoutParams(vertical_param);
        verticalLayout.setBackgroundColor(Color.TRANSPARENT);

        dropZoneViews = new HashMap<>();
        dargZoneViews = new HashMap<>();
        playerUtilHashMap = new HashMap<>();
        for (Entity card_entity : presentation_canvas.getQuestions()) {
            boolean clickable = false;
            if (card_entity.getActionType().equalsIgnoreCase("TAP")) {
                clickable = true;
            }

            int startX = (int) gridWidth * Integer.parseInt(card_entity.getGrid().split(",")[0]);
            int startY = (int) gridHeight * Integer.parseInt(card_entity.getGrid().split(",")[1]);
            int endX = (int) gridWidth * Integer.parseInt(card_entity.getGrid().split(",")[2]);
            int endY = (int) gridHeight * Integer.parseInt(card_entity.getGrid().split(",")[3]);

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

                int opt_startX = (int) gridWidth * Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[0]);
                int opt_startY = (int) gridHeight * Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[1]);
                int opt_endX = (int) gridWidth * Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[2]);
                int opt_endY = (int) gridHeight * Integer.parseInt(card_entity.getOptions().get(key).getGrid().split(",")[3]);

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

                if (card_entity.getOptions().get(key).getMediaUrl() != null && !card_entity.getOptions().get(key).getMediaUrl().equalsIgnoreCase("")) {
                    playerUtilHashMap.put(card_entity.getOptions().get(key).getId(), new AudioPlayerUtil(context, card_entity.getOptions().get(key).getMediaUrl()));
                }

            }
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


    private void evaluateAnswer(View view, Entity questionCard) {

        try {

            for (Entity card_entity : presentation_canvas.getQuestions()) {
                for (Integer key : card_entity.getOptions().keySet()) {
                    AudioPlayerUtil audioPlayerUtil = playerUtilHashMap.get(card_entity.getOptions().get(key).getId());
                    if (audioPlayerUtil != null && audioPlayerUtil.getMediaPlayer() != null && audioPlayerUtil.getMediaPlayer().isPlaying()) {
                        audioPlayerUtil.stop();
                    }
                }
            }


            MediaPlayer mediaPlayer = playerUtilHashMap.get(entityOptionHashMap.get(view).getId()).getMediaPlayer();
            if (mediaPlayer != null) {

                if(presenetationAudioUtil!=null) {
                    presenetationAudioUtil.pausePlay();
                    presenetationAudioUtil.setChildPlaying(true);
                }
                mediaPlayer.start();


                System.out.println("Started Option audio");
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        System.out.println("Stopppppppppppppped Option audio");
                        if(presenetationAudioUtil!=null) {
                            presenetationAudioUtil.setChildPlaying(false);
                            presenetationAudioUtil.resumePlay();
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

    private void showDiloagMessage(String message, boolean action, final EntityOption entityOption) {

        if (entityOption.getEvaluationScript() != null) {
            evaluateString.evaluateExpression(entityOption.getEvaluationScript());
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
                customDialog.dismiss();
                updateUI(entityOption);

            }
        }.start();

    }

    private void updateUI(final EntityOption entityOption) {

        if (entityOption.getCards().size() > 0) {
            dialogWindow = new DialogFragmentWindow();
            Bundle bundle = new Bundle();
            bundle.putInt("displayWidth", displayWidth);
            bundle.putInt("displayHeight", displayHeight);
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

                            for (Integer key : dropZoneViews.keySet()) {
                                CustomLayout customLayout = dropZoneViews.get(key);
                                if (Integer.parseInt(customLayout.getTag().toString()) == entityOption.getNextEntity()) {
                                    scrollbleLeft = customLayout.getLeft();
                                    scrollableRight = customLayout.getRight();


                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int sWidth = horizontalview.getWidth();
                                            horizontalview.smoothScrollTo(((scrollbleLeft + scrollableRight - sWidth) / 2), 0);
                                        }
                                    });
                                }


                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            uiHandler.postDelayed(runnable, 1500);
        } else {
            try {
                for (Integer key : dropZoneViews.keySet()) {
                    CustomLayout customLayout = dropZoneViews.get(key);
                    if (Integer.parseInt(customLayout.getTag().toString()) == entityOption.getNextEntity()) {
                        scrollbleLeft = customLayout.getLeft();
                        scrollableRight = customLayout.getRight();


                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                int sWidth = horizontalview.getWidth();
                                horizontalview.smoothScrollTo(((scrollbleLeft + scrollableRight - sWidth) / 2), 0);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public boolean onDrag(View view, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:

                /*int topOfDropZone =dropZoneViews.get(draggedView.getTag().toString()).getLeft();
                int bottomOfDropZone = dropZoneViews.get(draggedView.getTag().toString()).getRight();

                int scrollx = horizontalview.getScrollX();
                int scrollViewWidth = horizontalview.getMeasuredWidth();

                Log.d("scrollllll","location: Scroll X: "+ scrollx + " Scroll Y+Height: "+(scrollx + scrollViewWidth));
                Log.d("scrollllll"," top: "+ topOfDropZone +" bottom: "+bottomOfDropZone);

                if (bottomOfDropZone > (scrollx + scrollViewWidth - 100))
                    horizontalview.smoothScrollBy(30, 00);

                if (topOfDropZone < (scrollx + 100))
                    horizontalview.smoothScrollBy(-30, 00);*/

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
                        if (!isdragDropped)
                            draggedView.setVisibility(View.VISIBLE);
                        else
                            draggedView.setVisibility(View.GONE);
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
                        for (Entity card_entity : presentation_canvas.getQuestions()) {
                            if (Integer.parseInt(view.getTag().toString()) == card_entity.getId()) {
                                questionCard = card_entity;
                            }
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
        }else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        try {
            Entity questionCard = null;
            for (Entity card_entity : presentation_canvas.getQuestions()) {
                if (Integer.parseInt(view.getTag().toString()) == card_entity.getId()) {
                    questionCard = card_entity;
                }
            }
            view.setVisibility(View.INVISIBLE);


            evaluateAnswer(view, questionCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Entity card_entity : presentation_canvas.getQuestions()) {
            for (Integer key : card_entity.getOptions().keySet()) {
                AudioPlayerUtil audioPlayerUtil = playerUtilHashMap.get(card_entity.getOptions().get(key).getId());
                if (audioPlayerUtil.isChildPlaying()) {
                    audioPlayerUtil.setChildPlaying(false);
                    audioPlayerUtil.resumePlay();
                }
            }
        }

        if (presenetationAudioUtil != null && !presenetationAudioUtil.isChildPlaying() && !presenetationAudioUtil.getMediaPlayer().isPlaying()) {
            presenetationAudioUtil.resumePlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        for (Entity card_entity : presentation_canvas.getQuestions()) {
            for (Integer key : card_entity.getOptions().keySet()) {
                AudioPlayerUtil audioPlayerUtil = playerUtilHashMap.get(card_entity.getOptions().get(key).getId());
                if (audioPlayerUtil != null && audioPlayerUtil.getMediaPlayer() != null && audioPlayerUtil.getMediaPlayer().isPlaying()) {
                    audioPlayerUtil.setChildPlaying(true);
                    audioPlayerUtil.pausePlay();
                }
            }
        }


        if (presenetationAudioUtil != null && !presenetationAudioUtil.isChildPlaying())
            presenetationAudioUtil.pausePlay();
    }


}
