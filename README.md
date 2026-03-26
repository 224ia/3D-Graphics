# Software 3D Renderer

A clean, modular software 3D renderer written in Java. Features a pluggable renderer architecture, OBJ model loading, and intuitive camera controls.

## Architecture

The engine is designed with clean separation of concerns:

- **Engine** - Core game loop and scene management
- **Scene** - Container for game objects and camera
- **Renderer** - Abstract class for rendering implementations
- **Projection** - Handles FOV and perspective projection
- **Input/Mouse** - Abstracted input handling

## Features

- ✅ Perspective projection with adjustable FOV (30-150°)
- ✅ OBJ model loading with automatic directory scanning
- ✅ Camera movement (WASD + mouse)
- ✅ Object rotation and scaling
- ✅ Backface culling
- ✅ Diffuse lighting
- ✅ Mouse wheel zoom
- ✅ Clean API for end users

## Controls

| Key | Action |
|-----|--------|
| `WASD` | Move camera forward/back/left/right |
| `Q/E` | Move camera up/down |
| `Mouse` | Look around |
| `Mouse Wheel` | Zoom in/out |
| `ESC` | Toggle mouse lock |

## Quick Start

```java
// Choose renderer
Renderer renderer = RendererType.SOFTWARE.create(1920, 1080);

// Create engine
Engine engine = new Engine(renderer, 1920, 1080, 70); // FOV 70°

// Setup scene
engine.setScene(0.1f); // camera speed
Scene scene = engine.getScene();

// Add objects
scene.addObject(new Vector3f(0, 0, 5),    // position
                new Vector3f(0, 0, 0),    // rotation
                new Vector3f(1, 1, 1),    // scale
                Color.WHITE,              // color
                "cube");                 // model name

// Start engine
engine.start();
```
## How to Run

  - Clone the repository
  - Place your OBJ models in src/Models/
  - Open in your IDE
  - Run Main.java

## Dependencies

  - JOML (Java OpenGL Math Library) - included in lib/

## Next Steps

  - [ ] Own rasterizer (per-pixel drawing)
  - [ ] Z-buffer (replace painter's algorithm)
  - [ ] Textures (UV coordinates)
  - [ ] OpenGL renderer (GPU acceleration)
