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

import maths.vec2;
import maths.vec4;

public class RenderQuadAnim extends RenderQuad {
	vec2 sprite_size;
	vec2 sprite_count;
	vec2 sprite_uv_size;

	int current_frame;
	int frame_count;

	float target_fps;
	float elapsed_time;
	
	public RenderQuadAnim(float x, float y, float w, float h) {
		super(x, y, w, h);
	}
	
	public void init_animation(int frame_width, int frame_height, int target_fps) {
		this.sprite_size    = new vec2(frame_width, frame_height);
		this.sprite_count   = new vec2(texture.width / frame_width, texture.height / frame_height);
		this.sprite_uv_size = new vec2(1f / this.sprite_count.x, 1f / this.sprite_count.y);
		
		this.frame_count = (int) (sprite_count.x * sprite_count.y);
		
		this.target_fps	   = (float) target_fps;
		this.elapsed_time  = 0;
		this.current_frame = 0;
	}

	public void update(float delta_time) {
		elapsed_time += delta_time;
		
		if(elapsed_time > (1f / (float) target_fps)) {
			elapsed_time = 0;
			
			current_frame += 1;
			if(current_frame > frame_count) {
				current_frame = 0;
			}
		}
	}

	@Override
	public void render(ShaderProgram shader) {
		glBindVertexArray(vao);

		if(texture.texture_id[0] > 0) {
			shader.set_uniform("u_bitmap", 0);
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, texture.texture_id[0]);
		}
		
		float x_offset = current_frame % sprite_count.x;
		float y_offset = sprite_count.y - (float) Math.floor((float) current_frame / sprite_count.x);
		
		shader.set_uniform("u_uv_offset", new vec4(x_offset, y_offset, sprite_uv_size.x, sprite_uv_size.y));
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
