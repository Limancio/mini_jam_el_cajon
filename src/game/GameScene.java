package game;

import java.util.Random;

import engine.Input;
import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Sound;
import engine.Window;
import maths.mat4;
import maths.vec3;

public class GameScene {
	public Camera camera; 
	public Level level;
	
	public ShaderProgram shader;
	public mat4 projection_matrix;
	
	public Sound sound;
	
	public void init(Window window) {
    	projection_matrix = new mat4();
    	projection_matrix.ortho(window.width, window.height, -1f, 1f);
    	
		camera = new Camera();
		
		/*
			fire = new AnimObject();
			fire.quad = new RenderQuadAnim(-200, -100, 200.0f, 200.0f);
			fire.quad.texture.load_texture_file("res/fire1_64.png");
	    	fire.quad.init();
	    	fire.quad.init_animation(64, 64, 40);
    	*/

    	shader = new ShaderProgram();
    	shader.load_shader_file("res/scene.shader");
    	
    	sound = new Sound();
    	sound.load_sound_from_file("res/theme2.ogg", false);

    	level = new Level();
    	level.init();
    	
    	level.load_level_1();
	}
	
	public void handle_input(Window window, float delta_time) {
		level.input(window, delta_time);
	}
	
	public void update_scene(Window window, float delta_time) {
		// sound.play();
<<<<<<< HEAD
		// fire.quad.update(delta_time);
		
		level.update(window, delta_time);
		camera.update(level.player.position);
=======
		fire.quad.update(delta_time);
		player.Character_Fall(window ,delta_time);
>>>>>>> 3d0c7b455561bc3a58305aa3cc24b3a90893ebe5
	}
	
	public void render_scene(Window window, Input input) {
		shader.bind();
		
		mat4 view_matrix = camera.get_view_matrix(window);
		shader.set_uniform("u_view_matrix", view_matrix);
		
		shader.set_uniform("u_proj_matrix", projection_matrix);
		shader.set_uniform("u_mouse_pos", input.mouse_pos);

		level.render(shader);
		
		// fire.render(shader);
		
		shader.unbind();
	}
	
}
