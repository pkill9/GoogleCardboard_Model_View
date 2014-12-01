package com.example.bruno.opengl1.util;

import android.content.Context;

import com.example.bruno.opengl1.R;
import com.example.bruno.opengl1.objects.Material;
import com.example.bruno.opengl1.objects.Node;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.Matrix.setIdentityM;
import static com.example.bruno.opengl1.util.TextResourceReader.readTextFileFromResource;


public class ModelHelper {



    public Node CreateModel(Context context, int id){
        List<Material> mat = new LinkedList<>();
        float[] mMatrix = new float[16];
        setIdentityM(mMatrix, 0);
        Node head = new Node(mMatrix);

        String StringJSON = readTextFileFromResource(context, R.raw.model_json);

        try {
            JSONObject reader = new JSONObject(StringJSON);

            JSONArray materials = reader.getJSONArray("materials");
            JSONArray nodes = reader.getJSONArray("nodes");
            JSONArray meshes = reader.getJSONArray("meshes");

            for(int i = 0; i < materials.length(); i++){
                JSONObject o = materials.getJSONObject(i);
                mat.add(new Material(o.getJSONArray("diffuseTexture").getString(0),context));
            }

            for(int i = 0; i < nodes.length(); i++){
                JSONObject o = materials.getJSONObject(i);
                JSONArray a =o.getJSONArray("modelMatrix");
                JSONArray b =o.getJSONArray("meshIndices");

                for(int j =0;j<16;j++){
                    mMatrix[i]= (float)a.getDouble(i);
                }

                int meshI = b.getInt(0);
                JSONObject m = meshes.getJSONObject(meshI);
                JSONArray c =o.getJSONArray("vertexPositions");
                float [] vertexPos = new float[c.length()];
                for (int j = 0; j <c.length();j++){
                    vertexPos[i] = (float) c.getDouble(i);
                }

                JSONArray d =o.getJSONArray("vertexTexCoordinates");
                float [] vertexTex = new float[d.length()];
                for (int j = 0; j < d.length(); j++){
                    vertexPos[i] = (float) d.getDouble(i);
                }

                JSONArray e =o.getJSONArray("indices");
                int [] indices = new int[e.length()];
                for (int j = 0; j <e.length();j++){
                    vertexPos[i] = (float) e.getInt(i);
                }

                int matIndx = o.getInt("materialIndex");

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return head;
    }

}
