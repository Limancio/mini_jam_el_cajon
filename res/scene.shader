#version 330 core
layout (location = 0) in vec2  position;
layout (location = 1) in vec4  color;
layout (location = 2) in vec2  uv;
layout (location = 3) in float texture_id;

out vec4 v_color;
out vec2 v_uv;
out float v_texture_id;

uniform mat4 u_proj_matrix;

void main() {	
  gl_Position  = u_proj_matrix * vec4(position.x, position.y, 0.0, 1.0);
  v_color      = color;
  v_uv         = uv;
  v_texture_id = texture_id;
}

#frag
#version 330 core

out vec4 frag_color;

in vec4  v_color;
in vec2  v_uv;
in float v_texture_id;

uniform sampler2D u_bitmap;

void main() {
	if(v_texture_id == 0) {
  		frag_color = v_color;
  	} else {
	    frag_color = texture(u_bitmap, v_uv); 
  	}
}