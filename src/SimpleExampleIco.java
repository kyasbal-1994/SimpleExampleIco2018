import com.jogamp.opengl.*;
import com.jogamp.opengl.util.PMVMatrix;

import java.awt.event.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class SimpleExampleIco extends SimpleExampleBase {
    //Object3D obj;
    PMVMatrix mats;
    Shader shader;
    int uniformMat;
    int uniformLight;
    float t = 0;

    public SimpleExampleIco() {
       // obj = new Plane();
        //obj = new Cylinder(16,.7f,.5f,true);
        //obj = new GridPlane(12,5,8f,3f);
        //obj = new BezierPatch();
        mats = new PMVMatrix();
        shader = new Shader("resource/spot.vert", "resource/spot.frag");
        addKeyListener(new simpleExampleKeyListener());
        addMouseMotionListener(new simpleExampleMouseMotionListener());
        addMouseListener(new simpleExampleMouseListener());
    }

    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));
        final GL2 gl = drawable.getGL().getGL2();
        //drawable.getGL().getGL2();
        gl.glViewport(0, 0, SCREENW, SCREENH);

        // Clear color buffer with black
        gl.glClearColor(1.0f, 1.0f, 0.5f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
        gl.glFrontFace(GL.GL_CCW);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
        float X = .525731112119133606f;
        float Z = .850650808352039932f;
        float N = 0f;
        float[] v = new float[]{-X, N, Z, X, N, Z, -X, N, -Z, X, N, -Z, N, Z, X, N, Z, -X, N, -Z, X, N, -Z, -X, Z, X, N, -Z, X, N, Z, -X, N, -Z, -X, N};
        int[] i = new int[]{0,4,1,0,9,4,9,5,4,4,5,8,4,8,1, 8,10,1,8,3,10,5,3,8,5,2,3,2,7,3,7,10,3,7,6,10,7,11,6,11,0,6,0,1,6, 6,1,10,9,0,11,9,11,2,9,2,5,7,2,11};
        int[] glbfs = new int[]{0,0};
        gl.glGenBuffers(2,glbfs,0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER,glbfs[0]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER,v.length * 4, FloatBuffer.wrap(v),GL.GL_STATIC_DRAW);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER,glbfs[1]);
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,i.length * 4, IntBuffer.wrap(i),GL.GL_STATIC_DRAW);
        shader.init(gl);
        int programName = shader.getID();
        gl.glBindAttribLocation(programName, Object3D.VERTEXPOSITION, "inposition");
        gl.glEnableVertexAttribArray(Object3D.VERTEXPOSITION);
        shader.link(gl);
        uniformMat = gl.glGetUniformLocation(programName, "mat");
        uniformLight = gl.glGetUniformLocation(programName, "lightpos");
        gl.glUseProgram(programName);
        gl.glUniform3f(uniformLight, 10f, 10f, 10.0f);
        //obj.init(gl, mats, programName);
        gl.glUseProgram(0);
    }

    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        mats.glMatrixMode(GL2.GL_MODELVIEW);
        mats.glLoadIdentity();
        mats.glTranslatef(0f, 0f, -3.0f);
        if (t < 360) {
            t = t + 0.1f;
        } else {
            t = 0f;
        }
        mats.glRotatef(t, 0f, 1f, 0f);
        mats.glMatrixMode(GL2.GL_PROJECTION);
        mats.glLoadIdentity();
        mats.glFrustumf(-1f, 1f, -1f, 1f, 1f, 100f);
        mats.update();
        gl.glUseProgram(shader.getID());
        gl.glUniformMatrix4fv(uniformMat, 4, false, mats.glGetPMvMvitMatrixf());
        gl.glVertexAttribPointer(Object3D.VERTEXPOSITION, 3, GL.GL_FLOAT,
                false, 0, 0);
        gl.glDrawElements(GL.GL_TRIANGLES, 60, GL.GL_UNSIGNED_INT, 0);
        //obj.display(gl, mats);
        // gl.glFlush();
        gl.glUseProgram(0);
    }

    public static void main(String[] args) {
        SimpleExampleBase t = new SimpleExampleIco();
        t.start();
    }

    class simpleExampleKeyListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            System.out.print(keycode);
            if (java.awt.event.KeyEvent.VK_LEFT == keycode) {
                System.out.print("a");
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    class simpleExampleMouseMotionListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
            System.out.println("dragged:" + e.getX() + " " + e.getY());
        }

        public void mouseMoved(MouseEvent e) {
            System.out.println("moved:" + e.getX() + " " + e.getY());
        }
    }

    class simpleExampleMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            System.out.println("pressed:" + e.getX() + " " + e.getY());
        }

        public void mouseReleased(MouseEvent e) {
        }
    }
}
