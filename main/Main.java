package main;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;
import main.model.ModelMain;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main
{
    static ModelMain model;

    public static void main(String[] args)
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(1250, 650));
            Display.setTitle("Crusade of Elements");
            Display.setResizable(false);
            Display.create();
            Display.sync(60);
        }
        catch(LWJGLException e1)
        {
            e1.printStackTrace();
        }

        initOpenGL();
        model = new ModelMain("test");
        model.setUpList();
        loop();
    }

    static void loop()
    {
        while(!Display.isCloseRequested())
        {
            model.render();
            Display.update();

        }
    }
    
    static void initOpenGL()
    {
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);        
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);                    
 
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        glClearDepth(1);                                       
 
        glViewport(0,0,Display.getWidth(),Display.getHeight());
        glMatrixMode(GL_MODELVIEW);
 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

}
