#version 330 core

in vec2 pass_textureCoords;

out vec4 out_colour;

uniform sampler2D textureSampler;
uniform vec3 color;
uniform int overrideTextureColor;

void main(void)
{
	vec4 textureColour = texture(textureSampler, pass_textureCoords);
	
	if(textureColour.a < 0.1f )
	{
		discard;
	}
	else
	{
		if (overrideTextureColor == 1)
		{
			textureColour = vec4(color, textureColour.a);
		}
		out_colour = textureColour;
	}
}
