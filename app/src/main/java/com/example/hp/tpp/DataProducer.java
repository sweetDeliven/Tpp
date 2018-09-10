package com.example.hp.tpp;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.Response;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class DataProducer {
    public void initRecycleView() {

    }

    public static void putDataInbackground(String username, String cinemaTitle, String movieTitle, int j, String poster, MyCallBack<Integer> callBack) {
        for (int i = 0; i < 21; i += 2) {
            String cost = String.valueOf(25 + new Random().nextInt(20));
            Match match = new Match(i + ":30", (i + 2) + ":30", "国语3d", "3号厅", cost, new boolean[10][15], 0, getOldDate(j));
            AVObject avObject = new AVObject("Match");
            avObject.put("username", username);
            avObject.put("movieTitle", movieTitle);
            avObject.put("cinemaTitle", cinemaTitle);
            avObject.put("start_time", match.getStartTime());
            avObject.put("mSoldAndCheck", new Gson().toJson(new SoldAndCheck()));
            avObject.put("end_time", match.getEndTime());
            avObject.put("shuxing", match.getShuxing());
            avObject.put("date", match.getDate());
            avObject.put("place", match.getPlace());
            avObject.put("price", match.getPrice());
            avObject.put("poster", poster);
            final int finalJ = j;
            final int finalI = i;
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        callBack.onSuccess(j);
                    }
                }
            });
        }
    }


    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    public static void downLoadData(String username, String movieTitle, String cinemaTitle, int i, final MyCallBack<List<AVObject>> callBack) {

        AVQuery<AVObject> query = new AVQuery<>("Match");
        query.whereEqualTo("date", DataProducer.getOldDate(i));
        query.whereEqualTo("movieTitle", movieTitle);
        query.whereEqualTo("cinemaTitle", cinemaTitle);
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                }
            }
        });

    }

    public static void post(String movie_title, String cinema_title, String date_time, String shuxing, String seat_location, String place, String imagURL, String username, String ticket_number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                DataOutputStream out = null;
                try {
                    String data = "movie_title=" + URLEncoder.encode(movie_title,"utf-8")+ "&cinema_title=" + URLEncoder.encode(cinema_title,"utf-8") + "&date_time=" + URLEncoder.encode(date_time,"utf-8") + "&shuxing=" +URLEncoder.encode(shuxing,"utf-8")+ "&place=" + URLEncoder.encode(place,"utf-8") + "&seat_location=" + URLEncoder.encode(seat_location,"utf-8") + "&imageUrl=" +URLEncoder.encode(imagURL,"utf-8") + "&username=" +URLEncoder.encode(username,"utf-8")+ "&num=" + ticket_number;
                    URL url = new URL("http://47.106.95.140:8080/tpp/addticket");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);  //防止传入缓存
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(4000);
                    connection.setReadTimeout(4000);
                    connection.connect();
                  out=new DataOutputStream(connection.getOutputStream());
                  out.writeBytes(data);
                    Log.d("post_hehehe", data);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.d("hehutet", getInputStreamData(connection.getInputStream()));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        Log.d("post_hehehe", "setd");
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        connection.disconnect();
                    }
                }
            }

        }).start();
    }

    public static String getInputStreamData(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String ling;
        while ((ling = reader.readLine()) != null) {
            stringBuilder.append(ling);
        }
        return stringBuilder.toString();
    }
}

