package com.example.pannam.flickrbrowser;

import java.io.Serializable;

/**
 * Created by pannam on 2/1/2016.
 */

//This object/class is created to store json data
    //making it searializable to pass from one activity to another
public class Photo implements Serializable{
    //WHEN USING serializable use serialversion . its a standard

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID= 1L;
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImages;

    //Constructor

    public Photo(String mTitle, String mAuthor, String mAuthorId, String mLink, String mTags, String mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImages = mImage;
    }
    //Getter


    public String getAuthor() {
        return mAuthor;
    }

    public String getAuthorId() {
        return mAuthorId;
    }

    public String getImages() {
        return mImages;
    }

    public String getLink() {
        return mLink;
    }

    public String getTags() {
        return mTags;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mAuthor='" + mAuthor + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImages='" + mImages + '\'' +
                '}';
    }
}
