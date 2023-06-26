package engine;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import maths.vec4;

public class RenderQuadAnim extends RenderQuad {
	public int frame_width;
	public int frame_height;

	public float uv_width;
	public float uv_height;

	public int frame_count;
	public int current_frame;
	
	public RenderQuadAnim(float x, float y, float w, float h) {
		super(x, y, w, h);
	}
	
	public void init_animation(int frame_width, int frame_height) {
		this.frame_width   = frame_width;
		this.frame_height  = frame_height;
		this.frame_count   = (int) (((float) texture.width / (float) frame_width) * ((float) texture.height / (float) frame_height));
		this.current_frame = 0;

		uv_width  = ((float) texture.width  / (float) frame_width);
		uv_height = ((float) texture.height / (float) frame_height);
	}
	
	public void update(float delta_time) {
		
	}

	@Override
	public void render(ShaderProgram shader) {
        glBindVertexArray(vao);
        
        if(texture.texture_id[0] > 0) {
    		shader.set_uniform("u_bitmap", 0);
    		glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.texture_id[0]);
        }
        
		float uv_x_offset = 1f / (float) uv_width;
		float uv_y_offset = 1f / (float) uv_height; 
		
		shader.set_uniform("u_uv_offset", new vec4(0f, 0f, uv_x_offset, uv_y_offset));
		shader.set_uniform("u_animate_uv", 1f);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        if(texture.texture_id[0] > 0) {
            glBindTexture(GL_TEXTURE_2D, 0);
        }
        
        glBindVertexArray(0);
	}
}
