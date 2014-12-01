package com.example.bruno.opengl1.objects;

import java.util.LinkedList;
import java.util.List;


public class Mesh {
    public float[] vertexPositions;
    public float[] vertexTexCoordinates;
    public int[] indicies;
    public Material m;

    public Mesh(float[] vP,float[] vT, int[] ind, Material mat){
        vertexPositions = vP;
        vertexTexCoordinates=vT;
        indicies = ind;
        m = mat;
    }


}
