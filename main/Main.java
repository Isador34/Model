package main;

import models.OBJLoader;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;

public class Main
{

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

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel textureModel = new TexturedModel(model, texture);

        Entity entity = new Entity(textureModel, new Vector3f(0, -03, -20), 0, 0, 0, 1);

        Camera camera = new Camera();

        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(0, -0.007f, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            Display.update();
        }
        loader.cleanUp();
    }
}
