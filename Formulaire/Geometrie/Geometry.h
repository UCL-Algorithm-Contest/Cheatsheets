#include <cmath>
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

#define PI acos(-1)
#define EPS 1E-9

//_point _vector
typedef struct _point {double x, y; _point(double _x = 0, double _y = 0):x(_x), y(_y) {}
 bool operator==(_point other) const {return (fabs(x - other.x) < EPS) && (fabs(y - other.y) < EPS);};
}_vector; //_vector(AB) = _point(B) - _point(A)
_vector operator+(_vector  v1, _vector  v2) {return _vector(v1.x + v2.x, v1.y + v2.y);}
_vector operator-(_vector  v1, _vector  v2) {return _vector(v1.x - v2.x, v1.y - v2.y);}
_vector operator*(_vector  v, double p) {return _vector(v.x * p, v.y * p);}
_vector operator/(_vector  v, double p) {return _vector(v.x / p, v.y / p);}
double norm(_vector v) {return v.x * v.x + v.y * v.y;}

//product of 2 vectors
double dot(_vector v1, _vector v2) {return v1.x * v2.x + v1.y * v2.y;}
double cross(_vector v1, _vector v2) {return v1.x * v2.y - v1.y * v2.x;}

//add_square / hypot
double add_square(double x, double y) {return x * x + y * y;}
double hypot(double dx, double dy) {return sqrt(add_square(dx, dy));}

//distance between 2 points
double distance(_point p1, _point p2) {return hypot(p1.x - p2.x, p1.y - p2.y);}

//rotate vector (if t is not in rad, just do: t = t * PI / 180)
_vector rotate_counter_clockwise(_vector v, double t)
{return _vector(v.x * cos(t) - v.y * sin(t), v.x * sin(t) + v.y * cos(t));}
_vector rotate_clockwise(_vector v, double t)
{return _vector(v.x * cos(t) + v.y * sin(t), v.y * cos(t) - v.x * sin(t));};

//_line (ax + by + c = 0 with b = 0 for vertical lines; b = 1 for non vertical lines)
struct _line {double a, b, c;
_line(double _a = 0, double _b = 0, double _c = 0):a(_a), b(_b), c(_c) {}};

//build line with 2 points
_line points_to_line(_point p1, _point p2)
{if(fabs(p1.x - p2.x) < EPS) return _line(1, 0, -p1.x);
else {double la = (p2.y - p1.y) / (p1.x - p2.x), lc = -la * p1.x - p1.y; return _line(la, 1, lc);}}

//test if 2 lines are parallel / same / intersect(with intersection point)
bool are_parallel(_line l1, _line l2)
{return (fabs(l1.a - l2.a) < EPS) && (fabs(l1.b - l2.b) < EPS);}
bool are_same(_line l1, _line l2)
{return are_parallel(l1, l2) && (fabs(l1.c - l2.c) < EPS);}
bool are_intersect(_line l1, _line l2, _point &p)
{if(are_parallel(l1, l2)) return false;
 p.x = (l2.b * l1.c - l1.b * l2.c) / (l2.a * l1.b - l1.a * l2.b);
 if(fabs(l1.b) > EPS) p.y = -(l1.a * p.x + l1.c); else p.y = -(l2.a * p.x + l2.c);
 return true;}

//intersection of a line(AB) and a segment(pq)
_point line_intersect_segment(_point p, _point q, _point A, _point B)
{double a = B.y - A.y, b = A.x - B.x, c = B.x * A.y - A.x * B.y, u = fabs(a * p.x + b * p.y + c), v = fabs(a * q.x + b * q.y + c);
 return _point((p.x * v + q.x * u) / (u + v), (p.y * v + q.y * u) / (u + v));}

//distance from point to line defined by 2 points and find the closest point
double distance_to_line(_point p, _point a, _point b, _point &c)
{_vector ap = p - a, ab = b - a; double u = dot(ap, ab) / norm(ab);
 c = a + ab * u; return distance(p, c);}

//distance from point to line segment defined by 2 end points and find the closest point
double distance_to_line_segment(_point p, _point a, _point b, _point &c)
{_vector ap = p - a, ab = b - a; double u = dot(ap, ab) / norm(ab);
 if(u < 0) {c = _point(a.x, a.y); return distance(p, a);}
 if(u > 1) {c = _point(b.x, b.y); return distance(p, b);}
 return distance_to_line(p, a, b, c);}

//given 3 points / 2 vectors, compute the angle
double angle(_point a, _point o, _point b)
{_vector oa = a - o, ob = b - o; return acos(dot(oa, ob) / sqrt(norm(oa) * norm(ob)));}
double angle(_vector v1, _vector v2) {_point o(0, 0); return angle(o + v1, o, o + v2);}

