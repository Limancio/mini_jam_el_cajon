package game;

import static org.lwjgl.glfw.GLFW.*;

import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Window;
import maths.mat4;
import maths.vec3;

public class Player extends Entity {
	public RenderQuad quad;
	public boolean OnTheGround=true;
	public float time=0;
	public int JumpsLeft=2;
	public Player() {
		position = new vec3(0, 0, 0);

	}

	public void input(Window window, float delta_time) {
		if(window.is_key_press(GLFW_KEY_A)) {
			position.x -= 150.0 * delta_time;

		}
		if(window.is_key_press(GLFW_KEY_D)) {
			position.x += 150.0 * delta_time;

		}
		if(window.is_key_press(GLFW_KEY_W)) {
			position.y += 150.0 * delta_time;
			System.out.println(position.y);
		}
		if(time<=0){
			if (this.OnTheGround==true|| this.JumpsLeft==1) {
				if(window.is_key_press(GLFW_KEY_SPACE)) {
					time=1;
					System.out.println(position.y);
					System.out.println(this.JumpsLeft);
					this.JumpsLeft-=1;
					this.OnTheGround=false;
				}
			}
		}else {
			position.y += 100 * delta_time;
			time-=delta_time;
		}

	}

	public void render(ShaderProgram shader) {
		mat4 model_matrix = new mat4(1.0f);
		model_matrix.multiply(mat4.translate_matrix(position));
		shader.set_uniform("u_model_matrix", model_matrix);
		quad.render(shader);
	}

	public void Character_Fall(Window window,float delta_time) {
		if(window.is_key_press(GLFW_KEY_W)==false && time<=0) {
			if(position.y>0) {
				position.y -= 200 * delta_time;
				if(position.y<=0) {
					this.OnTheGround=true;
					this.JumpsLeft=2;
				}
			}
		}
	}
}
