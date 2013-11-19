package ocr.segmentation;

import common.Config;
import common.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Segmentation {
    
    public List<BufferedImage> segment(BufferedImage G) {
        List<BufferedImage> characters = new LinkedList<>();
        List<BufferedImage> lines = lineSegmentation(G);
        
        for(BufferedImage line : lines) {
            
        }
        
        return characters;
        //return Segmentation.segmentWords(G);
    }
    
    // Not robust
    public static List<BufferedImage> segmentWords(BufferedImage G) {
        
        List<BufferedImage> letters = new LinkedList<>();
        List<Integer> currentWord = null;
        
        for(int x = 0; x < G.getWidth(); ++x) {
            
            if(hasBlackPixel(G, x)) {
                if(currentWord == null) {
                    currentWord = new ArrayList<>();
                }
                currentWord.add(x);
            }
            else if(currentWord != null) {
                int width = currentWord.get(currentWord.size()-1) - currentWord.get(0);
                int height = G.getHeight();
                BufferedImage subimage = G.getSubimage(currentWord.get(0), 0, width, height);
                letters.add(subimage);
                ImageHelper.printImage(subimage, "segmented_character.png");
                currentWord = null;
            }            
        }
        
        // TODO: False, this needs fixed
        // There should not be any trailing whitespace
        int width = currentWord.get(currentWord.size()-1) - currentWord.get(0);
        int height = G.getHeight();
        BufferedImage subimage = G.getSubimage(currentWord.get(0), 0, width, height);
        letters.add(subimage);
        
        return letters;
    }
    
    private static List<BufferedImage> segmentCharacters(BufferedImage G) {
        List<BufferedImage> characters = new LinkedList<>();
        List<Integer> currentChar = null;
        int height = G.getHeight();
        
        return characters;
    }
    
    // Not robust
    private static List<BufferedImage> segmentLines(BufferedImage G) {        
        List<BufferedImage> lines = new LinkedList<>();
        List<Integer> currentLine = null;
        int width = G.getWidth();
        
        for(int y = 0; y < G.getHeight(); ++y) {
            
            if(rowHasBlackPixel(G, y)) {
                if(currentLine == null) { currentLine = new ArrayList<>(); }
                currentLine.add(y);
            }
            else if(currentLine != null) {
                int height = currentLine.get(currentLine.size()-1) - currentLine.get(0);
                BufferedImage subImage = G.getSubimage(0, currentLine.get(0), width, height);
                lines.add(subImage);
                ImageHelper.printImage(subImage, "segmented_line.png");
                currentLine = null;
            }
        }
        
        if(currentLine != null) {
            int height = currentLine.get(currentLine.size()-1) - currentLine.get(0);
            BufferedImage subImage = G.getSubimage(0, currentLine.get(0), width, height);
            lines.add(subImage);            
        }
        
        return lines;
    }
    
    private static BufferedImage trim(BufferedImage G) {
        BufferedImage trimmedImg = null;
        
        return trimmedImg;
    }
    
    
    // This is better
    private static List<BufferedImage> lineSegmentation(BufferedImage G) {        
        int width = G.getWidth();
        int y0 = -1;
        int y1 = -1;
        List<BufferedImage> lines = new LinkedList<>();
        
        for(int y = 0; y < G.getHeight(); ++y) {            
            if(rowHasBlackPixel(G, y)) {
                if(y0 == -1) { y0 = y; }
                y1 = y;
            } else if (y0 != -1) {
                BufferedImage subimage = G.getSubimage(0, y0, width, y1 - y0);
                lines.add(subimage);
                y0 = y1 = -1;
            }      
        }
        
        return lines;
    }
    
    private static List<BufferedImage> characterSegmentation(BufferedImage G) {
        int height = G.getHeight();
        int x0 = -1;
        int x1 = -1;
        List<BufferedImage> characters = new LinkedList<>();
        
        for(int x = 0; x < G.getWidth(); ++x) {
            if(colHasBlackPixel(G, x)) {
                if(x0 == -1) { x0 = x; }
                x1 = x;
            } else if (x0 != -1) {
                
            }
        }
        
        return characters;
    }
    
    private static boolean rowHasBlackPixel(BufferedImage G, int row) {        
        for(int x = 0; x < G.getWidth(); ++x) {
            int color = Math.abs(G.getRGB(x, row));
            if(color > Config.THRESHOLD) { return true; }
        }
        return false;
    }
    
    private static boolean colHasBlackPixel(BufferedImage G, int col) {
        for(int y = 0; y < G.getHeight(); ++y) {
            int color = Math.abs(G.getRGB(col, y));
            if(color > Config.THRESHOLD) { return true; }
        }
        return false;
    }
    
    private static boolean hasBlackPixel(BufferedImage G, int col) {
        
        for(int y = 0; y < G.getHeight(); ++y) {
            int color = Math.abs(G.getRGB(col, y));
            if(color > Config.THRESHOLD) { return true; }
        }
        return false;
    }
    
}
