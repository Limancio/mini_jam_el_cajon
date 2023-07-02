package game;

import engine.Input;
import engine.RenderQuad;
import engine.RenderQuadAnim;
import engine.ShaderProgram;
import engine.Sound;
import engine.Window;
import maths.mat4;

public class GameScene {
	public Player player;
	public AnimObject fire;
	
	public ShaderProgram shader;
	public mat4 projection_matrix;
	
	public Sound sound;
	
	public void init(Window window) {
		player = new Player();
		player.quad = new RenderQuad(0, 0, 100.0f, 200.0f);
    	// quad.texture.load_texture_file("res/mytm.png");
		player.quad.init();

		fire = new AnimObject();
		fire.quad = new RenderQuadAnim(-200, -100, 200.0f, 200.0f);
		fire.quad.texture.load_texture_file("res/fire1_64.png");
    	fire.quad.init();
    	fire.quad.init_animation(64, 64, 40);
    	
    	projection_matrix = new mat4();
    	projection_matrix.ortho(window.width, window.height, -1f, 1f);
    	
    	shader = new ShaderProgram();
    	shader.load_shader_file("res/scene.shader");
    	
    	sound = new Sound();
    	sound.load_sound_from_file("res/theme2.ogg", false);
	}
	
	public void handle_input(Window window, float delta_time) {
		player.input(window, delta_time);
	}
	
	public void update_scene(Window window, float delta_time) {
		// sound.play();
		fire.quad.update(delta_time);
		player.Character_Fall(window ,delta_time);
	}
	
	public void render_scene(Window window, Input input) {
		shader.bind();
		shader.set_uniform("u_proj_matrix", projection_matrix);
		shader.set_uniform("u_mouse_pos", input.mouse_pos);

		player.render(shader);
		fire.render(shader);
		
		shader.unbind();
	}
	
}
