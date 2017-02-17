package test.com.androidtemplates.dummy.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.pojo.CardContent;
import test.com.androidtemplates.dummy.pojo.Entity;
import test.com.androidtemplates.dummy.pojo.EntityOption;
import test.com.androidtemplates.dummy.pojo.PPT;
import test.com.androidtemplates.util.CustomLayout;
import test.com.androidtemplates.util.ImageSaver;
import test.com.androidtemplates.util.SaveImageAsync;


/**
 * Created by ajith on 08-02-2017.
 */

public class AssementUtil {
    Context context;
    PPT presentation_canvas;
    Picasso picasso;
    HashMap<View, String> hashMapOfViews = new HashMap<>();
    private int ppt_id = 1000;

    public AssementUtil(Context context, PPT presentation_canvas) {
        this.presentation_canvas = presentation_canvas;
        this.context = context;
        picasso = Picasso.with(context);
    }


    public void setupBackground(CustomLayout customLayout, int width, int height) {
        int index = presentation_canvas.getBgImage().lastIndexOf("/");
        String bg_image_name = presentation_canvas.getBgImage().substring(index, presentation_canvas.getBgImage().length()).replace("/", "");
        String url = presentation_canvas.getBgImage().replaceAll("&", "%26");
        if (!url.contains("http")) {
            url = context.getResources().getString(R.string.resources_fetch_ip) + url;
        }

        ImageSaver imageSaver = new ImageSaver(context).
                setParentDirectoryName("" + ppt_id).
                setFileName(bg_image_name).
                setExternal(ImageSaver.isExternalStorageReadable());

        Boolean file_exist = imageSaver.checkFile();
        if (file_exist) {
            try {
                Uri uri = Uri.fromFile(imageSaver.pathFile());
                picasso.load(uri).resize(width, height).into(customLayout);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            picasso.load(url).resize(width, height).into(customLayout);
            new SaveImageAsync(imageSaver).execute(url);
        }

    }


    public void setupCardBackground(CustomLayout customLayout, Entity card_entity) {
        if (card_entity.getBgColor() != null && !card_entity.getBgColor().equalsIgnoreCase("") && !card_entity.getBgColor().equalsIgnoreCase("none")) {
            customLayout.setBackgroundColor(Color.parseColor(card_entity.getBgColor()));
        }
        if (card_entity.getBackgroundImage() != null && !card_entity.getBackgroundImage().equalsIgnoreCase("") && !card_entity.getBackgroundImage().equalsIgnoreCase("none")) {
            String url = card_entity.getBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(customLayout, url);
        }
    }

    public void setQuestionCardText(CustomLayout customLayout, Entity card_entity) {
        if (card_entity.getText() != null && !card_entity.getText().equalsIgnoreCase("")) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setText(card_entity.getText());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL | RelativeLayout.CENTER_VERTICAL);
            textView.setLayoutParams(layoutParams);
            customLayout.addView(textView);
        }
    }


    public void setOptionCardText(CustomLayout customLayout, EntityOption option_entity) {
        if (option_entity.getOptionText() != null && !option_entity.getOptionText().equalsIgnoreCase("")) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setText(option_entity.getOptionText());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL | RelativeLayout.CENTER_VERTICAL);
            textView.setLayoutParams(layoutParams);
            customLayout.addView(textView);
        }
    }


    public void setupOptionCardBackground(CustomLayout customLayout, EntityOption option_entity) {
        if (option_entity.getBackgroundColor() != null && !option_entity.getBackgroundColor().equalsIgnoreCase("") && !option_entity.getBackgroundColor().equalsIgnoreCase("none")) {
            customLayout.setBackgroundColor(Color.parseColor(option_entity.getBackgroundColor()));
        }
        if (option_entity.getBackgroundImage() != null && !option_entity.getBackgroundImage().equalsIgnoreCase("") && !option_entity.getBackgroundImage().equalsIgnoreCase("none")) {
            String url = option_entity.getBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(customLayout, url);
        }
    }

    public void setupInfoCardBG(CustomLayout main_layout, CustomLayout customLayout, CardContent cardContent) {

        if (cardContent.getaBackgroundColor() != null && !cardContent.getaBackgroundColor().equalsIgnoreCase("")) {
            GradientDrawable drawable = (GradientDrawable) main_layout.getBackground();
            drawable.setColor(Color.parseColor(cardContent.getaBackgroundColor()));
        }

        if (cardContent.getaBackgroundImage() != null && !cardContent.getaBackgroundImage().equalsIgnoreCase("") && !cardContent.getaBackgroundImage().equalsIgnoreCase("none")) {
            String url = cardContent.getaBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(customLayout, url);
        }
    }


    public void setUpInfoCardImageAndContent(ImageView iv_photo, TextView title, final WebView paragraph, final CardContent cardContent) {
        if (cardContent.getaForegroundImage() != null && !cardContent.getaForegroundImage().equalsIgnoreCase("") && !cardContent.getaForegroundImage().equalsIgnoreCase("none")) {
            String url = cardContent.getaForegroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadImage(iv_photo, url);
        }

        if (title != null && cardContent.getaTitle() != null) {
            title.setText(cardContent.getaTitle());
        }


        paragraph.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        paragraph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        paragraph.setBackgroundColor(Color.parseColor("#00000000"));

        if (cardContent.getaDescription() != null) {
            paragraph.setVisibility(View.VISIBLE);
            paragraph.getSettings().setJavaScriptEnabled(true);
            paragraph.getSettings().setDomStorageEnabled(true);
            paragraph.setWebViewClient(new WebViewClient());
            paragraph.loadData("<p style='color:#000000'>" + cardContent.getaDescription() + "</p>", "text/html; charset=utf-8", "UTF-8");

        } else {
            paragraph.setVisibility(View.GONE);
        }
    }


    public void setupInfoCard2BG(CustomLayout main_layout, CustomLayout customLayout, CardContent cardContent) {

        if (cardContent.getbBackgroundColor() != null && !cardContent.getbBackgroundColor().equalsIgnoreCase("")) {
            GradientDrawable drawable = (GradientDrawable) main_layout.getBackground();
            drawable.setColor(Color.parseColor(cardContent.getbBackgroundColor()));
        }

        if (cardContent.getbBackgroundImage() != null && !cardContent.getbBackgroundImage().equalsIgnoreCase("") && !cardContent.getbBackgroundImage().equalsIgnoreCase("none")) {
            String url = cardContent.getbBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(customLayout, url);
        }
    }

    public void setupInfoCard2TopLeft(View convertView, CardContent cardContent) {


        ImageView top_left = (ImageView) convertView.findViewById(R.id.top_left);

        if (cardContent.getbTopleftBGImage() != null && !cardContent.getbTopleftBGImage().equalsIgnoreCase("") && !cardContent.getbTopleftBGImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbTopleftBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadImage(top_left, url);
            top_left.setTag("TL");
        }
        hashMapOfViews.put(top_left, cardContent.getbTopLeftMediaType());

    }

    public void setupInfoCard2TopRight(View convertView, CardContent cardContent) {

        ImageView top_right = (ImageView) convertView.findViewById(R.id.top_right);

        if (cardContent.getbTopRightBGImage() != null && !cardContent.getbTopRightBGImage().equalsIgnoreCase("") && !cardContent.getbTopRightBGImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbTopRightBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadImage(top_right, url);
            top_right.setTag("TR");
        }
        hashMapOfViews.put(top_right, cardContent.getbTopRightMediaType());
    }

    public void setupInfoCard2BottomLeft(View convertView, CardContent cardContent) {
        ImageView bottom_left = (ImageView) convertView.findViewById(R.id.bottom_left);

        if (cardContent.getbBottomLeftBGImage() != null && !cardContent.getbBottomLeftBGImage().equalsIgnoreCase("") && !cardContent.getbBottomLeftBGImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbBottomLeftBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadImage(bottom_left, url);
            bottom_left.setTag("BL");
        }

        hashMapOfViews.put(bottom_left, cardContent.getbBottomLeftMediaType());
    }

    public void setupInfoCard2BottomRight(View convertView, CardContent cardContent) {
        ImageView bottom_right = (ImageView) convertView.findViewById(R.id.bottom_right);

        if (cardContent.getbBottomRightBGImage() != null && !cardContent.getbBottomRightBGImage().equalsIgnoreCase("") && !cardContent.getbBottomRightBGImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbBottomRightBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }

            loadImage(bottom_right, url);
            bottom_right.setTag("BR");
        }
        hashMapOfViews.put(bottom_right, cardContent.getbBottomRightMediaType());
    }

    public void setupInfoCard2CenterTop(View convertView, CardContent cardContent) {
        CustomLayout ceneter_top_background = (CustomLayout) convertView.findViewById(R.id.ceneter_top_background);
        ImageView ceneter_top = (ImageView) convertView.findViewById(R.id.ceneter_top);


        if (cardContent.getbCenterTopBackgroundImage() != null && !cardContent.getbCenterTopBackgroundImage().equalsIgnoreCase("") && !cardContent.getbCenterTopBackgroundImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbCenterTopBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(ceneter_top_background, url);
        }


        if (cardContent.getbCenterTopForegrounImage() != null && !cardContent.getbCenterTopForegrounImage().equalsIgnoreCase("") && !cardContent.getbCenterTopForegrounImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbCenterTopForegrounImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }

            loadImage(ceneter_top, url);
        }
        ceneter_top_background.setTag("CTB");
        hashMapOfViews.put(ceneter_top_background, cardContent.getbCenterTopMediaType());
    }

    public void setupInfoCard2CenterBottom(View convertView, CardContent cardContent) {
        CustomLayout ceneter_bottom_background = (CustomLayout) convertView.findViewById(R.id.ceneter_bottom_background);
        ImageView ceneter_bottom = (ImageView) convertView.findViewById(R.id.ceneter_bottom);


        if (cardContent.getbCenterBottomBackgroundImage() != null && !cardContent.getbCenterBottomBackgroundImage().equalsIgnoreCase("") && !cardContent.getbCenterBottomBackgroundImage().equalsIgnoreCase("none")) {


            String url = cardContent.getbCenterBottomBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(ceneter_bottom_background, url);
        }


        if (cardContent.getbCenterBottomForegroundImage() != null && !cardContent.getbCenterBottomForegroundImage().equalsIgnoreCase("") && !cardContent.getbCenterBottomForegroundImage().equalsIgnoreCase("none")) {

            String url = cardContent.getbCenterBottomForegroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }

            loadImage(ceneter_bottom, url);
        }
        ceneter_bottom_background.setTag("CBB");
        hashMapOfViews.put(ceneter_bottom_background, cardContent.getbCenterBottomMediaType());

    }


    public void setUpCardBG(CustomLayout customLayout, Entity entity) {

        if (entity.getTransitionImage() != null && !entity.getTransitionImage().equalsIgnoreCase("") && !entity.getTransitionImage().equalsIgnoreCase("none")) {
            String url = entity.getTransitionImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            loadBG(customLayout, url);
        }
    }


    public void loadImage(final ImageView image, final String url) {

        int index = url.lastIndexOf("/");
        String bg_image_name = url.substring(index, url.length()).replace("/", "");

        ImageSaver imageSaver = new ImageSaver(context).
                setParentDirectoryName("" + ppt_id).
                setFileName(bg_image_name).
                setExternal(ImageSaver.isExternalStorageReadable());

        Boolean file_exist = imageSaver.checkFile();
        if (file_exist) {
            try {
                Uri uri = Uri.fromFile(imageSaver.pathFile());
                picasso.load(uri).into(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            picasso.load(url).into(image);
            new SaveImageAsync(imageSaver).execute(url);
        }

    }


    public void loadBG(final CustomLayout image, final String url) {
        int index = url.lastIndexOf("/");
        String bg_image_name = url.substring(index, url.length()).replace("/", "");


        ImageSaver imageSaver = new ImageSaver(context).
                setParentDirectoryName("" + ppt_id).
                setFileName(bg_image_name).
                setExternal(ImageSaver.isExternalStorageReadable());

        Boolean file_exist = imageSaver.checkFile();
        if (file_exist) {
            try {
                Uri uri = Uri.fromFile(imageSaver.pathFile());
                picasso.load(uri).into(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            picasso.load(url).into(image);
            new SaveImageAsync(imageSaver).execute(url);
        }

    }

    public HashMap<View, String> getHashMapOfViews() {
        return hashMapOfViews;
    }

    public void setHashMapOfViews(HashMap<View, String> hashMapOfViews) {
        this.hashMapOfViews = hashMapOfViews;
    }

}
