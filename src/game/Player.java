package game;

import static org.lwjgl.glfw.GLFW.*;

import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Window;
import maths.mat4;
import maths.vec3;

public class Player {
	public vec3 position;
	public RenderQuad quad;
	
	public Player() {
		position = new vec3(0, 0, 0);
	}
	
	public void input(Window window) {
		if(window.is_key_press(GLFW_KEY_A)) {
			position.x -= 1;
		}
	}
	
	public void render(ShaderProgram shader) {
        mat4 model_matrix = new mat4(1.0f);
        model_matrix.multiply(mat4.translate_matrix(position));
        
		shader.set_uniform("u_model_matrix", model_matrix);
		quad.render(shader);
	}
	
}
