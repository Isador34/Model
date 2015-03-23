package coeclient.render.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import coeclient.entity.Entity;
import coeclient.render.Camera;
import coeclient.util.model.TexturedModel;
import coeclient.util.model.shaders.StaticShader;
import coeclient.util.model.shaders.WorldShader;
import coeclient.world.World;

public class MasterRenderer
{
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    public static boolean allowRender = true;

    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;

    private WorldRenderer worldRenderer;
    private WorldShader worldShader = new WorldShader();

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<World> worlds = new ArrayList<World>();

    public MasterRenderer()
    {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        worldRenderer = new WorldRenderer(worldShader, projectionMatrix);
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void render(Camera camera)
    {
        if(allowRender)
        {
            shader.start();
            shader.loadViewMatrix(camera);
            entityRenderer.render(entities);
            shader.stop();
            
            worldShader.start();
            worldShader.loadViewMatrix(camera);
            worldRenderer.render(worlds);
            worldShader.stop();

            worlds.clear();
            entities.clear();
        }
    }

    public void processWorld(World world)
    {
        worlds.add(world);
    }

    public void processEntity(Entity entity)
    {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);

        if(batch != null)
        {
            batch.add(entity);
        }
        else
        {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    private void createProjectionMatrix()
    {
        float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight();
        float y_scale = (float)((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE - NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanUp()
    {
        shader.cleanUp();
        worldShader.cleanUp();
    }
}
