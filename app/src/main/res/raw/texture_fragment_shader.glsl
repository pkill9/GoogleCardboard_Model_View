precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;

void main()
{
    vec2 v_TextureCoordinates_flipped = vec2(v_TextureCoordinates.x, 1.0 - v_TextureCoordinates.y);
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates_flipped);
}
