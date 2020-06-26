package com.rightcodeit.parsernews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;


public class Controller {
    private Connection con;
    private int number = 0;
    private String heading;
    private String text;
    private String date;
    private String photoUrl;
    private String url;


    private CustomNews customNews;
    private  ArrayList<CustomNews> arrayList;



    public Controller()
    {
    }



    public void start() {
        customNews = new CustomNews();
        arrayList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc;
                try
                {
                    doc = Jsoup.connect("http://www.en-news.com.ua/").get();
                    Elements ipElements = doc.getElementsByAttributeValue("class", "_self");
                    for(Element ee: ipElements){
                        Element aElement = ee;
                        url = aElement.attr("href");
                        heading = aElement.text();


                        try {
                            Document docUrl = Jsoup.connect(url).get();

                            Elements urlImageElements = docUrl.getElementsByAttributeValue("class", "attachment-colormag-featured-image size-colormag-featured-image wp-post-image");

                            for(Element el: urlImageElements){
                                Element imElement = el;
                                photoUrl = imElement.attr("src");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Document docTime = Jsoup.connect(url).get();

                            Elements dateElements = docTime.getElementsByAttributeValue("class","entry-date published");

                            for(Element ed: dateElements){
                                Element dataElement = ed;
                                date = dataElement.text();
                            }

                            Document docText = Jsoup.connect(url).get();

                            Elements textElements = docText.getElementsByAttributeValue("class", "entry-content clearfix");

                            for(Element et: textElements){
                                Element textElement = et;
                                text = textElement.text();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        arrayList.add(new CustomNews(number, heading, text, date, photoUrl, url));


                    }
                    for(int i=0;i<arrayList.size();i++){
                        System.out.println(arrayList.get(i).toString());
                    }
                }
                catch (IOException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                /*ConnectDB conToDB = new ConnectDB();
                conToDB.AddNews(con, arrayList);
                for(int i=0;i<arrayList.size();i++){
                    System.out.println(arrayList.get(i).toString());
                }*/
            }
        }).start();




    }
}
