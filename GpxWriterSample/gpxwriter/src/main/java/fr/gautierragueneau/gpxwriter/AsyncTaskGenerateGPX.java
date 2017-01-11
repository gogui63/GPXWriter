package fr.gautierragueneau.gpxwriter;

import android.location.Location;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gautier on 11/01/2017.
 */

public class AsyncTaskGenerateGPX extends AsyncTask<Void, Void, String> {

    private final GPX mGpx;
    private final IGPX mListner;

    public AsyncTaskGenerateGPX(GPX mGpx, IGPX mListner) {
        this.mGpx = mGpx;
        this.mListner = mListner;
    }

    @Override
    protected String doInBackground(Void... params) {
        return getHeader() + getMetaDatas() + getPoints() + getFooter();
    }


    @Override
    protected void onPostExecute(String s) {
        mListner.generateListner(s);
        super.onPostExecute(s);
    }

    private String getHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                "<gpx version=\"1.1\"\n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "    xmlns=\"http://www.topografix.com/GPX/1/1\"\n" +
                "    xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">";
    }

    private String getMetaDatas() {
        if (mGpx != null &&
                (TextUtils.isValidText(mGpx.getAuthor()) ||
                        TextUtils.isValidText(mGpx.getDescription()) ||
                        TextUtils.isValidText(mGpx.getName()))) {
            StringBuilder stringBuilder = new StringBuilder("<metadata>\n");
            if (TextUtils.isValidText(mGpx.getAuthor())) {
                stringBuilder.append("<author>\n")
                        .append("<name>").append(mGpx.getAuthor()).append("</name>")
                        .append("\n</author>");
            }

            if (TextUtils.isValidText(mGpx.getDescription())) {
                stringBuilder.append("<desc>\n")
                        .append(mGpx.getDescription())
                        .append("\n</desc>");
            }

            if (TextUtils.isValidText(mGpx.getName())) {
                stringBuilder.append("<name>\n")
                        .append(mGpx.getName())
                        .append("\n</name>");
            }

            stringBuilder.append("</metadata>");
            return stringBuilder.toString();
        }
        return "";
    }

    private String getPoints() {
        if (mGpx.getPoints() != null && !mGpx.getPoints().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder("<trk>\n<trkseg>");
            for (Location location : mGpx.getPoints()) {
                stringBuilder.append("<trkpt lat=\"").append(location.getLatitude())
                        .append("\" lon=\"").append(location.getLongitude()).append("\">")
                        .append("\n<ele>").append(location.getAltitude()).append("</ele>")
                        .append("\n<time>").append(getFormatedTime(location.getTime())).append("</time>")
                        .append("</trkpt>\n");
            }
            stringBuilder.append("</trkseg>\n</trk>");
            return stringBuilder.toString();
        }
        return "";
    }

    private String getFormatedTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date(time));
    }


    private String getFooter() {
        return "</gpx>";
    }
}
