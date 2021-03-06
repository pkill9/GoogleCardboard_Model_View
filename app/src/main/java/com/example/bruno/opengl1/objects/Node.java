package com.example.bruno.opengl1.objects;

import com.example.bruno.opengl1.data.IndexBuffer;
import com.example.bruno.opengl1.data.VertexArray;
import com.example.bruno.opengl1.programs.TextureShaderProgram;

import java.util.LinkedList;
import java.util.List;

import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.Matrix.multiplyMM;
import static com.example.bruno.opengl1.Constants.BYTES_PER_FLOAT;
import static com.example.bruno.opengl1.Constants.BYTES_PER_SHORT;

public class Node {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int INDICIES_COMPONENT_COUNT = 2;
    private static final int STRIDEP = (POSITION_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final int STRIDET = (TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final int STRIDEI = (INDICIES_COMPONENT_COUNT) * BYTES_PER_SHORT;

    public float[] mMatrix = new float[16];
    public List<Node> children;
    public Mesh mesh;

    private VertexArray PosBuffer;
    private VertexArray texCBuffer;
    private IndexBuffer indexBuffer;

    public Node(float[] mMat){
        mMatrix = mMat;
        children = new LinkedList<>();
    }

    public Node(float[] mMat, Mesh m){
        this.mesh = m;
        mMatrix = mMat;
        //Log.wtf("mesh.Pos", Arrays.toString(mesh.vertexPositions));
        PosBuffer = new VertexArray(mesh.vertexPositions);
        texCBuffer = new VertexArray(mesh.vertexTexCoordinates);
        indexBuffer = new IndexBuffer(mesh.indices);

    }

    public void draw(float[] modMatrix,float[] viewProjHead, TextureShaderProgram textureProgram, int texture){
        float [] temp = new float[16];
        float [] modelViewProjectionMatrix = new float[16];
        textureProgram.useProgram();

        //multiplyMM(temp, 0, modMatrix, 0, mMatrix, 0);

        multiplyMM(modelViewProjectionMatrix, 0, viewProjHead, 0, mMatrix, 0);
        textureProgram.setUniforms(modelViewProjectionMatrix, mesh.m.textId);

        this.bindData(textureProgram);
        //Log.e("ABOUT TO DRAW", Arrays.toString(modelViewProjectionMatrix));

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getBufferId());
        glDrawElements(GL_TRIANGLES, mesh.indices.length, GL_UNSIGNED_SHORT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void bindData(TextureShaderProgram textureProgram) {

        PosBuffer.setVertexAttribPointer(0, textureProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);
        texCBuffer.setVertexAttribPointer(0,textureProgram.getTextureCoordinatesAttributeLocation(),TEXTURE_COORDINATES_COMPONENT_COUNT,0);

    }

    public void drawC(float[] viewProjHead,TextureShaderProgram textureProgram,int texture){

       for (Node n:children){
           n.draw(mMatrix,viewProjHead,textureProgram,texture);
       }

    }

    public void addChild(Node n){
        children.add(n);
    }



}
