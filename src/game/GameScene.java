package game;

import engine.RenderQuad;
import engine.ShaderProgram;
import engine.Sound;
import maths.mat4;

public class GameScene {
	
	public RenderQuad quad;
	public ShaderProgram shader;
	public mat4 projection_matrix;
	
	public Sound sound;
	
	public void handle_input() {
		
	}
	
	public void update_scene() {
		sound.play();
	}
	
	public void render_scene() {
		shader.bind();
		shader.set_uniform("u_bitmap", 0);
		shader.set_uniform("u_proj_matrix", projection_matrix);
		
		quad.render();
		
		shader.unbind();
	}
	
}
