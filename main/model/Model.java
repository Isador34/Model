package main.model;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Model
{
    public List<Vector2f> normals = new ArrayList<Vector2f>();
    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector2f> textureCoordinates = new ArrayList<Vector2f>();
    public List<Face> faces = new ArrayList<Face>();
    
    public List<Vector2f> getNormals()
    {
        return normals;
    }

    public List<Vector3f> getVertices()
    {
        return vertices;
    }

    public List<Vector2f> getTextureCoordinates()
    {
        return textureCoordinates;
    }
    
    public boolean haveTextureCoor()
    {
        return textureCoordinates.size()>0 ? true : false;
    }
    
    public List<Face> getFaces()
    {
        return faces;
    }
}
