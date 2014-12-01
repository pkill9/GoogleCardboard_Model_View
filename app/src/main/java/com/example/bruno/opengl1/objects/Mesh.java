package com.example.bruno.opengl1.objects;

import android.util.Log;

import com.example.bruno.opengl1.Constants;

import java.util.Arrays;

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

        Log.i(Constants.TAG, Arrays.toString(vertexPositions));
        Log.i(Constants.TAG, Arrays.toString(vertexTexCoordinates));
        Log.i(Constants.TAG, Arrays.toString(indices));

    }


}
