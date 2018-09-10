package com.example.hp.tpp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketPage extends AppCompatActivity {
     //二维码
    ImageView mImageView;
     Bitmap mBitmap;

     //电影名
    TextView movieTitle;

    //电影放映时间+属性 demo 9月18号 13:00~15:00  国语5d
    TextView date_shuxing;

    //影院名+放映地点  demo 重庆ume影城 5号厅
    TextView cinema_place;

    //座位
    TextView seat_location;

    //海报
    ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
       initView();
        /*

        这里添加读取服务器数据代码  部分控件内容需要多个数据拼接


         */
        //data为 电影名+场次+属性+位置
       mBitmap= CoreProducer.createQRCodeBitmap("data",800,800);
       mImageView.setImageBitmap(mBitmap);
    }
   public void initView(){
       mImageView=findViewById(R.id.page_core);
       movieTitle=findViewById(R.id.page_movie_title);
       cinema_place=findViewById(R.id.page_cinema_place);
       date_shuxing=findViewById(R.id.page_time_shuxing);
       seat_location=findViewById(R.id.page_seat_location);
       poster =findViewById(R.id.poster);
   }
}
