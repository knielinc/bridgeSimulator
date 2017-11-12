#if LINUXVERSION==1
#include "GL/glut.h"
#else
#include "GLUT/glut.h"
#endif
#include "Scene.h"

// timers
#if MACVERSION==1
#include <CoreServices/CoreServices.h>
#else
#if LINUXVERSION==1
#include <time.h>
#include <sys/time.h>
#include <sys/times.h>
#else
#include <windows.h> // mac :
#endif
#endif

Scene *sc=NULL;
unsigned int lastTick=0;
unsigned int stepping= 5000; //(unsigned int)(1e6 * Scene::step);
double mspt;

// windows only
void timerInit()
{
#if MACVERSION==1
	// nothing to do...
#else
#if LINUXVERSION==1
	// nothing to do...
#else
	LARGE_INTEGER tps_long;
	QueryPerformanceFrequency(&tps_long);
	mspt = 1e6/(double)tps_long.QuadPart;
#endif
#endif
}

unsigned int getTime()
{
	#if MACVERSION==1
	// mac
	UnsignedWide uCur;
	Microseconds(&uCur);
	return uCur.lo;
#else
#if LINUXVERSION==1
	struct timeval tv;
	struct timezone tz;
	tz.tz_minuteswest = 0;
	tz.tz_dsttime = 0;
	gettimeofday(&tv,&tz);
 	return (unsigned int) ( (tv.tv_sec*1000)+(tv.tv_usec/1000) )*10000;
#else
	// windows
	LARGE_INTEGER tick;
	QueryPerformanceCounter(&tick);
	return (unsigned int) (mspt*tick.QuadPart);
#endif
#endif
}

void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);//Clear the screen

	unsigned int tm = getTime();
	if (tm-lastTick > stepping)
	{
		lastTick += stepping;
		sc->Update();
	}
	sc->Render();

    glFlush();//Draw everything to the screen
    glutPostRedisplay();//Start drawing again
    glutSwapBuffers();
}

void reshape(int width, int height)
{
    glViewport(0, 0, width, height);
}

void idle(void)
{
    glutPostRedisplay();
}

int main(int argc, char** argv)
{
	sc = new Scene(argc, argv);
	//sc->Init(); - done is Scene constructor
    glutInit(&argc, argv);

    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE);
    glutInitWindowSize(600, 600);
    glutCreateWindow("Exercise 1");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(idle);
	timerInit();
    lastTick = getTime();

    glEnable(GL_LINE_SMOOTH);
    glutMainLoop();
    delete sc;

	return EXIT_SUCCESS;
}
