package Util;

import Software.SoftwareRenderer;

public enum RendererType {
    SOFTWARE;

    public Renderer create(int width, int height) {
        return switch (this) {
            case SOFTWARE -> new SoftwareRenderer(width, height);
        };
    }
}