#include "Vec2.h"
#include <math.h>
#include <stdio.h>

// gravitational acceleration (9.81)
static const double g = 9.81;
static const double e = 2.71828182846;


// Exercise 1
// hanging mass point

// stiffness, mass, damping, L, startT, ANALYTIC, 0, 0, startPos, startV
void AdvanceTimeStep1(double k, double m, double d, double L, double dt, int method, double p1, double v1, double& p2, double& v2)
{
  switch(method){

    //explicit Euler
    case 1:
      {
        //aproximate Yn+1 =Yn+hf(t,y'(tn)).
        //y'(tn) = f(tn, y(t))



        double tmp_p2 = p2;
        double tmp_v2 = v2;

        p2 = p2 + dt*tmp_v2;
        v2 = v2 + dt*(((-k*(tmp_p2 + L) - m*g) - d*tmp_v2)/m);

        /*
        printf("\n Euler: \n Friction force: %f ",d*v2);

        printf("velocity: %f ",v2);

        printf("Pos: %f ",p2);
        */
        break;
      }

    //symplectic_euler
    case 2:{

      v2 = v2 + dt*(((-k*(p2 + L) - m*g) - d*v2)/m);
      p2 = p2 + dt*v2;

      break;
    }
    //explicit Midpoint = 3
    case 3:
      {
        /*
        BLACKBOARD NOTES
        y(t+h) = y(t) + h*f(t+(h/2), y(t) + h/2 * f(t,y(t)))
        explicit: y' (t + h/2) = (y(t+h)-y(t))/h
        implicit: (1/2) * (y(t) + y(t+h))
        y(t+h) = y(t) + hf(t + (h/2), (1/2) * (y(t) + y(t+h)))

        */
        //aproximate Yn+1 =Yn+hf(t+h/2,y(tn+h/2)).
        //For an estimation we use an explicit Euler step to get
        //k1 = Yn + h/2 * f(t,Yn)
        //Yn+1= Yn+hf(t+h/2,k1)

        double h = dt;
        double fx1 = v2;
        double x1 = p2;
        //k1
        double x2 = x1 + (h/2)*fx1;

        double fy1 = (((-k*(p2 + L) - m*g) - d*v2)/m);
        double y1 = v2;
        //k1
        double y2 = v2 + (h/2)*fy1;

        //f(t+h/2,k1)
        double fx2 = y2;
        double fy2 = (((-k*(x2 + L) - m*g) - d*y2)/m);

        //Yn+1
        p2 = x1 + h * fx2;
        v2 = y1 + h * fy2;
        /*
        printf("\n Midpoint: \n Friction force: %f ",d*v2);

        printf("velocity: %f ",v2);

        printf("Pos: %f \n",p2);
        */
        break;
      }
      //implicit Euler
    case 4:
      {
        double h = dt;
        v2 = (m*v2 - h*(g*m+k*(L+p2)))/(d*h + h*h*k + m);
        p2 = p2 + dt*v2;

        break;
      }
      //analytic solution
    case 5:
      {
      //y(t) = c1 * e^(alpha * t) * cos(Beta * t) + c2 * e^(alpha * t) * sin (Beta * t) - L - (m * g / k)
      //y'(t) = e^(alpha * t) * (sin(Beta * t) * (alpha * c2 - Beta * c2) + cos(Beta * t) * (alpha * c1 - Beta * c2))

      double c1 = (m * g) / k; // calculated by hand
      double c2 = (d * m * g) / (k * sqrt(4 * k * m - (d * d))); // calculated by hand
      double alpha = (-1) * (d / (2 * m));
      double Beta = sqrt((4 * k * m) - (d * d))/(2 * m);
      double t = dt;

      p2 = c1 * pow(e,(alpha * t)) * cos(Beta * t) + c2 * pow(e,(alpha * t)) * sin (Beta * t) - L - (m * g / k);
      v2 = pow(e,(alpha * t)) * (sin(Beta * t) * ((alpha * c2) - (Beta * c1)) + cos(Beta * t) * ((alpha * c1) + (Beta * c2)));
      //double v3 = pow(e, (alpha * t)) * (c1 *(alpha * cos(Beta * t) - Beta * sin(Beta*t))  + c2 *(alpha * sin(Beta * t) + Beta * cos(Beta*t)) );
      //printf("difference: %f\n", v2-v3);
      /*
      printf("\n analytic: \n Friction force: %f ",d*v2);
      printf("velocity: %f ",v2);
      printf("alphat: %f ",alpha*t);
      printf("Pos: %f \n",p2);
      */
      break;
    }
    default:{
      break;
    }
  }
    //v2 = v2 + sin(p2);
}


