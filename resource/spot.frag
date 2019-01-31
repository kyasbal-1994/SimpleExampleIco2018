//#version 120
//
// simple.frag
//
//uniform mat4 mat;
uniform sampler2D texture0;
uniform vec3 lightpos;
varying vec3 pos; 
varying vec3 color;
varying vec3 vPos;
void main (void){
//  gl_FragColor = max(texture2D(texture0, texcoord),color.xyzw);
//  gl_FragColor = texture2D(texture0, texcoord);
//  gl_FragColor = vec4(0.3, 0.8, 0.2, 1.0);
//  vec2 dfdx = abs(dFdx(texcoord));
//  vec2 dfdy = abs(dFdy(texcoord));
//  float d = max(max(dfdx.x,dfdx.y),max(dfdy.x,dfdy.y));
//  gl_FragColor = vec4(d*20,0,0,1);
    vec3 matC = color;
    vec3 lightDir = normalize(lightpos - vPos);
    vec3 ambient = vec3(1,1,1) * 0.15;
    vec3 normal = normalize(pos - vec3(0,0,-2));
    vec3 halfVector = normalize(lightDir + vec3(0,0,1));
    float specular = pow(dot(halfVector,normal),100.0);
    vec3 lightingResult = vec3(max(0.,dot(normal,lightDir) + specular)) + ambient;
    gl_FragColor = vec4(matC*lightingResult,1);
//  gl_FragColor = vec4(1.0,0.8,0.3,1.0);
}