//test if a point is on the left of a line defined by 2 points / they are all collinear
bool is_on_the_left(_point p, _point a, _point b) {return cross(b - a, p - a) > 0;}
bool are_collinear(_point p, _point a, _point b) {return fabs(cross(b - a, p - a)) < EPS;}

//test if a point is inside a circle (0:inside 1:border 2:outside)
int is_inside(_point p, _point o, double r)
{double dx = p.x - o.x, dy = p.y - o.y, Euc = add_square(dx, dy), rSq = r * r;
 return Euc < rSq ? 0 : Euc == rSq ? 1 : 2;}

//length of arc / chord (if t is not in rad, just do: t = t * PI / 180)
//area of circular segment 弓形
double length_arc(double r, double t) {return r * t;}
double length_chord(double r, double t) {return sqrt(2 * r * r * (1 - cos(t)));}
double area_circular_segment(double r, double t) {return r * r / 2 * (PI * t - sin(t));}

//given 2 points and radius, find the center of circle (max 2 possible circles)
bool circle_center(_point p1, _point p2, double r, _point &o)
{double d2 = add_square(p1.x - p2.x, p1.y - p2.y), det = r * r / d2 - 0.25;
 if(det < 0) return false; //reverse p1 and p2 to get another circle center if there're 2 circles
 double h = sqrt(det); o.x = (p1.x + p2.x) * 0.5 + (p1.y - p2.y) * h;
 o.y = (p1.y + p2.y) * 0.5 + (p2.x - p1.x) * h; return true;}

//number of tangents from a point to a circle (with tangent vectors)
int getTangents(_point p, _point o, double r,_vector *tangents)
{_vector u = o - p; double dist = norm(u); if(dist < r) return 0; else if(fabs(dist - r) < EPS)
 {tangents[0] = rotate_counter_clockwise(u, PI / 2); return 1;} else {double ang = asin(r / dist);
 tangents[0] = rotate_counter_clockwise(u, -ang); tangents[1] = rotate_counter_clockwise(u, ang); return 2;}}

//area of triangle
double area(double a, double b, double c)
{double p = (a + b + c) / 2; return sqrt(p * (p - a) * (p - b) * (p - c));}
double area(_point a, _point b, _point c) {return fabs(cross(b - a, c - a));}

//inscribed circle of triangle
double r_inscribed_circle(double a, double b, double c)
{return area(a, b, c) / (0.5 * (a + b + c));}
double r_inscribed_circle(_point a, _point b, _point c)
{return r_inscribed_circle(distance(a, b), distance(b, c), distance(c, a));}
bool inscribed_circle(_point a, _point b, _point c, _point &oic, double &ric)
{ric = r_inscribed_circle(a, b, c); if(fabs(ric) < EPS) return false;
 double ratio = distance(a, b) / distance(a, c);
 _point p = b + (c - b) * ratio / (1 + ratio); _line l1 = points_to_line(a, p);
 ratio = distance(b, a) / distance(b, c); p = a + (c - a) * ratio / (1 + ratio);
 _line l2 = points_to_line(b, p); are_intersect(l1, l2, oic); return true;}

//circumcircle of triangle
double r_circum_circle(double a, double b, double c)
{return a * b * c / (4.0 * area(a, b, c));}
double r_circum_circle(_point a, _point b, _point c)
{return r_circum_circle(distance(a, b), distance(b, c), distance(c, a));}

//test if 3 segments can form a triangle
bool can_form_triangle(double a, double b, double c)
{return (a + b - c > 0) && (a + c - b > 0) && (b + c - a > 0);}

//law of Cosines: c^2 = a^2 + b^2 - 2 * a * b * cos(opposite_angle_c)
//law of Sines: for any side s of triangle, we have: s / sin(opposite_angle_s) = 2 * radius_circum_circle
double opposite_angle_c(double a, double b, double c)
{return acos((a * a + b * b - c * c) / 2 / a / b);}
double c(double a, double b, double opposite_angle_c)
{return sqrt(a * a + b * b - 2 * a * b * cos(opposite_angle_c));}

//polygon representation: vector<_point> poly; remember that poly[0] = poly[n - 1] (the last point = the first point)
//add points: poly.push_back(_point(1, 1)); poly.push_back(_point(2, 4)); poly.push_back(_point(3, 7)); poly.push_back(P[0]);
//parimeter / area of polygon
double perimeter(const vector<_point> &poly)
{double result = 0; for(int i = 0; i < (int)poly.size() - 1; i++) result += distance(poly[i], poly[i + 1]); return result;}
double area(const vector<_point> &poly)
{double result = 0, x1, x2, y1, y2; for(int i = 0; i < (int)poly.size() - 1; i++)
{x1 = poly[i].x; x2 = poly[i + 1].x; y1 = poly[i].y; y2 = poly[i + 1].y; result += (x1 * y2 - x2 * y1);} return fabs(result / 2);}

