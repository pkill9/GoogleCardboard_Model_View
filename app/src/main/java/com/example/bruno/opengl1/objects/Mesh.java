package com.example.bruno.opengl1.objects;

public class Mesh {
    public float[] vertexPositions;
    public float[] vertexTexCoordinates;
    public short[] indices;
    public Material m;

    public Mesh(float[] vP,float[] vT, short[] ind, Material mat){
        vertexPositions = vP;
        vertexTexCoordinates=vT;
        indices = ind;
        m = mat;

//        Log.e(Constants.TAG, Arrays.toString(vertexPositions));
//        Log.e(Constants.TAG, Arrays.toString(vertexTexCoordinates));
//        Log.e(Constants.TAG, Arrays.toString(indices));

    }


}
