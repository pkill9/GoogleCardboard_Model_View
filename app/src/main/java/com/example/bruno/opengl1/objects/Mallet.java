package com.example.bruno.opengl1.objects;

/**
 * Created by Bruno on 11/29/2014.
 */

import com.example.bruno.opengl1.data.VertexArray;
import com.example.bruno.opengl1.objects.ObjectBuilder.DrawCommand;
import com.example.bruno.opengl1.objects.ObjectBuilder.GeneratedData;
import com.example.bruno.opengl1.programs.ColorShaderProgram;
import com.example.bruno.opengl1.util.Geometry;

import java.util.List;


public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius;
    public final float height;

    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f,
                0f, 0f), radius, height, numPointsAroundMallet);

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }
    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
