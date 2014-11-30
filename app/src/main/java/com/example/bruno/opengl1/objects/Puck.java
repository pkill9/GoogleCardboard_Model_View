package com.example.bruno.opengl1.objects;

import com.example.bruno.opengl1.data.VertexArray;
import com.example.bruno.opengl1.objects.ObjectBuilder.DrawCommand;
import com.example.bruno.opengl1.objects.ObjectBuilder.GeneratedData;
import com.example.bruno.opengl1.programs.ColorShaderProgram;
import com.example.bruno.opengl1.util.Geometry;

import java.util.List;

/**
 * Created by Bruno on 11/29/2014.
 */
public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        GeneratedData generatedData = ObjectBuilder.createPuck(new Geometry.Cylinder(
                new Geometry.Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);
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
