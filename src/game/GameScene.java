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
	
	public RenderQuadAnim anim_quad;
	public ShaderProgram shader;
	public mat4 projection_matrix;
	
	public Sound sound;
	
	public void init(Window window) {
		player = new Player();
		player.quad = new RenderQuad(0, 0, 100.0f, 200.0f);
    	// quad.texture.load_texture_file("res/mytm.png");
		player.quad.init();

    	anim_quad = new RenderQuadAnim(-200, -100, 200.0f, 200.0f);
    	anim_quad.texture.load_texture_file("res/fire1_64.png");
    	anim_quad.init();
    	anim_quad.init_animation(64, 64, 40);
    	
    	projection_matrix = new mat4();
    	projection_matrix.ortho(window.width, window.height, -1f, 1f);
    	
    	shader = new ShaderProgram();
    	shader.load_shader_file("res/scene.shader");
    	
    	sound = new Sound();
    	sound.load_sound_from_file("res/theme2.ogg", false);
	}
	
	public void handle_input(Window window) {
		player.input(window);
	}
	
	public void update_scene(float delta_time) {
		// sound.play();
		anim_quad.update(delta_time);
	}
	
	public void render_scene(Window window, Input input) {
		shader.bind();
		shader.set_uniform("u_proj_matrix", projection_matrix);
		shader.set_uniform("u_mouse_pos", input.mouse_pos);

		player.render(shader);
		anim_quad.render(shader);
		
		shader.unbind();
	}
	
}
