package com.example.xh.retrofit;

import java.util.List;

/**
 * Created by xiehui on 2016/11/3.
 */
public class Subject {
    @Override
    public String toString() {
        return "Subject{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", subjects=" + subjects.toString() +
                ", title='" + title + '\'' +
                '}';
    }

    private int count;

    private int start;

    private int total;

    private List<Subjects> subjects ;

    private String title;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setSubjects(List<Subjects> subjects){
        this.subjects = subjects;
    }
    public List<Subjects> getSubjects(){
        return this.subjects;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public class Rating {
        private int max;

        private double average;

        private String stars;

        private int min;

        public void setMax(int max){
            this.max = max;
        }
        public int getMax(){
            return this.max;
        }
        public void setAverage(double average){
            this.average = average;
        }
        public double getAverage(){
            return this.average;
        }
        public void setStars(String stars){
            this.stars = stars;
        }
        public String getStars(){
            return this.stars;
        }
        public void setMin(int min){
            this.min = min;
        }
        public int getMin(){
            return this.min;
        }

    }
    public class Subjects {
        @Override
        public String toString() {
            return "Subjects{" +
                    "rating=" + rating +
                    ", genres=" + genres +
                    ", title='" + title + '\'' +
                    ", casts=" + casts.toString() +
                    ", collect_count=" + collect_count +
                    ", original_title='" + original_title + '\'' +
                    ", subtype='" + subtype + '\'' +
                    ", directors=" + directors.toString() +
                    ", year='" + year + '\'' +
                    ", images=" + images +
                    ", alt='" + alt + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        private Rating rating;

        private List<String> genres ;

        private String title;

        private List<Casts> casts ;

        private int collect_count;

        private String original_title;

        private String subtype;

        private List<Directors> directors ;

        private String year;

        private Images images;

        private String alt;

        private String id;

        public void setRating(Rating rating){
            this.rating = rating;
        }
        public Rating getRating(){
            return this.rating;
        }
        public void setString(List<String> genres){
            this.genres = genres;
        }
        public List<String> getString(){
            return this.genres;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setCasts(List<Casts> casts){
            this.casts = casts;
        }
        public List<Casts> getCasts(){
            return this.casts;
        }
        public void setCollect_count(int collect_count){
            this.collect_count = collect_count;
        }
        public int getCollect_count(){
            return this.collect_count;
        }
        public void setOriginal_title(String original_title){
            this.original_title = original_title;
        }
        public String getOriginal_title(){
            return this.original_title;
        }
        public void setSubtype(String subtype){
            this.subtype = subtype;
        }
        public String getSubtype(){
            return this.subtype;
        }
        public void setDirectors(List<Directors> directors){
            this.directors = directors;
        }
        public List<Directors> getDirectors(){
            return this.directors;
        }
        public void setYear(String year){
            this.year = year;
        }
        public String getYear(){
            return this.year;
        }
        public void setImages(Images images){
            this.images = images;
        }
        public Images getImages(){
            return this.images;
        }
        public void setAlt(String alt){
            this.alt = alt;
        }
        public String getAlt(){
            return this.alt;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }

    }

    public class Casts {
        @Override
        public String toString() {
            return "Casts{" +
                    "alt='" + alt + '\'' +
                    ", avatars=" + avatars +
                    ", name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        private String alt;

        private Avatars avatars;

        private String name;

        private String id;

        public void setAlt(String alt){
            this.alt = alt;
        }
        public String getAlt(){
            return this.alt;
        }
        public void setAvatars(Avatars avatars){
            this.avatars = avatars;
        }
        public Avatars getAvatars(){
            return this.avatars;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }

    }
    public class Directors {
        @Override
        public String toString() {
            return "Directors{" +
                    "alt='" + alt + '\'' +
                    ", avatars=" + avatars +
                    ", name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        private String alt;

        private Avatars avatars;

        private String name;

        private String id;

        public void setAlt(String alt){
            this.alt = alt;
        }
        public String getAlt(){
            return this.alt;
        }
        public void setAvatars(Avatars avatars){
            this.avatars = avatars;
        }
        public Avatars getAvatars(){
            return this.avatars;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }

    }
    public class Images {
        private String small;

        private String large;

        private String medium;

        public void setSmall(String small){
            this.small = small;
        }
        public String getSmall(){
            return this.small;
        }
        public void setLarge(String large){
            this.large = large;
        }
        public String getLarge(){
            return this.large;
        }
        public void setMedium(String medium){
            this.medium = medium;
        }
        public String getMedium(){
            return this.medium;
        }

    }
    public class Avatars {
        private String small;

        private String large;

        private String medium;

        public void setSmall(String small){
            this.small = small;
        }
        public String getSmall(){
            return this.small;
        }
        public void setLarge(String large){
            this.large = large;
        }
        public String getLarge(){
            return this.large;
        }
        public void setMedium(String medium){
            this.medium = medium;
        }
        public String getMedium(){
            return this.medium;
        }

    }

}
