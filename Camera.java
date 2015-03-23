package coeclient.render;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import coeclient.entity.player.EntityPlayer;

public class Camera
{
    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;
    final EntityPlayer player;

    public Camera(EntityPlayer player)
    {
        this.player = player;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getPitch()
    {
        return pitch;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getRoll()
    {
        return roll;
    }

    public void increasePosition(float dx, float dy, float dz)
    {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
        this.player.increasePosition(dx, dy, dz);;
    }

    public void increaseRotation(float dx, float dy, float dz)
    {
        this.pitch += dx;
        this.yaw += dy;
        this.roll += dz;
    }

    public void processMouse()
    {
        if(Mouse.isButtonDown(0))
        {
            this.yaw += Mouse.getDX() / 3f;
            this.pitch += Mouse.getDY() / 3f;
            
            if(this.yaw > 360)
                this.yaw = 0;
            if(this.yaw < -360)
                this.yaw = 0;
            
            if(this.pitch > 90)
                this.pitch = 90;
            if(this.pitch < -90)
                this.pitch = -90;
        }
    }
}
