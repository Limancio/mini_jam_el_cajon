package game;

import static org.lwjgl.glfw.GLFW.*;

import engine.Engine;
import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Texture;
import engine.Window;
import maths.vec3;

public class Level {
	public Player player;
	public StaticObject blocks[];
	
	public RenderQuad wall_displacement_array[];
	public RenderQuad wall_background;
	
	public Level() {
		blocks = new StaticObject[50*50];
	}
	
	public void load_layout(int[] map_layout, int col, int row) {
		for(int y= 0; y < row; y++) {
			for(int x = 0; x < col; x++) {
				int index = x + ((7 - y) * 10);
				blocks[index] = new StaticObject();
				StaticObject it = blocks[index];
				it.block_type 	= map_layout[index];
				
				float pos_x = (float) x * (128.0f);
				float pos_y = (float) y * (128.0f);
				
				
				if(it.block_type == 2) {
					it.block_type = 0;
					
					player.position = new vec3(pos_x - 64.0f, pos_y, 0);
				}
				
				switch(it.block_type) {
				case 0: {
					it.position = new vec3(pos_x, pos_y, 0);
					it.quad = wall_background;
				} break;
				case 1: {
					it.position = new vec3(pos_x, pos_y, 0);
					it.quad = wall_displacement_array[Engine.random.nextInt(3)];
				} break;
				}
			}	
		}
	}
	
	public void init() {
		player = new Player();
		player.init();

		wall_displacement_array = new RenderQuad[3];
		for(int i = 0; i < 3; i++) {
			wall_displacement_array[i] = new RenderQuad(0, 0, 128.0f, 128.0f);
			
			wall_displacement_array[i].texture_array    = new Texture[1];
			wall_displacement_array[i].texture_array[0] = new Texture();
			wall_displacement_array[i].texture_array[0].load_texture_file("res/block_" + (i + 1) + ".png");
			wall_displacement_array[i].init();
		}
		
		wall_background = new RenderQuad(0, 0, 128.0f, 128.0f);
		
		wall_background.texture_array    = new Texture[1];
		wall_background.texture_array[0] = new Texture();
		wall_background.texture_array[0].load_texture_file("res/block_bg.png");
		wall_background.init();
		
	}
	
	public void load_level_1() {
		int[] map_layout = {
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
				1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
				1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
				1, 1, 1, 1, 0, 2, 0, 0, 0, 0, 
				1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 
				1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 
				1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		};
		
		load_layout(map_layout, 10, 8);
	}

	public void input(Window window, float delta_time) {
		if(window.is_key_press(GLFW_KEY_R)) {
	    	load_level_1();
		}
		
		player.input(window, delta_time);
	}
	
	public void render(ShaderProgram shader) {
		for(StaticObject it : blocks) {
			if(it != null) {
				it.render(shader);
			}
		}
		
		player.render(shader);
	}

	public void update(Window window, float delta_time) {
		player.update(window, this, delta_time);
	}

}
