package com.example.bruno.opengl1.objects;

import android.content.Context;

import com.example.bruno.opengl1.util.TextureHelper;

public class Material {
    public String diffuseTexture;
    public int textId;

    public Material(String dT, Context context){
        diffuseTexture = dT;
        int resID = context.getResources().getIdentifier(dT, "drawable", "com.example.bruno.opengl1");
        textId = TextureHelper.loadTexture(context, resID);
    }

}
