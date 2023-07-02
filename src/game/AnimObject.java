package game;

import engine.RenderQuadAnim;
import engine.ShaderProgram;
import maths.mat4;

public class AnimObject extends Entity {
	public RenderQuadAnim quad;

	public void render(ShaderProgram shader) {
        mat4 model_matrix = new mat4(1.0f);
        model_matrix.multiply(mat4.translate_matrix(position));
        
		shader.set_uniform("u_model_matrix", model_matrix);
		quad.render(shader);
	}
	
}
