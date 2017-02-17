package test.com.androidtemplates.dummy.cards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import pl.droidsonroids.gif.GifImageView;
import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.pojo.Variable;
import test.com.androidtemplates.util.CustomLayout;
import test.com.androidtemplates.util.ImageSaver;
import test.com.androidtemplates.util.SaveImageAsync;


/**
 * Created by ajith on 09-02-2017.
 */

public class DialogScoreWindow extends DialogFragment {
    private LayoutInflater inflater1;
    private HashMap<String, Variable> scoreValues = new HashMap<>();
    private ArrayList<Variable> strings = new ArrayList<>();


    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;
        try {
            if (getArguments() != null) {
                Bundle bundle = getArguments();
                getDialog().getWindow().setLayout(bundle.getInt("displayWidth") - 50, bundle.getInt("displayHeight") - 150);
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
    public void onPause() {
        super.onPause();
        dismiss();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<TextView> textViews = new ArrayList<>();
        inflater1 = inflater;
        View view = inflater.inflate(R.layout.dilog_score_window, container);
        CustomLayout main_layout = (CustomLayout) view.findViewById(R.id.main_layout);
        GifImageView gif_image = (GifImageView) view.findViewById(R.id.gif_image);
        GifImageView button_close = (GifImageView) view.findViewById(R.id.button_close);
        /*int alpha = (int)(1.0 * 255.0f);

        main_layout.setBackgroundColor(Color.argb(alpha,0, 150, 136));*/


        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ListView list_view = (ListView) view.findViewById(R.id.list_view);
        try {
            Bundle bundle = getArguments();
            scoreValues = (HashMap<String, Variable>) bundle.getSerializable("variables");


            if (scoreValues != null && scoreValues.size() > 0) {
                for (String key : scoreValues.keySet()) {
                    strings.add(scoreValues.get(key));
                }
                list_view.setAdapter(new CustomAdapter(getActivity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    public class CustomAdapter extends BaseAdapter {
        Context context;

        public CustomAdapter(Context context) {
            this.context = context;
            inflater1 = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder {
            TextView tv1, tv2;
            ImageView img;
            RelativeLayout score_val_bg;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            View rowView;
            rowView = inflater1.inflate(R.layout.score_list_item, null);
            holder.tv1 = (TextView) rowView.findViewById(R.id.textView1);
            holder.tv2 = (TextView) rowView.findViewById(R.id.textView2);

            holder.score_val_bg = (RelativeLayout) rowView.findViewById(R.id.score_val_bg);

            GradientDrawable drawable = (GradientDrawable) holder.score_val_bg.getBackground();
            drawable.setColor(Color.parseColor("#00C851"));

            holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
            holder.tv1.setText(strings.get(position).getName().replaceAll("_"," ")+"");
            holder.tv2.setText(strings.get(position).getValue()+"");

            if (strings.get(position).getIcon() != null && !strings.get(position).getIcon().equalsIgnoreCase("")) {

                if (strings.get(position).getIcon() != null && !strings.get(position).getIcon().equalsIgnoreCase("") && !strings.get(position).getIcon().equalsIgnoreCase("none")) {
                    String url = strings.get(position).getIcon().replaceAll("&", "%26");
                    if (!url.contains("http")) {
                        url = context.getResources().getString(R.string.resources_fetch_ip) + url;
                    }
                    loadImage(holder.img, url);
                }
            }
            return rowView;
        }

        public void loadImage(final ImageView image, final String url) {

            int index = url.lastIndexOf("/");
            String bg_image_name = url.substring(index, url.length()).replace("/", "");

            ImageSaver imageSaver = new ImageSaver(context).
                    setParentDirectoryName("" + 1000).
                    setFileName(bg_image_name).
                    setExternal(ImageSaver.isExternalStorageReadable());

            Boolean file_exist = imageSaver.checkFile();
            if (file_exist) {
                try {
                    Uri uri = Uri.fromFile(imageSaver.pathFile());
                    Picasso.with(context).load(uri).into(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Picasso.with(context).load(url).into(image);
                new SaveImageAsync(imageSaver).execute(url);
            }

        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // now cast activity to your activity class and get a reference
        // to the listener
    }

}