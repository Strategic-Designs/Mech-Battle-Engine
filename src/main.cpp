

#include <SDL3/SDL.h>
#include <Windows.h>


#pragma comment (lib, "SDL3.lib")


int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInst, LPSTR lpCmdLine, int iCmdShow)
{

	if (!SDL_Init(SDL_INIT_VIDEO))
		return 0;

	SDL_Window* window = SDL_CreateWindow("Mech Battle Engine", 640, 480, SDL_WINDOW_FULLSCREEN);
	SDL_Surface* windowSurface = SDL_GetWindowSurface(window);

	SDL_Event event;
	bool running = true;





	while (running)
	{
		while (SDL_PollEvent(&event))
		{
			
		}
	}












	return 1;
}