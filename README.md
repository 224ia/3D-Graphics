# 3D Graphics Engine

A real-time 3D graphics engine implementing multiple rendering techniques with a unified architecture.

## Features

- **Three rendering methods** (switch instantly with F1-F3):
  - Ray Casting (basic)
  - Ray Tracing (with reflections)
  - Ray Marching (with SDF)

- **Free camera movement**:
  - WASD for movement
  - Mouse for looking around
  - Scroll for zoom

- **Clean architecture**:
  - Scene data separated from rendering logic
  - All renderers share the same scene
  - Modular Java classes (Input, Camera, Scene, ShaderProcessing)

## Controls

| Key | Action |
|-----|--------|
| W/S | Move forward/backward |
| A/D | Strafe left/right |
| Q/E | Move down/up |
| Mouse | Look around |
| Scroll | Zoom in/out |
| F1 | Switch to Ray Casting |
| F2 | Switch to Ray Tracing |
| F3 | Switch to Ray Marching |
| ESC | Exit |

## Built With

- Java 17
- LWJGL 3 (OpenGL binding)
- GLSL shaders
- Gradle build system

## Future Plans

- **Polygon rendering** — add rasterization pipeline with triangle meshes
- **SSBO (Shader Storage Buffer Objects)** — efficient data transfer for large scenes
- **Textures and materials** — support for images and complex material properties
- **Physics integration** — basic collision detection and response
