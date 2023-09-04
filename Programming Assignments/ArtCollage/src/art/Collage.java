package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color
    // of the pixel on the screen.
    private int tileDimension;

    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension
     * x tileDimension*collageDimension,
     * where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling
     * filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage(String filename) {
        this.collageDimension = 4;
        this.tileDimension = 150;
        this.originalPicture = new Picture(filename);
        this.collagePicture = new Picture(this.tileDimension * this.collageDimension,
                this.tileDimension * this.collageDimension);
        scale(this.originalPicture, this.collagePicture);
        // WRITE YOUR CODE HERE
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension
     * x tileDimension*collageDimension,
     * where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling
     * filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage(String filename, int td, int cd) {
        this.collageDimension = cd;
        this.tileDimension = td;
        this.originalPicture = new Picture(filename);
        this.collagePicture = new Picture(this.tileDimension * this.collageDimension,
                this.tileDimension * this.collageDimension);
        scale(this.originalPicture, this.collagePicture);
        // WRITE YOUR CODE HERE
    }

    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * 
     * @target. Do not update @source.
     * 
     * @param source is the image to be scaled.
     * 
     * @param target is the
     */
    public static void scale(Picture source, Picture target) {
        int width = target.width();
        int height = target.height();
        for (int targetCol = 0; targetCol < width; targetCol++) {
            for (int targetRow = 0; targetRow < height; targetRow++) {
                int sourceCol = targetCol * source.width() / width;
                int sourceRow = targetRow * source.height() / height;
                Color color = source.get(sourceCol, sourceRow);
                target.set(targetCol, targetRow, color);
            }
        }

        // WRITE YOUR CODE HERE
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */

    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */

    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {
        collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles,
     * where each tile has tileDimension X tileDimension pixels.
     */
    public void makeCollage() {

        for (int i = 0; i < this.collageDimension; i++) {
            for (int j = 0; j < this.collageDimension; j++) {
                for (int targetCol = 0; targetCol < this.tileDimension; targetCol++) {
                    for (int targetRow = 0; targetRow < this.tileDimension; targetRow++) {
                        int sourceCol = targetCol * originalPicture.width() / this.tileDimension;
                        int sourceRow = targetRow * originalPicture.height() / this.tileDimension;
                        Color color = originalPicture.get(sourceCol, sourceRow);
                        collagePicture.set(i * tileDimension + targetCol,
                                j * tileDimension + targetRow, color);
                    }
                }

            }
        }
        // WRITE YOUR CODE HERE
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component
     * (see Week 9 slides, the code for color separation is at the
     * book's website)
     *
     * @param component is either red, blue or green
     * 
     * @param collageCol tile column
     * 
     * @param collageRow tile row
     */
    public void colorizeTile(String component, int collageCol, int collageRow) {

        for (int targetCol = collageCol*this.tileDimension; targetCol < (collageCol+1)*this.tileDimension; targetCol++) {
            for (int targetRow = collageRow*this.tileDimension; targetRow < (collageRow+1)*this.tileDimension; targetRow++) {
                Color color = collagePicture.get(targetCol, targetRow);
                Color newColor = new Color(0,0,0);
                if (component.equals("green")) {
                	int green = color.getGreen();
                    newColor = new Color(0,green,0); 	
                }
                else if (component.equals("blue")) {
                	int blue = color.getBlue();
                    newColor = new Color(0,0,blue); 
                }
                else if (component.equals("red")) {
                	int red = color.getRed();
                	newColor = new Color(red,0,0);
                }    
                collagePicture.set(targetCol, targetRow, newColor);
            }
        }
        // WRITE YOUR CODE HERE
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * 
     * @param collageCol tile column
     * 
     * @param collageRow tile row
     */
    public void replaceTile(String filename, int collageCol, int collageRow) {

    	Picture newerPicture = new Picture(filename); 
        Picture targetPicture = new Picture(this.tileDimension,this.tileDimension);
    	scale(newerPicture,targetPicture);    	
    	for (int i = collageCol*this.tileDimension; i < (collageCol+1)*this.tileDimension; i++) {
            for (int j = collageRow*this.tileDimension; j < (collageRow+1)*this.tileDimension; j++) {        	
            	Color color = targetPicture.get(i-(collageCol*this.tileDimension), j-(collageRow*this.tileDimension));
            	collagePicture.set(i, j, color);
            }
        }
        // WRITE YOUR CODE HERE
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * 
     * @param collageRow tile row
     */
    public void grayscaleTile(int collageCol, int collageRow) {
    	for (int i = collageCol*this.tileDimension; i < (collageCol+1)*this.tileDimension; i++) {
            for (int j = collageRow*this.tileDimension; j < (collageRow+1)*this.tileDimension; j++) {
                Color color = collagePicture.get(i, j);
                collagePicture.set(i, j, toGray(color));
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b)
            return r; // to avoid floating-point issues
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color))); // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow() {
        if (originalPicture != null) {
            originalPicture.closeWindow();
        }
        if (collagePicture != null) {
            collagePicture.closeWindow();
        }
    }
}
