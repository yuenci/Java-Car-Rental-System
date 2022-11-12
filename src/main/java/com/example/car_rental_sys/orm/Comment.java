package com.example.car_rental_sys.orm;

public class Comment {
    private int commentID;
    private String type;
    private String payload;
    private int posterID;
    private String comment;
    private int relevantCommentID;
    private String commentDateTime;
    private int like;
    private int star;


    public Comment(int commentID, String type, String payload, int posterID, String comment, int relevantCommentID, String commentDateTime, int like, int star) {
        this.commentID = commentID;
        this.type = type;
        this.payload = payload;
        this.posterID = posterID;
        this.comment = comment;
        this.relevantCommentID = relevantCommentID;
        this.commentDateTime = commentDateTime;
        this.like = like;
        this.star = star;
    }
}
