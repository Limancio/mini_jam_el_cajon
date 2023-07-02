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
	
	public boolean on_ground = true;
	
	public boolean OnTheGround=true;
	public float time=0;
	public int JumpsLeft=2;
	
	public vec3 motion;
	public vec2 box;
	public float player_speed = 500.0f;
	
	public Player() {
		position = new vec3(0, 0, 0);
		box = new vec2(112.0f, 204.0f);
	}

	public void input(Window window, float delta_time) {
		motion = new vec3(0, 0, 0);
		
		if(window.is_key_press(GLFW_KEY_A)) {
			motion.x -= player_speed * delta_time;
		} 
		if(window.is_key_press(GLFW_KEY_D)) {
			motion.x += player_speed * delta_time;
		} 
		
		if(time<=0){
			if (this.on_ground==true|| this.JumpsLeft==1) {
				if(window.is_key_press(GLFW_KEY_SPACE)) {
					time=0.5f;
					
					System.out.println(position.y);
					System.out.println(this.JumpsLeft);
					
					this.JumpsLeft-=1;
					this.on_ground=false;
				}
			}
		}else {
			motion.y += 500 * delta_time;
			time-=delta_time;
		}
	}

	public void Character_Fall(Window window, float delta_time) {
		if(window.is_key_press(GLFW_KEY_W) == false && time <= 0) {
			if(on_ground == false) {
				motion.y -= 400 * delta_time;
			} else {
				motion.y = 0;
			}
		}
	}
	
	public void update(Window window, Level level, float delta_time) {
		vec3 target_position = vec3.add(position, motion);
		vec2 player_center = new vec2(target_position.x + (quad.rect.w * 0.5f) - (box.x * 0.5f), target_position.y + (quad.rect.h * 0.5f) - (box.y * 0.5f));
		CollisionBox player_box = new CollisionBox(player_center.x, player_center.y, player_center.x + box.x, player_center.y + box.y);

		for(StaticObject it : level.blocks) {
			if(it != null) {
				if(it.block_type == 1) {
					CollisionBox block_box = new CollisionBox(it.position.x, it.position.y, 
							it.position.x + it.quad.rect.w, it.position.y + it.quad.rect.h);
					
					
					if(player_box.do_overlap(block_box)) {
						if(motion.x > 0.0f) {
							motion.x = 0f;
						} 
						if(motion.x < 0.0f) {
							motion.x = 0f;
						} 
						
						break;
					}
				}
			}
		}
		
		target_position = vec3.add(position, motion);
		player_center	= new vec2(target_position.x + (quad.rect.w * 0.5f) - (box.x * 0.5f), target_position.y + (quad.rect.h * 0.5f) - (box.y * 0.5f));
		player_box 		= new CollisionBox(player_center.x, player_center.y, player_center.x + box.x, player_center.y + box.y);
		
		on_ground = false;
		for(StaticObject it : level.blocks) {
			if(it != null && it.block_type == 1) {
					CollisionBox check_ground_box = new CollisionBox(it.position.x, it.position.y + (it.quad.rect.h * 0.5f), 
							it.position.x + it.quad.rect.w, it.position.y + (it.quad.rect.h * 1.01f));
					
					if(player_box.do_overlap(check_ground_box)) {
						on_ground = true;
						this.JumpsLeft=2;
						break;
					}
			}
		}
		for(StaticObject it : level.blocks) {
			if(it != null && it.block_type == 1) {
				CollisionBox check_head_box = new CollisionBox(it.position.x, it.position.y - (it.quad.rect.h * 0.05f), 
						it.position.x + it.quad.rect.w, it.position.y + it.quad.rect.h);

				if(player_box.do_overlap(check_head_box)) {
					motion.y = 0f;
					time = 0;
					break;
				}
			}
		}
		
		Character_Fall(window, delta_time);
		position = vec3.add(position, motion);
	}
	
	public void render(ShaderProgram shader) {
		mat4 model_matrix = new mat4(1.0f);
		model_matrix.multiply(mat4.translate_matrix(position));
		
		shader.set_uniform("u_model_matrix", model_matrix);
		quad.render(shader);
	}

	public void init() {
		quad = new RenderQuad(0, 0, 512.0f * 0.5f, 512.0f * 0.5f);
		
		quad.texture.load_texture_file("res/personaje.png");
		quad.init();
	}
	
}
