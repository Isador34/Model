package main.model;

public class Face
{
    public int[] pos = new int[3];
    public int[] textCoor = new int[3];

    public Face(int[] pos)
    {
        this.pos[0] = pos[0];
        this.pos[1] = pos[1];
        this.pos[2] = pos[2];
    }

    public Face(int[] pos, int[] textCoor)
    {
        this.pos[0] = pos[0];
        this.pos[1] = pos[1];
        this.pos[2] = pos[2];
        this.textCoor[0] = textCoor[0];
        this.textCoor[1] = textCoor[1];
        this.textCoor[2] = textCoor[2];
    }

    public int[] getPos()
    {
        return pos;
    }

    public int[] getTextCoor()
    {
        return textCoor;
    }
}
