package main.model;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ModelMain
{
    String name;
    Model model;

    int lineCount = 0;
    int displayList;

    public ModelMain(String name)
    {
        this.name = name;
        model = new Model();
    }

    public Model loadModel(String name) throws IOException
    {
        BufferedReader objectFile = new BufferedReader(new FileReader(name));
        Model m = new Model();

        String line;
        while((line = objectFile.readLine()) != null)
        {
            lineCount++;

            if(line.startsWith("v "))// vertices
            {
                m.vertices.add(new Vector3f(Float.parseFloat(line.split(" ")[1]), Float.parseFloat(line.split(" ")[2]), Float.parseFloat(line.split(" ")[3])));
            }
            else if(line.startsWith("vn "))// normals
            {
                m.normals.add(new Vector2f(Float.parseFloat(line.split(" ")[1]), Float.parseFloat(line.split(" ")[2])));
            }
            else if(line.startsWith("f "))// faces with simple "/" (vertex / texture coordinate)
            {
                m.faces.add(new Face(parseFacePos(line), parseFaceTexture(line)));
            }
            else if(line.startsWith("vt "))// texture coordinate
            {
                m.textureCoordinates.add(new Vector2f(Float.parseFloat(line.split(" ")[1]), Float.parseFloat(line.split(" ")[2])));
                System.out.println("text lenght: " + m.textureCoordinates.size());
            }
            else if(line.startsWith("#") || line.startsWith("g") || line.startsWith("s") || line.startsWith("usemtl") || line.startsWith("mtllib") || line.startsWith("o"))
            {
                System.out.println("ignored line: " + line + " at: " + lineCount);
                continue;
            }
            else
            {
                objectFile.close();
                throw new RuntimeException("OBJ file contain a bad line: " + line + " at: " + lineCount);
            }
        }
        objectFile.close();
        return m;
    }

    public int[] parseFacePos(String line)
    {
        String[] lineArray = line.split(" ");
        int[] vertexArray = {Integer.parseInt(lineArray[1].split("/")[0]), Integer.parseInt(lineArray[2].split("/")[0]), Integer.parseInt(lineArray[3].split("/")[0])};
        return vertexArray;
    }

    public int[] parseFaceTexture(String line)
    {
        String[] lineArray = line.split(" ");
        return new int[] {Integer.parseInt(lineArray[1].split("/")[1]), Integer.parseInt(lineArray[2].split("/")[1]), Integer.parseInt(lineArray[3].split("/")[1])};
    }

    public void setUpList()
    {
        displayList = glGenLists(1);

        glNewList(displayList, GL_COMPILE);
        {
            Model m = null;
            try {
                m = loadModel("res/test.obj");
            } catch (IOException e) {
                e.printStackTrace();
            }
            glBegin(GL_TRIANGLES);
            {
                for(Face face : m.getFaces())
                {
                    if(m.haveTextureCoor())
                    {
                        Vector2f t1 = m.getTextureCoordinates().get(face.getTextCoor()[0]);
                        glTexCoord2f(t1.x, t1.y);
                    }
                    Vector3f v1 = m.getVertices().get(face.getPos()[0]);
                    glVertex3f(v1.x, v1.y, v1.z);
                    
                    if(m.haveTextureCoor())
                    {
                        Vector2f t2 = m.getTextureCoordinates().get(face.getTextCoor()[1]);
                        glTexCoord2f(t2.x, t2.y);
                    }
                    Vector3f v2 = m.getVertices().get(face.getPos()[0]);
                    glVertex3f(v2.x, v2.y, v2.z);

                    if(m.haveTextureCoor())
                    {
                        Vector2f t3 = m.getTextureCoordinates().get(face.getTextCoor()[2]);
                        glTexCoord2f(t3.x, t3.y);
                    }
                    Vector3f v3 = m.getVertices().get(face.getPos()[0]);
                    glVertex3f(v3.x, v3.y, v3.z);
                }
            }
            glEnd();
        }
        glEndList();
    }
    
    public void render()
    {
        glColor4f(1, 1, 1, 1);
       glCallList(displayList);
    }

}
