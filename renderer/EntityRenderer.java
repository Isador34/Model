package coeclient.render.renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import coeclient.entity.Entity;
import coeclient.util.Maths;
import coeclient.util.model.RawModel;
import coeclient.util.model.TexturedModel;
import coeclient.util.model.shaders.StaticShader;

public class EntityRenderer
{

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix)
    {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities)
    {
            for(TexturedModel model : entities.keySet())
            {
                prepareTexturedModel(model);
                List<Entity> batch = entities.get(model);

                for(Entity entity : batch)
                {
                    prepareInstance(entity);
                    GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                }
                unbindTexturedModel();
            }
    }

    private void prepareTexturedModel(TexturedModel model)
    {
        RawModel rawmodel = model.getRawModel();
        GL30.glBindVertexArray(rawmodel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
    }

    private void unbindTexturedModel()
    {
        GL20.glDisableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity)
    {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
