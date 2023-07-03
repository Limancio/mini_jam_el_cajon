package game;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import engine.CollisionBox;
import engine.Engine;
import engine.Input;
import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Window;
import game.TriggerObject.TriggerType;
import maths.mat4;
import maths.vec3;

public class Level {
	public Player player;
	public int current_level;
	public boolean exit_open;
	
	public StaticObject blocks[];
	public List<TriggerObject> object_list;
	
	public RenderQuad wall_displacement_array[];
	public RenderQuad wall_background;
	public RenderQuad quad_undefined;
	public AnimObject cloud_imagination;

	public RenderQuad carton_sprite;
	public RenderQuad llave_sprite;

	public enum ItemType {
		llave_type,
		carton_type,
	}
	
	public static final int EX = -2;
	public static final int PL = -1;
	public static final int BG = 0;
	public static final int WL = 1;
	public static final int WT = 2;
	
	public Level() {
		object_list = new ArrayList<>();
		blocks 		= new StaticObject[25*25];
	}

	public vec3 get_position_from_index(int x, int y) {
		return(new vec3((float) x * 128.0f, (float) y * 128.0f, 0));
	}
	public vec3 get_position_from_index(float x, float y) {
		return(new vec3(x * 128.0f, y * 128.0f, 0));
	}
	
	public void load_layout(int[] map_layout, int col, int row) {
		for(int y= 0; y < row; y++) {
			for(int x = 0; x < col; x++) {
				int index = x + (((row-1) - y) * col);
				blocks[index] = new StaticObject();
				
				StaticObject it = blocks[index];
				it.block_type 	= map_layout[index];
				
				if(it.block_type == -1) {
					player.position = get_position_from_index(x, y);
				}
				
				switch(it.block_type) {
					case 0: {
						it.position = get_position_from_index(x, y);
						it.quad 	= wall_background;
					} break;
					case 1: {
						it.position = get_position_from_index(x, y);
						it.quad 	= wall_displacement_array[Engine.random.nextInt(3)];
					} break;
					case 2: {
						it.position = get_position_from_index(x, y);
						it.quad 	= wall_displacement_array[Engine.random.nextInt(3)];
					} break;
					default: {
						it.position = get_position_from_index(x, y);
						it.quad 	= quad_undefined;
					} 
				}
			}
		}
	}
	
	public void init() {
		player = new Player();
		player.init();
		
		cloud_imagination = new AnimObject(512.0f, 256.0f, "res/block_bg.png");
		carton_sprite 	  = new RenderQuad(64.0f, 64.0f, "res/carton.png");
		llave_sprite 	  = new RenderQuad(64.0f, 64.0f, "res/llave.png");
		wall_background   = new RenderQuad(128.0f, 128.0f, "res/block_bg.png");
		quad_undefined    = new RenderQuad(128.0f, 128.0f, "res/grass_1.png");
		
		wall_displacement_array = new RenderQuad[3];
		for(int i = 0; i < 3; i++) {
			wall_displacement_array[i] = new RenderQuad(128.0f, 128.0f, "res/block_" + (i + 1) + ".png");
		}
		
	}
	
	public void load_level_1() {
		exit_open = false;
		object_list.clear();
		player.item_bag.clear();
		
		int[] map_layout = {
				WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, PL, BG, BG, BG, BG, BG, BG, BG, EX, BG, WL, 
				WL, BG, BG, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, 
				WL, BG, BG, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, 
				WL, BG, BG, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, 
			//  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  
		};
		
		object_list.add(new TriggerObject(TriggerType.trigger_carton,  get_position_from_index(1.5f, 3.25f), 64.0f, 64.0f, "res/carton.png"));
		object_list.add(new TriggerObject(TriggerType.trigger_carton,  get_position_from_index(6.5f, 3.25f), 64.0f, 64.0f, "res/carton.png"));
		object_list.add(new TriggerObject(TriggerType.trigger_level,  get_position_from_index(12, 3), 128.0f, 128.0f, "res/mytm.png"));
		// object_list.add(new TriggerObject(TriggerType.trigger_water, get_position_from_index(10f, 2.65f), 460.0f, 60.0f, "res/fea_mancha_de_agua.png"));
		// object_list.add(new TriggerObject(TriggerType.trigger_water, get_position_from_index(12f, 2.65f), 460.0f, 60.0f, "res/fea_mancha_de_agua.png"));

		this.current_level 	= 1;
		load_layout(map_layout, 15, 10);
	}
	
