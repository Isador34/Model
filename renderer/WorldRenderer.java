package coeclient.render.renderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import coeclient.util.Maths;
import coeclient.util.model.RawModel;
import coeclient.util.model.shaders.WorldShader;
import coeclient.util.model.textures.ModelTexture;
import coeclient.world.World;

public class WorldRenderer
{
    private WorldShader shader;

    public WorldRenderer(WorldShader shader, Matrix4f projectionMatrix)
    {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    
    public void render(List<World> worlds)
    {
        
        for(World world : worlds)
        {
            prepareWorld(world);
            loadModelMatrix(world);
            GL11.glDrawElements(GL11.GL_TRIANGLES, world.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }
    
    private void prepareWorld(World world)
    {
        RawModel rawmodel = world.getModel();
        GL30.glBindVertexArray(rawmodel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = world.getTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
    }

    private void unbindTexturedModel()
    {
        GL20.glDisableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(World world)
    {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(world.getX(), 0, world.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
    
}