// Exercise 3
// falling triangle
void AdvanceTimeStep3(double k, double m, double d, double L, double dt,
                      Vec2& p1, Vec2& v1, Vec2& p2, Vec2& v2, Vec2& p3, Vec2& v3)
{
  //SYMPlECTIC EULER
  //p1 += Vec2(1,1);

  //SPRING FORCES

  double spAB = k*((p1 - p2).length() - L);
  double spBC = k*((p2 - p3).length() - L);
  double spAC = k*((p3 - p1).length() - L);

  //printf("spAB : %f spBC : %f spAC: %f \n",spAB,spBC,spAC);

  //X DIRECTION
  //init positions
  double Ax = p1.x;
  double Bx = p2.x;
  double Cx = p3.x;
  //init velocities
  double Avx = v1.x;
  double Bvx = v2.x;
  double Cvx = v3.x;

  //Y DIRECTION
  //init positions
  double Ay = p1.y;
  double By = p2.y;
  double Cy = p3.y;
  //init velocities
  double Avy = v1.y;
  double Bvy = v2.y;
  double Cvy = v3.y;

  //Total velocities

  double Av = sqrt(Avx * Avx + Avy * Avy);
  double Bv = sqrt(Bvx * Bvx + Bvy * Bvy);
  double Cv = sqrt(Cvx * Cvx + Cvy * Cvy);


  //ANGLES
  double AngleAB = atan2(Ay-By,Ax-Bx);
  double AngleBC = atan2(By-Cy,Bx-Cx);
  double AngleCA = atan2(Cy-Ay,Cx-Ax);
  double AngleBA = atan2(By-Ay,Bx-Ax);
  double AngleCB = atan2(Cy-By,Cx-Bx);
  double AngleAC = atan2(Ay-Cy,Ax-Cx);

  double AngleAv = atan2(Avy,Avx);
  double AngleBv = atan2(Bvy,Bvx);
  double AngleCv = atan2(Cvy,Cvx);


  //printf("\nAngleAB : %f AngleBC : %f AngleCA: %f AngleBA: %f AngleCB: %f AngleAC: %F\n",AngleAB,AngleBC,AngleCA,AngleBA,AngleCB,AngleAC);

  //X DIRECTION STEP
  double AvxDeltaOut = dt * (1./m) * (((1./2.) * cos(AngleBA) * spAB) + ((1./2.) * cos(AngleCA) * spAC) - (d * cos(AngleAv) * Av));
  double AxDeltaOut = dt*(Avx + AvxDeltaOut);

  double BvxDeltaOut = dt * (1./m) * (((1./2.) * cos(AngleAB) * spAB) + ((1./2.) * cos(AngleCB) * spBC) - (d * cos(AngleBv) * Bv));
  double BxDeltaOut = dt*(Bvx + BvxDeltaOut);

  double CvxDeltaOut = dt * (1./m) * (((1./2.) * cos(AngleAC) * spAC) + ((1./2.) * cos(AngleBC) * spBC) - (d * cos(AngleCv) * Cv));
  double CxDeltaOut = dt*(Cvx + CvxDeltaOut);

  //Y DIRECTION
  //repulsive spring force in the ground
  double GroundA = Ay > -1 ? 0 : 100 * (-1 - Ay);
  double GroundB = By > -1 ? 0 : 100 * (-1 - By);
  double GroundC = Cy > -1 ? 0 : 100 * (-1 - Cy);

  //printf("Ground A : %f GroundB : %f Ground C: %f \n",GroundA,GroundB,GroundC );

  //Y DIRECTION STEP
  double AvyDeltaOut = dt * (1./m) * (((1./2.) * sin(AngleBA) * spAB) + ((1./2.) * sin(AngleCA) * spAC) - m*g + GroundA - (d * sin(AngleAv) * Av));
  double AyDeltaOut = dt*(Avy + AvyDeltaOut);

  double BvyDeltaOut = dt * (1./m) * (((1./2.) * sin(AngleAB) * spAB) + ((1./2.) * sin(AngleCB) * spBC) - m*g + GroundB - (d * sin(AngleBv) * Bv));
  double ByDeltaOut = dt*(Bvy + BvyDeltaOut);

  double CvyDeltaOut = dt * (1./m) * (((1./2.) * sin(AngleAC) * spAC) + ((1./2.) * sin(AngleBC) * spBC) - m*g + GroundC - (d * sin(AngleCv) * Cv));
  double CyDeltaOut = dt*(Cvy + CvyDeltaOut);

  //printf("forceAyB %f %f %f %f \n\n",AngleAB, spAB, sin(AngleAB) ,((1./2.) * sin(AngleAB) * spAB));

  //Assigning output
  p1 += Vec2(AxDeltaOut,AyDeltaOut);
  p2 += Vec2(BxDeltaOut,ByDeltaOut);
  p3 += Vec2(CxDeltaOut,CyDeltaOut);

  v1 += Vec2(AvxDeltaOut,AvyDeltaOut);
  v2 += Vec2(BvxDeltaOut,BvyDeltaOut);
  v3 += Vec2(CvxDeltaOut,CvyDeltaOut);


  /*official code
  	// Compute gravitational and damping forces for all mass points
  	Vec2 f1 = Vec2(0, -g * m) - d * v1;
  	Vec2 f2 = Vec2(0, -g * m) - d * v2;
  	Vec2 f3 = Vec2(0, -g * m) - d * v3;

  	// Compute spring forces
  	Vec2 springF1 = -k * ((p1 - p2).length() - L) * (p1 - p2).getNormalizedCopy();
  	Vec2 springF2 = -k * ((p2 - p3).length() - L) * (p2 - p3).getNormalizedCopy();
  	Vec2 springF3 = -k * ((p3 - p1).length() - L) * (p3 - p1).getNormalizedCopy();

  	// Add spring forces to vertices
  	f1 += springF1 - springF3;
  	f2 += springF2 - springF1;
  	f3 += springF3 - springF2;

  	// Additional force for collision
  	const double kr = 100;
  	if (p1.y < -1)
  		f1 -= kr * Vec2(0, 1 + p1.y);
  	if (p2.y < -1)
  		f2 -= kr * Vec2(0, 1 + p2.y);
  	if (p3.y < -1)
  		f3 -= kr * Vec2(0, 1 + p3.y);

  	// Velocity update
  	v1 += dt / m * f1;
  	v2 += dt / m * f2;
  	v3 += dt / m * f3;

  	// Position update
  	p1 += dt * v1;
  	p2 += dt * v2;
  	p3 += dt * v3;*/
}
