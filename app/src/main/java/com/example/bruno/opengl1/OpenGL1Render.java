package com.example.bruno.opengl1;

import android.content.Context;

import com.example.bruno.opengl1.objects.Node;
import com.example.bruno.opengl1.objects.Table;
import com.example.bruno.opengl1.programs.ColorShaderProgram;
import com.example.bruno.opengl1.programs.TextureShaderProgram;
import com.example.bruno.opengl1.util.ModelHelper;
import com.example.bruno.opengl1.util.TextureHelper;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.EyeTransform;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.perspectiveM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;

public class OpenGL1Render extends CardboardView implements CardboardView.StereoRenderer {
    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] eyeViewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] mHeadView = new float[16];
    private final float[] headViewMatrix = new float[16];


    private Node head;
    private Table table;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;


    public OpenGL1Render(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        //mallet = new Mallet(0.08f, 0.15f, 32);
        //puck = new Puck(0.06f, 0.02f, 32);
        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);
        head = ModelHelper.CreateModel(context);

        texture = TextureHelper.loadTexture(context, R.drawable.table);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);
        perspectiveM(projectionMatrix, 0, 45, (float) width / (float) height, .1f, 200f);
        setLookAtM(viewMatrix, 0, 0f, 0f, -15f, 0f, 0f, 0f, 0f, 1f, 0f);
    }
    @Override
    public void onNewFrame(HeadTransform headTransform) {

        // Multiply the view and projection matrices together.
        headTransform.getHeadView(mHeadView,0);
        multiplyMM(headViewMatrix, 0, mHeadView, 0, viewMatrix, 0);
        // Preposition table

        textureProgram.useProgram();


    }

    @Override
    public void onDrawEye(EyeTransform eyeTransform) {
        glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);

        positionTableInScene(eyeTransform.getEyeView());
//        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
//        table.bindData(textureProgram);
//        table.draw();

        head.drawC(eyeViewProjectionMatrix,textureProgram,texture);
    }


    private void positionTableInScene(float[] eyeT) {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, headViewMatrix, 0);
        multiplyMM(eyeViewProjectionMatrix, 0, eyeT, 0, viewProjectionMatrix, 0);

        multiplyMM(modelViewProjectionMatrix, 0, eyeViewProjectionMatrix, 0, modelMatrix, 0);


    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onRendererShutdown() {

    }
}
