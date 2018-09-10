package com.example.hp.tpp;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class TicketInformation {
    private String movie_title;    //电影名字
    private String cinema_title;   // 影院名字
    private String date_time;      //日期
    private String shuxing;        //国语3d
    private String place;          //几号厅
    private String seat_location;  //几排几座
    private String imageUrl;       //电影海报
    private String username;       // 用户账号
    private String ticket_number;  //电影票张数

    public String getTicket_number() {
        return ticket_number;
    }

    public String getUsername() {
        return username;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public String getCinema_title() {
        return cinema_title;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getShuxing() {
        return shuxing;
    }

    public String getPlace() {
        return place;
    }

    public String getSeat_location() {
        return seat_location;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
