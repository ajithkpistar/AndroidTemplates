package test.com.androidtemplates.dummy;

import android.content.Context;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.pojo.CardContent;
import test.com.androidtemplates.dummy.pojo.Entity;
import test.com.androidtemplates.dummy.pojo.EntityOption;
import test.com.androidtemplates.dummy.pojo.PPT;
import test.com.androidtemplates.util.DownloaderThread;
import test.com.androidtemplates.util.ImageSaver;


/**
 * Created by Ajith on 12-1-2017.
 */
public class AssessmentResourceUtility {
    private ExecutorService executor = Executors.newFixedThreadPool(20);
    private Context context;
    private String xml;
    private int ppt_id;
    private String downloadingpath = "";
    private Boolean pres_audio_check = false;

    PPT presentation_canvas;

    public AssessmentResourceUtility(Context context, String xml, int ppt_id) {
        this.context = context;
        this.xml = xml;
        this.ppt_id = ppt_id;
        this.pres_audio_check = false;
    }

    public void fetchResource() {
        long start_time = System.currentTimeMillis();
        xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
        try {
            StringReader reader = new StringReader(xml);
            Serializer serializer = new Persister();
            presentation_canvas = serializer.read(PPT.class, reader);
            ImageSaver imageSaver = new ImageSaver(context).
                    setParentDirectoryName("" + ppt_id).
                    setFileName("").
                    setExternal(ImageSaver.isExternalStorageReadable());

            File dummyFile = imageSaver.pathFile();
            downloadingpath = dummyFile.getAbsolutePath();
            Log.v("Talentify", "downloadingpath------>" + downloadingpath);
            getpresentationImage(presentation_canvas);
            for (Entity entity : presentation_canvas.getQuestions()) {
                getEntityImages(entity);

                for (Integer key : entity.getOptions().keySet()) {
                    EntityOption entityOption = entity.getOptions().get(key);
                    getEntityOptionImages(entityOption);
                    for (Integer integer : entityOption.getCards().keySet()) {
                        getEntityCardImages(entityOption.getCards().get(integer).getContent());
                    }
                }
            }

            executor.shutdown();
            while (!executor.isTerminated()) {

            }
            long end_time = System.currentTimeMillis();
            System.out.println("Toatal Time-->" + (end_time - start_time) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getpresentationImage(PPT presentation_canvas) {

        if (presentation_canvas != null && presentation_canvas.getAudioUrl() != null && !presentation_canvas.getAudioUrl().equalsIgnoreCase("")) {
            String url = presentation_canvas.getAudioUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }

            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }


        if (presentation_canvas != null && presentation_canvas.getBgImage() != null && !presentation_canvas.getBgImage().equalsIgnoreCase("")) {
            String url = presentation_canvas.getBgImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }

            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }

    }

    private void getEntityImages(Entity entity) {
        if (entity != null && entity.getTransitionImage() != null && !entity.getTransitionImage().equalsIgnoreCase("")) {
            String url = entity.getTransitionImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
    }

    private void getEntityOptionImages(EntityOption entityOption) {
        if (entityOption != null && entityOption.getBackgroundImage() != null && !entityOption.getBackgroundImage().equalsIgnoreCase("")) {
            String url = entityOption.getBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
    }


    private void getEntityCardImages(CardContent cardContent) {
        //Card A
        //backbground
        if (cardContent != null && cardContent.getaBackgroundImage() != null && !cardContent.getaBackgroundImage().equalsIgnoreCase("")) {
            String url = cardContent.getaBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
        //foreground
        if (cardContent != null && cardContent.getaForegroundImage() != null && !cardContent.getaForegroundImage().equalsIgnoreCase("")) {
            String url = cardContent.getaForegroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }


        //Card B
        //top left
        if (cardContent != null && cardContent.getbTopleftBGImage() != null && !cardContent.getbTopleftBGImage().equalsIgnoreCase("")) {
            String url = cardContent.getbTopleftBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
        if (cardContent != null && cardContent.getbTopLeftMediaUrl() != null && !cardContent.getbTopLeftMediaUrl().equalsIgnoreCase("")) {
            String url = cardContent.getbTopLeftMediaUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }


        //top right
        if (cardContent != null && cardContent.getbTopRightBGImage() != null && !cardContent.getbTopRightBGImage().equalsIgnoreCase("")) {
            String url = cardContent.getbTopRightBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
        if (cardContent != null && cardContent.getbTopRightMediaUrl() != null && !cardContent.getbTopRightMediaUrl().equalsIgnoreCase("")) {
            String url = cardContent.getbTopRightMediaUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }


        //bottom left
        if (cardContent != null && cardContent.getbBottomLeftBGImage() != null && !cardContent.getbBottomLeftBGImage().equalsIgnoreCase("")) {
            String url = cardContent.getbBottomLeftBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
        if (cardContent != null && cardContent.getbBottomLeftMediaUrl() != null && !cardContent.getbBottomLeftMediaUrl().equalsIgnoreCase("")) {
            String url = cardContent.getbBottomLeftMediaUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }

        //bottom Right
        if (cardContent != null && cardContent.getbBottomRightBGImage() != null && !cardContent.getbBottomRightBGImage().equalsIgnoreCase("")) {
            String url = cardContent.getbBottomRightBGImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
        if (cardContent != null && cardContent.getbBottomRightMediaUrl() != null && !cardContent.getbBottomRightMediaUrl().equalsIgnoreCase("")) {
            String url = cardContent.getbBottomRightMediaUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }

        //Center Top BG
        if (cardContent != null && cardContent.getbCenterTopBackgroundImage() != null && !cardContent.getbCenterTopBackgroundImage().equalsIgnoreCase("")) {
            String url = cardContent.getbCenterTopBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }

        //Center Top FG
        if (cardContent != null && cardContent.getbCenterTopForegrounImage() != null && !cardContent.getbCenterTopForegrounImage().equalsIgnoreCase("")) {
            String url = cardContent.getbCenterTopForegrounImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }

        if (cardContent != null && cardContent.getbCenterTopMediaUrl() != null && !cardContent.getbCenterTopMediaUrl().equalsIgnoreCase("")) {
            String url = cardContent.getbCenterTopMediaUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }


        //Center Bottom BG
        if (cardContent != null && cardContent.getbCenterBottomBackgroundImage() != null && !cardContent.getbCenterBottomBackgroundImage().equalsIgnoreCase("")) {
            String url = cardContent.getbCenterBottomBackgroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }

        //Center Bottom FG
        if (cardContent != null && cardContent.getbCenterBottomForegroundImage() != null && !cardContent.getbCenterBottomForegroundImage().equalsIgnoreCase("")) {
            String url = cardContent.getbCenterBottomForegroundImage().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
        //
        if (cardContent != null && cardContent.getbCenterBottomMediaUrl() != null && !cardContent.getbCenterBottomMediaUrl().equalsIgnoreCase("")) {
            String url = cardContent.getbCenterBottomMediaUrl().replaceAll("&", "%26");
            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + url;
            }
            url = url.trim().replaceAll("\\s+", "%20");
            Runnable worker = new DownloaderThread(url, downloadingpath);
            executor.execute(worker);
        }
    }
}
