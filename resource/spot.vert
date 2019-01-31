//#version 120
//
// simple.vert
//
//invariant gl_Position; // invariant out gl_Position; //for #version 130
attribute vec3 inposition;//in vec3 position;          //for #version 130
varying vec3 pos;        
uniform mat4 mat[4];
varying vec3 color;
varying vec3 vPos;
void main(void)
{
  gl_Position = mat[0]*mat[1]*vec4(inposition*1.333, 1.0);
  color = (inposition + 1.0)/2.0 * 0.5 + 0.5;
  vec4 vPosAffine = mat[1]*vec4(inposition * 0.8, 1.0);
  vPos = vPosAffine.xyz / vPosAffine.w;
//  gl_Position = vec4(inposition, 1.0);
  pos = gl_Position.xyz; 
}

