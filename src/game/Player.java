package game;

import static org.lwjgl.glfw.GLFW.*;

import engine.CollisionBox;
import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Window;
import maths.mat4;
import maths.vec2;
import maths.vec3;

public class Player extends Entity {
	public RenderQuad quad;
<<<<<<< HEAD
	
	public boolean OnTheGround=true;
	public float time=0;
	public int JumpsLeft=2;
	
	public vec3 motion;
	public vec2 box;
	public float player_speed = 500.0f;
	
	public Player() {
		position = new vec3(0, 0, 0);
		box = new vec2(112.0f, 204.0f);
=======
	public boolean OnTheGround=true;
	public float time=0;
	public int JumpsLeft=2;
	public Player() {
		position = new vec3(0, 0, 0);

>>>>>>> 3d0c7b455561bc3a58305aa3cc24b3a90893ebe5
	}

	public void input(Window window, float delta_time) {
		motion = new vec3(0, 0, 0);
		
		if(window.is_key_press(GLFW_KEY_A)) {
<<<<<<< HEAD
			motion.x -= player_speed * delta_time;
		}
		if(window.is_key_press(GLFW_KEY_D)) {
			motion.x += player_speed * delta_time;
		}
		if(window.is_key_press(GLFW_KEY_W)) {
			motion.y += player_speed * delta_time;
		}
		if(window.is_key_press(GLFW_KEY_S)) {
			motion.y -= player_speed * delta_time;
		}
		
		if(time<=0){
			if (this.OnTheGround==true|| this.JumpsLeft==1) {
				if(window.is_key_press(GLFW_KEY_SPACE)) {
					time=0.5f;
					
					System.out.println(position.y);
					System.out.println(this.JumpsLeft);
					
=======
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
>>>>>>> 3d0c7b455561bc3a58305aa3cc24b3a90893ebe5
					this.JumpsLeft-=1;
					this.OnTheGround=false;
				}
			}
		}else {
<<<<<<< HEAD
			motion.y += 500 * delta_time;
			time-=delta_time;
		}
	}

	public void Character_Fall(Window window,float delta_time) {
		if(window.is_key_press(GLFW_KEY_W)==false && time<=0) {
			if(position.y>0) {
				motion.y -= 500 * delta_time;
			}
=======
			position.y += 100 * delta_time;
			time-=delta_time;
>>>>>>> 3d0c7b455561bc3a58305aa3cc24b3a90893ebe5
		}

	}
<<<<<<< HEAD
	
	public void update(Window window, Level level, float delta_time) {
		Character_Fall(window, delta_time);
		
		boolean ignore_movement = false;
		vec3 target_position = vec3.add(position, motion);

		vec2 player_center = new vec2(target_position.x + (quad.rect.w * 0.5f) - (box.x * 0.5f), target_position.y + (quad.rect.h * 0.5f) - (box.y * 0.5f));
		CollisionBox player_box = new CollisionBox(player_center.x, player_center.y, player_center.x + box.x, player_center.y + box.y);
		
		for(StaticObject it : level.blocks) {
			if(it != null) {
				if(it.block_type == 1) {
					CollisionBox block_box  = new CollisionBox(it.position.x, it.position.y, 
							it.position.x + it.quad.rect.w, it.position.y + it.quad.rect.h);
					
					if(player_box.do_overlap(block_box)) {
						ignore_movement = true;
						break;
					}
				}
			}
		}

		if(position.y<=0) {
			this.OnTheGround=true;
			this.JumpsLeft=2;
		}
		
		if(ignore_movement == false) {
			position = target_position;
		}
	}
=======
>>>>>>> 3d0c7b455561bc3a58305aa3cc24b3a90893ebe5

	public void render(ShaderProgram shader) {
		mat4 model_matrix = new mat4(1.0f);
		model_matrix.multiply(mat4.translate_matrix(position));
		shader.set_uniform("u_model_matrix", model_matrix);
		quad.render(shader);
	}

<<<<<<< HEAD
	public void init() {
		quad = new RenderQuad(0, 0, 512.0f * 0.5f, 512.0f * 0.5f);
		
		quad.texture.load_texture_file("res/personaje.png");
		quad.init();
	}
	
=======
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
>>>>>>> 3d0c7b455561bc3a58305aa3cc24b3a90893ebe5
}
