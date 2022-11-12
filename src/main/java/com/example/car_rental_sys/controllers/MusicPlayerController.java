package com.example.car_rental_sys.controllers;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerController {
    @FXML
    ImageView backwardIcon, previousIcon, pauseIcon,nextIcon,forwardIcon;

    @FXML
    Pane musicCoverPane,processBarPane;

    @FXML
    Label songsterLabel, songNameLabel,playedTimeLabel, totalTimeLabel;

    //src/main/resources/com/example/car_rental_sys/fxml/OrderDetailsComponent.fxml
    Image playing = new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/playIcon_05.gif");

    Image paused = new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/pause.png");

    String MusicFilePath = "src\\main\\resources\\com\\example\\car_rental_sys\\music\\";

    private Media sound;
    private MediaPlayer player;
    private int currentSongID = 0;
    private int currentMusicDuration = 0;

    private ArrayList<String> musicList = new ArrayList<>();

    @FXML
    private void initialize(){
        setAllMusicAndCovers();
        loadMusic(currentSongID);
        initTimeLabel();
//        initPlayerEvent();
//        playMusic();
    }
//    private  void playMusic(){
//        sound = new Media(new File("src/main/resources/com/example/javamedia/music/Danza_Kuduro-Don_Omar.mp3").toURI().toString());
//        player = new MediaPlayer(sound);
//        player.play();
//    }

    public void setAllMusicAndCovers(){
        // set all music and covers
        ArrayList<String> files = getFiles(MusicFilePath);
        for (String file : files) {
            if(file.endsWith(".mp3")){
                // add to music list
                String musicName =  file.substring(0, file.length()-4);
                //System.out.println(musicName);
                musicList.add(musicName);
            }
        }
    }


    private void loadMusic(int musicID){
        if(player != null){
            player.stop();
        }
        String musicName = musicList.get(musicID);
        String musicPath = musicName+".mp3";
        String coverPath = musicName+".png";


        Image cover = new Image(new File(coverPath).toURI().toString());
        ImageView imageView = new ImageView(cover);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);

        Rectangle rectangle = new Rectangle(90,90);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);

        imageView.setClip(rectangle);

        String name = getNameFromPath(musicName);
        //System.out.println("name:" + name);
        String songName = name.split("-")[0].replace("_"," ");
        String songster = name.split("-")[1].replace("_"," ");
        songsterLabel.setText(songName);
        songNameLabel.setText(songster);


        musicCoverPane.getChildren().add(imageView);

        sound = new Media(new File(musicPath).toURI().toString());
        player = new MediaPlayer(sound);
        initPlayerEvent();

    }

    public  ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (File value : tempList) {
            if (value.isFile()) {
                //System.out.println("file:" + tempList[i]);
                files.add(value.toString());
            }
            if (value.isDirectory()) {
                //System.out.println("folder" + tempList[i]);
            }
        }
        return files;
    }


    private void initPlayerEvent(){
        player.setOnReady(() -> {
            //System.out.println(player.getMedia().getDuration());
            String totalTime = millisecondesToMinutsAndSeconds(player.getMedia().getDuration().toMillis());
            totalTimeLabel.setText(totalTime);
            addProcessBar();
        });
    }

    private String getNameFromPath(String path){
        String[] musicNameArag =  path.split("\\\\");
        return musicNameArag[musicNameArag.length-1];
    }

    @FXML
    private void pauseClickEvent(){
        // get imageView image path
        String path = pauseIcon.getImage().getUrl();
        if(path.endsWith("playIcon_05.gif")){
            // do pause
            pauseIcon.setImage(paused);
            player.pause();
            stopProcessBar();
            //System.out.println("pause");
        }else{
            // do play
            pauseIcon.setImage(playing);
            player.play();
            addProcessBar();
            //System.out.println("play");
        }
        //System.out.println(path);
    }

    @FXML
    private void nextClickEvent(){
        currentSongID ++;
        if(currentSongID >= musicList.size()){
            currentSongID = 0;
        }
        loadMusic(currentSongID);
        pauseIcon.setImage(playing);
        player.play();
        processBarPane.setPrefWidth(0);
        resetProcessBar();
    }

    @FXML
    private void previousClickEvent(){
        currentSongID --;
        if(currentSongID < 0){
            currentSongID = musicList.size()-1;
        }
        loadMusic(currentSongID);
        pauseIcon.setImage(playing);
        player.play();
        processBarPane.setPrefWidth(0);
        resetProcessBar();
    }

    // add 30 seconds
    @FXML
    private void add10(){
        player.seek(player.getCurrentTime().add(player.getMedia().getDuration().multiply(0.1)));
        addOrminus10ProcessBar(1);
    }

    // minus 30 seconds
    @FXML
    private void minus10(){
        player.seek(player.getCurrentTime().subtract(player.getMedia().getDuration().multiply(0.1)));
        addOrminus10ProcessBar(-1);
    }

    private String millisecondesToMinutsAndSeconds(double milliseconds){

        long minutes = (long)(milliseconds / 1000) / 60;
        long seconds = (long)(milliseconds / 1000) % 60;

        // long to int
        currentMusicDuration = (int) (milliseconds / 1000);


        // us padStart to add 0 before the number
        String minutesString = String.valueOf(minutes).length() == 1 ? "0"+minutes : String.valueOf(minutes);
        String secondsString = String.valueOf(seconds).length() == 1 ? "0"+seconds : String.valueOf(seconds);

        return minutesString+":"+secondsString;
    }

    private void initTimeLabel(){
        // init time label
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            String currentTime = millisecondesToMinutsAndSeconds(player.getCurrentTime().toMillis());
            playedTimeLabel.setText(currentTime);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    Timer timer = null;
    private void addProcessBar(){
        if(currentMusicDuration == 0){
            return;
        }
        double item = 392 / currentMusicDuration;
        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                processBarPane.setPrefWidth(processBarPane.getWidth()+item);
            }
        },0,1000);
    }

    private void addOrminus10ProcessBar(int type){
        if(currentMusicDuration == 0){
            return;
        }
        double item = 392 / currentMusicDuration;
        if(type == 1){
            processBarPane.setPrefWidth(processBarPane.getWidth()+item*10);
        }else if(-1 == type){
            processBarPane.setPrefWidth(processBarPane.getWidth()-item*10);
        }
    }
    private void resetProcessBar(){
        processBarPane.setPrefWidth(0);
    }

    private void stopProcessBar(){
        if(timer != null){
            timer.cancel();
        }
    }

    private void setRadiusForImage(){
        // set radius for image
        Image image = new Image("file:src/main/resources/images/cover.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        imageView.setClip(new Circle(45,45,45));
        musicCoverPane.getChildren().add(imageView);
    }
}