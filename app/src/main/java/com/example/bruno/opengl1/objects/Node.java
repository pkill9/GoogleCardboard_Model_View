package com.example.bruno.opengl1.objects;

import java.util.LinkedList;
import java.util.List;

public class Node {
    public float[] mMatrix = new float[16];
    public List<Node> children;
    public Mesh mesh;

    public Node(float[] mMat){
        mMatrix = mMat;
        children = new LinkedList<>();
    }

    public Node(float[] mMat, Mesh m){
        this.mesh = m;
        children = new LinkedList<>();
    }

    public void draw(float[] mMatrix){

    }

    public void draw(){
       for (Node n:children){
           n.draw(mMatrix);
       }
    }

    public void addChild(Node n){
        children.add(n);
    }

}