	public void load_level_2() {
		exit_open = false;
		object_list.clear();
		player.item_bag.clear();
		
		int[] map_layout = {
				WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, BG, WL, 
				WL, BG, PL, BG, BG, BG, BG, BG, BG, BG, BG, BG, EX, BG, WL, 
				WL, WL, WL, WL, WL, WL, BG, BG, WL, WL, WL, WL, WL, WL, WL, 
				WL, WL, WL, WL, WL, WL, BG, BG, WL, WL, WL, WL, WL, WL, WL, 
				WL, WL, WL, WL, WL, WL, BG, BG, WL, WL, WL, WL, WL, WL, WL, 
			//  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  
		};
		
		// object_list.add(new TriggerObject(TriggerType.trigger_carton,  get_position_from_index(1.5f, 3.25f), 64.0f, 64.0f, "res/carton.png"));
		// object_list.add(new TriggerObject(TriggerType.trigger_water, get_position_from_index(10f, 2.65f), 460.0f, 60.0f, "res/fea_mancha_de_agua.png"));

		player.item_bag.add(ItemType.llave_type);
		this.current_level = 2;
		load_layout(map_layout, 15, 10);
	}

	public void input(Window window, Input input, float delta_time) {
		switch(current_level) {
			case 1: {
				if(window.is_key_press(GLFW_KEY_R)) load_level_1();
			
				if(window.is_key_typed(GLFW_KEY_1) && !player.item_bag.isEmpty()) {
					ItemType new_type = player.item_bag.get(0).equals(ItemType.carton_type) ? ItemType.llave_type : ItemType.carton_type;
					player.item_bag.set(0, new_type);
				}
			} break;
			case 2: {
				if(window.is_key_press(GLFW_KEY_R)) load_level_2();
			} break;
		}
		
		
		player.input(window, delta_time);
	}
	
	public void render(Window window, ShaderProgram shader) {
		for(StaticObject it : blocks) {
			if(it != null) {
				it.render(shader);
			}
		}
		
		for(TriggerObject it : object_list) {
			if(!it.removed) 
				it.render(shader);
		}
		
		player.render(shader);
		/*
		if(player.imagine_active) {
			vec3 cloud_position = new vec3(player.position.x, player.position.y, player.position.z);
			cloud_imagination.position = cloud_position;
			
			cloud_position.x += player.quad.rect.w * 0.5f;
			cloud_position.y += player.quad.rect.h * 1.0f;
			cloud_position.x -= cloud_imagination.quad.rect.w * 0.5f;
			
			cloud_imagination.render(shader);
		}
		*/
		mat4 u_view_matrix = new mat4(1.0f);
		shader.set_uniform("u_view_matrix", u_view_matrix);
		
		int slot = 0;
		for(ItemType item : player.item_bag) {
			RenderQuad icon = carton_sprite;
			
			switch(item) {
			case carton_type:
				icon = carton_sprite;
				break;
			case llave_type:
				icon = llave_sprite;
				break;
			
			}
			
			vec3 position 	  = new vec3((slot * (icon.rect.w * 0.45f)) + window.width * -0.5f, window.height * -0.5f, 0);
			mat4 model_matrix = new mat4(1.0f);
			
			model_matrix.multiply(mat4.translate_matrix(position));
			shader.set_uniform("u_model_matrix", model_matrix);
			
			icon.render(shader);
			slot += 1;
		}
	}

	public void update(Window window, float delta_time) {
		CollisionBox player_box = player.get_box();
		boolean end_level = false;
		
		for(TriggerObject it : object_list) {
			if(!it.removed && it.box.do_overlap(player_box)) {
				switch(it.type) {
					case trigger_carton: {
						it.removed = true;
						player.item_bag.add(ItemType.carton_type);
					} break;
					case trigger_water: { 
					} break;
					case trigger_level: {
						end_level = true;
					} break;
				}
			}
		}
		
		if(end_level) {
			handle_level_end();
		}
		
		player.update(window, this, delta_time);
	}

	public void handle_level_end() {
		switch(current_level) {
			case 1: {
				if(!player.item_bag.isEmpty() && player.item_bag.get(0).equals(ItemType.llave_type)) {
					load_level_2();
				}
			} break;
			case 2: {
				
			} break;
		}
	}
	
}
