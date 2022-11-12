package com.example.car_rental_sys.ToolsLib;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ImageTools {
    public static Image getCircleImages(String fileUrl) {
        BufferedImage avatarImage;
        try {
            avatarImage = ImageIO.read(new URL(fileUrl));
            avatarImage = scaleByPercentage(avatarImage, avatarImage.getWidth(),  avatarImage.getWidth());
            if (avatarImage == null) {
                throw new RuntimeException("avatarImage is null");
            }
            int width = avatarImage.getWidth();
            BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int border = 1;
            Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width - border * 2);
            graphics.setClip(shape);
            graphics.drawImage(avatarImage, border, border, width - border * 2, width - border * 2, null);
            graphics.dispose();
            graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int border1 = 3;
            Stroke s = new BasicStroke(5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(s);
            graphics.setColor(Color.WHITE);
            graphics.drawOval(border1, border1, width - border1 * 2, width - border1 * 2);
            graphics.dispose();

            WritableImage wr;
            wr = new WritableImage(formatAvatarImage.getWidth(), formatAvatarImage.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < formatAvatarImage.getWidth(); x++) {
                for (int y = 0; y < formatAvatarImage.getHeight(); y++) {
                    pw.setArgb(x, y, formatAvatarImage.getRGB(x, y));
                }
            }
            return wr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight){
        try {
            int type = inputImage.getColorModel().getTransparency();
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            BufferedImage img = new BufferedImage(newWidth, newHeight, type);
            Graphics2D graphics2d = img.createGraphics();
            graphics2d.setRenderingHints(renderingHints);
            graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
            graphics2d.dispose();
            return img;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImageObjFromPath(String path) {
        File file = new File(path);
        try {
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image getUIImageObjFromName(String name) {
        String rootPath ="src/main/resources/com/example/car_rental_sys/image/UI/";
        return  getImageObjFromPath(rootPath + name);
    }

    public static Image getImageObjFromUserID(int userID) {
        String avatarRoot = "src/main/resources/com/example/car_rental_sys/image/avatar/" + userID + ".png";
        File file = new File(avatarRoot);
        String path;
        if (file.exists()) {
            path = "file:" + avatarRoot;
            return new Image(path);
        } else {
            if (Objects.equals(FXTools.getGenderFromUserID(userID), "female")) {
                path = avatarRoot + "avatar_female.png";
                return new Image(path);
            }
            path = avatarRoot + "avatar_male.png";
            return getCircleImages(path);
        }
    }

    public static void setImageShape(ImageView imageView, double radius) {
        double imageWidth =  imageView.getFitWidth();
        double imageHeight =  imageView.getFitHeight();

        javafx.scene.shape.Rectangle rectangle = new Rectangle(imageWidth,imageHeight);
        rectangle.setArcHeight(radius);
        rectangle.setArcWidth(radius);
        imageView.setClip(rectangle);
    }

    public static  void setImageShapeToCircle(ImageView imageView) {
        double imageWidth =  imageView.getFitWidth();
        double imageHeight =  imageView.getFitHeight();
        double circleRadius = imageHeight >= imageWidth ? imageWidth / 2 : imageHeight / 2;
        Circle circle = new Circle(circleRadius,imageWidth/2,imageHeight/2);
        imageView.setClip(circle);
    }
}
