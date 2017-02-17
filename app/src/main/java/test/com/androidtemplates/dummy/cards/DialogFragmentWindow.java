package test.com.androidtemplates.dummy.cards;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import link.fls.swipestack.SwipeStack;
import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.pojo.CardContent;
import test.com.androidtemplates.dummy.pojo.EntityOption;
import test.com.androidtemplates.dummy.pojo.InfoCard;
import test.com.androidtemplates.dummy.util.AssementUtil;
import test.com.androidtemplates.util.AudioVideoSaver;
import test.com.androidtemplates.util.CustomLayout;
import test.com.androidtemplates.util.SaveAudioVideoAsync;


/**
 * Created by ajith on 09-02-2017.
 */

public class DialogFragmentWindow extends DialogFragment {
    private SwipeStack mSwipeStack;
    private SwipeStackAdapter mAdapter;
    private ArrayList<InfoCard> mData;
    private LayoutInflater inflater1;
    private AssementUtil assementUtil;
    private EntityOption entityOption;
    private MediaPlayer mediaPlayer;

    private ImageView button_close;
    private int count = 0;


    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;
        try {
            if (getArguments() != null) {
                Bundle bundle = getArguments();
                getDialog().getWindow().setLayout(bundle.getInt("displayWidth"), bundle.getInt("displayHeight"));
                getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflater1 = inflater;
        View view = inflater.inflate(R.layout.popup, container);


        mSwipeStack = (SwipeStack) view.findViewById(R.id.swipeStack);
        assementUtil = new AssementUtil(getActivity(), null);

        mData = new ArrayList<>();
        try {
            Bundle bundle = getArguments();
            entityOption = (EntityOption) bundle.getSerializable("entity_options");
            for (Integer key : entityOption.getCards().keySet()) {
                mData.add(entityOption.getCards().get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new SwipeStackAdapter(mData);
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {

            }

            @Override
            public void onViewSwipedToRight(int position) {

            }

            @Override
            public void onStackEmpty() {
                dismiss();
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    public class SwipeStackAdapter extends BaseAdapter implements View.OnClickListener {

        private ArrayList<InfoCard> mData;
        private int i = 0;
        private HashMap<View, String> hashMapOfViews = new HashMap<>();

        public SwipeStackAdapter(ArrayList<InfoCard> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public InfoCard getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final InfoCard infoCard = mData.get(position);
            CardContent cardContent = infoCard.getContent();

            if (convertView == null) {

                if (infoCard.getTemplate() != null && infoCard.getTemplate().equalsIgnoreCase("CARDA")) {
                    convertView = inflater1.inflate(R.layout.assessment_dilog_card1, parent, false);
                    CustomLayout background = (CustomLayout) convertView.findViewById(R.id.background);
                    ImageView iv_photo = (ImageView) convertView.findViewById(R.id.image);
                    TextView title = (TextView) convertView.findViewById(R.id.title);
                    WebView paragraph = (WebView) convertView.findViewById(R.id.paragraph);
                    paragraph.bringToFront();

                    CustomLayout main_layout = (CustomLayout) convertView.findViewById(R.id.main_layout);
                    main_layout.setBackgroundResource(R.drawable.tags_rounded_corners);

                    assementUtil.setupInfoCardBG(main_layout, background, cardContent);
                    assementUtil.setUpInfoCardImageAndContent(iv_photo, title, paragraph, cardContent);

                } else if (infoCard.getTemplate() != null && infoCard.getTemplate().equalsIgnoreCase("CARDB")) {
                    convertView = inflater1.inflate(R.layout.assessment_dilog_card2, parent, false);
                    CustomLayout main_layout = (CustomLayout) convertView.findViewById(R.id.main_layout);
                    main_layout.setBackgroundResource(R.drawable.tags_rounded_corners);
                    CustomLayout background = (CustomLayout) convertView.findViewById(R.id.background);


                    assementUtil.setupInfoCard2BG(main_layout, background, cardContent);
                    assementUtil.setupInfoCard2TopLeft(convertView, cardContent);
                    assementUtil.setupInfoCard2TopRight(convertView, cardContent);
                    assementUtil.setupInfoCard2BottomLeft(convertView, cardContent);
                    assementUtil.setupInfoCard2BottomRight(convertView, cardContent);
                    assementUtil.setupInfoCard2CenterTop(convertView, cardContent);
                    assementUtil.setupInfoCard2CenterBottom(convertView, cardContent);

                    hashMapOfViews = assementUtil.getHashMapOfViews();

                    if (hashMapOfViews.size() > 0) {
                        for (View view : hashMapOfViews.keySet()) {
                            if (hashMapOfViews.get(view).equalsIgnoreCase("AUDIO")) {
                                view.setOnClickListener(this);
                            }
                        }
                    }
                }

                button_close = (ImageView) convertView.findViewById(R.id.button_close);
                button_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            if ((count++) % 2 == 0) {
                                mSwipeStack.swipeTopViewToRight();
                            } else {
                                mSwipeStack.swipeTopViewToLeft();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
            }


            return convertView;
        }

        @Override
        public void onClick(View view) {
            try {
                System.out.println(hashMapOfViews.get(view) + view.getTag());
                InfoCard infoCard = mData.get(mSwipeStack.getCurrentPosition());

                switch (view.getTag().toString()) {
                    case "TL":
                        startAudio(getActivity(), infoCard.getContent().getbTopLeftMediaUrl());
                        break;
                    case "TR":
                        startAudio(getActivity(), infoCard.getContent().getbTopRightMediaUrl());
                        break;
                    case "BL":
                        startAudio(getActivity(), infoCard.getContent().getbBottomLeftMediaUrl());
                        break;
                    case "BR":
                        startAudio(getActivity(), infoCard.getContent().getbBottomRightMediaUrl());
                        break;
                    case "CTB":
                        startAudio(getActivity(), infoCard.getContent().getbCenterTopMediaUrl());
                        break;
                    case "CBB":
                        startAudio(getActivity(), infoCard.getContent().getbCenterBottomMediaUrl());
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        private void startAudio(Context context, String url) {
            try {

                if (!url.contains("http")) {
                    url = context.getResources().getString(R.string.resources_fetch_ip) + "/" + url;
                }
                int index = url.lastIndexOf("/");
                String audio_name = url.substring(index, url.length()).replace("/", "");
                //url = url.replace("", "/content/compress?src=orgadmin&file=/video/");
                AudioVideoSaver audioVideoSaver = new AudioVideoSaver(context).
                        setParentDirectoryName("" + 1000).
                        setFileName(audio_name.replace(".wav", ".mp3")).
                        setExternal(AudioVideoSaver.isExternalStorageReadable());
                Boolean file_exist = audioVideoSaver.checkFile();

                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                    mediaPlayer = null;
                    //mediaPlayer.release();
                }
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //mediaPlayer.reset();
                    }
                });
                Uri videouri = null;

                if (file_exist) {
                    try {
                        videouri = Uri.fromFile(audioVideoSaver.load());
                        mediaPlayer.setDataSource(context, videouri);
                        mediaPlayer.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        videouri = Uri.parse(url);
                        mediaPlayer.setDataSource(context, videouri);
                        mediaPlayer.prepare();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new SaveAudioVideoAsync(audioVideoSaver).execute(url);
                }

                mediaPlayer.start();
            } catch (Exception e) {

            }
        }


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // now cast activity to your activity class and get a reference
        // to the listener
    }


    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

}