//test if polygon is convex
bool is_convex(const vector<_point> &poly)
{int s = (int)poly.size(); if(s <= 3) return false; bool l = is_on_the_left(poly[2], poly[0], poly[1]);
 for(int i = 1; i < s - 1; i++) if(is_on_the_left(poly[(i + 2) == s ? 1 : i + 2], poly[i], poly[i + 1]) != l) return false; return true;}

//test if point is in polygon
bool is_inside(_point p, const vector<_point> &poly)
{if((int)poly.size() == 0) return false; double sum = 0; for(int i = 0; i < (int)poly.size() - 1; i++) {
 if(is_on_the_left(poly[i + 1], p, poly[i])) sum += angle(poly[i], p, poly[i + 1]); else sum -= angle(poly[i], p, poly[i + 1]);}
 return fabs(fabs(sum) - 2 * PI) < EPS;}

//cut polygon along a line(result is the left part after cutting)
vector<_point> cut_polygon(_point a, _point b, const vector<_point> &poly)
{vector<_point> result; for(int i = 0; i < (int)poly.size() - 1; i++) {double left1 = cross(b - a, poly[i] - a), left2 = 0;
 if(i != (int)poly.size() - 1) left2 = cross(b - a, poly[i + 1] - a); if(left1 > -EPS) result.push_back(poly[i]);
 if(left1 * left2 < -EPS) result.push_back(line_intersect_segment(poly[i], poly[i + 1], a, b));}
 if(!result.empty() && !(result.back() == result.front())) result.push_back(result.front()); return result;}

//find the convex hull of a set of points
_point pivot(0, 0);
bool angle_compare(_point a, _point b)
{if(are_collinear(b, pivot, a)) return distance(pivot, a) < distance(pivot, b);
 double d1x = a.x - pivot.x, d1y = a.y - pivot.y, d2x = b.x - pivot.x, d2y = b.y - pivot.y;
 return (atan2(d1y, d1x) - atan2(d2y, d2x)) > 0;}
vector<_point> convex_hull(vector<_point> P)
{int i, j, n = (int)P.size(); if(n <= 3) {if(!(P[0] == P[n - 1])) P.push_back(P[0]); return P;}
 int P0 = 0; for(int i = 1; i < n; i++) if(P[i].y < P[P0].y || (P[i].y == P[P0].y && P[i].x > P[P0].x)) P0 = i;
 _point temp = P[0]; P[0] = P[P0]; P[P0] = temp; pivot = P[0]; sort(++P.begin(), P.end(), angle_compare);
 vector<_point> S; S.push_back(P[n - 1]); S.push_back(P[0]); S.push_back(P[1]); i = 2;
 while(i < n) {j = (int)S.size() - 1; if(is_on_the_left(P[i], S[j - 1], S[j])) S.push_back(P[i++]); else S.pop_back();} return S;}

//area / radius of inscribed circle / radius of circumcircle of regular polygon
double area(double a, int n) {return n * a * a / 4 / tan(PI / n);}
double r_inscribed_circle(double a, int n) {return a / 2 / tan(PI / n);}
double r_circum_circle(double a, int n) {return a / 2 / sin(PI / n);}

//volume of pyramid
//surface area of pyramid with regular bottom
double surface_area_pyramid_regular(double a, int n, double h)
{return area(a, n) + n * a / 2 * sqrt(h * h + a * a / 4 / tan(PI / n) / tan(PI / n));}
double volume_pyramid(double area_bottom, double h) {return area_bottom * h / 3;}
double volume_pyramid_regular(double a, int n, double h) {return volume_pyramid(area(a, n), h);}

//surface area(include the bottom) / volume of cone 圆锥
double surface_area_cone(double r, double h) {return PI * r * (r + hypot(r, h));}
double volume_cone(double r, double h) {return PI * r * r * h / 3;}

//surface area / volume of sphere
double surface_area_sphere(double r) {return 4 * PI * r * r;}
double volume_sphere(double r) {return 4 * PI / 3 * r * r * r;}

//surface area(include the top and the bottom) / volume of spherical segment 球台
double surface_area_spherical_segment(double rt, double rb, double R, double h) {return 2 * PI * R * h + PI * rt * rt + PI * rb * rb;}
double volume_spherical_segment(double rt, double rb, double R, double h) {return PI * h / 6 * (3 * rt * rt + 3 * rb * rb + h * h);}

//surface area(include the bottom) / volume of spherical cap 球冠
double surface_area_spherical_cap(double r, double R, double h) {return 2 * PI * R * h + PI * r * r;}
double volume_spherical_cap(double r, double h) {return PI * h / 6 * (3 * r * r + h * h);}