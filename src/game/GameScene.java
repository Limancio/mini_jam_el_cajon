package game;

import engine.Input;
import engine.RenderQuad;
import engine.RenderQuadAnim;
import engine.ShaderProgram;
import engine.Sound;
import engine.Window;
import maths.mat4;

public class GameScene {
	
	public RenderQuad quad;
	public RenderQuadAnim anim_quad;
	public ShaderProgram shader;
	public mat4 projection_matrix;
	
	public Sound sound;
	
	public void handle_input() {
		
	}
	
	public void update_scene() {
		// sound.play();
	}
	
	public void render_scene(Window window, Input input) {
		shader.bind();
		shader.set_uniform("u_proj_matrix", projection_matrix);
		shader.set_uniform("u_mouse_pos", input.mouse_pos);

		quad.render(shader);
		anim_quad.render(shader);
		
		shader.unbind();
	}
	
}
