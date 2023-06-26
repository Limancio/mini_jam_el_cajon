package engine;

import game.GameScene;
import maths.mat4;

public class Engine implements Runnable {

    public static final int TARGET_FPS = 60;
    public static final int TARGET_UPS = 30;

    public Thread loop_thread;
    public Timer  timer;
    public Window window;
    public Input  input;
	public Audio  audio;
    
    // @NOTE(Liman1): Game
    public GameScene game_scene;
    
    public Engine(String title, int width, int height, boolean vsync) {
        window		= new Window(title, width, height, vsync);
    	loop_thread = new Thread(this, "GAME_LOOP_THREAD");
        timer		= new Timer();
        input		= new Input();
        audio		= new Audio();
        
        game_scene  = new GameScene();
	}
    
    public void init() {
    	window.init();
    	window.set_clear_color(0.75f, 0.5f, 0f, 1f);
    	
    	timer.init();
    	
    	input.init(window);
    	audio.init();
    	
    	game_scene.quad = new RenderQuad( 200, -100, 200.0f, 200.0f);
    	game_scene.quad.texture.load_texture_file("res/mytm.png");
    	game_scene.quad.init();

    	game_scene.anim_quad = new RenderQuadAnim(-200, -100, 200.0f, 200.0f);
    	game_scene.anim_quad.texture.load_texture_file("res/fire1_64.png");
    	game_scene.anim_quad.init();
    	game_scene.anim_quad.init_animation(64,64);
    	
    	game_scene.projection_matrix = new mat4();
    	game_scene.projection_matrix.ortho(window.width, window.height, -1f, 1f);
    	
    	game_scene.shader = new ShaderProgram();
    	game_scene.shader.load_shader_file("res/scene.shader");
    	
    	game_scene.sound = new Sound();
    	game_scene.sound.load_sound_from_file("res/theme2.ogg", false);
    }
    
    @Override
    public void run() {
        float elapsed_time   = 0f;
        float global_time    = 0f;
        float interval_timer = 0f;
        float interval 		 = 1f / TARGET_UPS;
        
        boolean running = true;
        while (running) {
        	elapsed_time = timer.getElapsedTime();
        	global_time    += elapsed_time;
        	interval_timer += elapsed_time;
        	
        	System.out.println(elapsed_time + " - " + global_time);
        	
            window.clear();
        	if(window.window_should_close()) {
        		running = false;
        	}
        	
        	game_scene.handle_input();
            while (interval_timer >= interval) {
            	game_scene.update_scene();
                interval_timer -= interval;
            }
            
            game_scene.render_scene(window, input);
            window.update();

            float loopSlot = 1f / TARGET_FPS;
            double endTime = timer.getLastLoopTime() + loopSlot;
            while (timer.getTime() < endTime) {
                try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
    }
}
