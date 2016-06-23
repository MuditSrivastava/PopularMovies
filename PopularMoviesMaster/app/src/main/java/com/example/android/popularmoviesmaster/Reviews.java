package com.example.android.popularmoviesmaster;

import java.util.List;


public class Reviews {
    public List<SingleReview> results;
    public List<SingleReview> getReviews(){
        return results;
    }
    public void setReviews(List<SingleReview> reviews) {
        this.results = reviews;
    }
    public static class SingleReview{
        String content;
        String url;
        public String getContent(){
            return content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setContent(String content){
            this.content = content;
        }
    }
}
