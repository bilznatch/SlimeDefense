package com.lol.fraud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class RainbowShader extends ShaderProgram {
    RainbowShader () {
        super(Gdx.files.internal("Shaders/button.vert"), Gdx.files.internal("Shaders/button.frag"));
        if (!isCompiled()) {
            throw new RuntimeException("Shader compilation failed:\n" + getLog());
        }
    }
}
