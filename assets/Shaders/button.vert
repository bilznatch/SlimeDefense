attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
varying vec4 v_color;
varying vec2 v_texCoords;

void main()                   
{ 
	// set our varying variables for use in frag shader
	v_color = vec4(1, 0, 0, 1);  
	v_texCoords = a_texCoord0;
	
	// sgl_Position is a special output variable from 
	// openGL that must be set in the vertex shader
	gl_Position =  u_projTrans * a_position;
}