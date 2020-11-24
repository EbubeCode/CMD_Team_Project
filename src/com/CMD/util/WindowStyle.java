package com.CMD.util;


import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author:   BENJAH
 */

// Class from stack Overflow to enable dragging of window and it's styling.

public class WindowStyle {
    private static final Rectangle2D SCREEN_BOUNDS= Screen.getPrimary()
            .getVisualBounds();
    private static double[] pref_WH, offset_XY;
    private static String styleSheet;

    private WindowStyle(String css) {
        styleSheet= getClass().getResource(css).toString();
    }

    // Method to enable dragging of window
    public static void allowDrag(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent p) -> offset_XY= new double[]{p.getSceneX(), p.getSceneY()});

        root.setOnMouseDragged((MouseEvent d) -> {
            //Ensures the stage is not dragged past the taskbar
            if (d.getScreenY()<(SCREEN_BOUNDS.getMaxY()-20))
                stage.setY(d.getScreenY() - offset_XY[1]);
            stage.setX(d.getScreenX() - offset_XY[0]);
        });

        root.setOnMouseReleased((MouseEvent r)-> {
            //Ensures the stage is not dragged past top of screen
            if (stage.getY()<0.0) stage.setY(0.0);
        });
    }

    //Sets the default stage prefered width and height.
    protected static void stageDimension(Double width, Double height) {
        pref_WH= new double[]{width, height};
    }

    protected static void fullScreen(Stage stage) {
        stage.setX(SCREEN_BOUNDS.getMinX());
        stage.setY(SCREEN_BOUNDS.getMinY());
        stage.setWidth(SCREEN_BOUNDS.getWidth());
        stage.setHeight(SCREEN_BOUNDS.getHeight());
    }

    protected static void restoreScreen(Stage stage) {
        stage.setX((SCREEN_BOUNDS.getMaxX() - pref_WH[0])/2);
        stage.setY((SCREEN_BOUNDS.getMaxY() - pref_WH[1])/2);
        stage.setWidth(pref_WH[0]);
        stage.setHeight(pref_WH[1]);
    }

    protected static String addStyleSheet(String css) {
        new WindowStyle(css);
        return styleSheet;
    }
}