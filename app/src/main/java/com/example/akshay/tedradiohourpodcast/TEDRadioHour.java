package com.example.akshay.tedradiohourpodcast;

/**
 * Created by akshay on 3/4/2017.
 */

public class TEDRadioHour {
   String Title;
    String Description;
    String  Publicationdate;
    String ImageURL;
    String Duration;
    String MP3URL;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public String toString() {
        return "Cnn{" +
                "title='" + Title + '\'' +
                ", link='" + ImageURL + '\'' +
                ", duration='" + Duration + '\'' +
                ", desc='" + Description + '\'' +
                ", pubdate='" + Publicationdate + '\'' +
                ", mp3link='" + MP3URL + '\'' +
                '}';
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPublicationdate() {
        return Publicationdate;
    }

    public void setPublicationdate(String publicationdate) {
        Publicationdate = publicationdate;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getMP3URL() {
        return MP3URL;
    }

    public void setMP3URL(String MP3URL) {
        this.MP3URL = MP3URL;
    }
}
