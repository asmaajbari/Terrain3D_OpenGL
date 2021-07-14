package core.kernel;


import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;

import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import core.utils.ImageLoader;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode; 
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;

import core.utils.ImageLoader;

/**
 * 
 * @author oreon3D
 * GLFW Window implementation
 *
 */
public class Window {

	private static Window instance = null;

	private long window;
	private int width;
	private int height;
	
	public static Window getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new Window();
	    }
	      return instance;
	}
	
	public void init(){}
	
	public void create(int width, int height) {
		
		if (!glfwInit()) {
			System.err.println("Error 001");
		}
		//create window
		window = glfwCreateWindow(width, height, "3D ENGINE",0,0); 
		
		if(window == 0) {
            System.err.println("Error002");
			System.exit(0);
		}
		ByteBuffer bufferedImage = ImageLoader.loadImageToByteBuffer("./res/logo/logo.png");
		
		GLFWImage image = GLFWImage.malloc();
		
		image.set(32, 32, bufferedImage);
		
		GLFWImage.Buffer images = GLFWImage.malloc(1);
        images.put(0, image);
		
		glfwSetWindowIcon(window, images);
        
		//set position of window
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, vid.width()/2-width/2,vid.height()/2-height/2);
		//context
		glfwMakeContextCurrent(window);
		//capability
		GL.createCapabilities();
		//set color
		glClearColor(1, 0, 0, 1);
		glfwShowWindow(window);
	}
	
	public void render()
	{
		glfwSwapBuffers(window);
	}
	
	public void dispose()
	{
		glfwDestroyWindow(window);
	}
	
	public boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window);
	}
	
	public void setWindowSize(int x, int y){
		glfwSetWindowSize(window, x, y);
		setHeight(y);
		setWidth(x);
		Camera.getInstance().setProjection(70, x, y);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public long getWindow() {
		return window;
	}

	public void setWindow(long window) {
		this.window = window;
	}
}
