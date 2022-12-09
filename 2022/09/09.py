import math
import sys


class Point:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def __str__(self):
        return f"({self.x}, {self.y})"

    def __add__(self, other):
        x = self.x + other.x
        y = self.y + other.y
        return Point(x,y)

    def __sub__(self, other):
        x = self.x - other.x
        y = self.y - other.y
        return Point(x,y)



lines = sys.stdin.read().strip().split("\n")

dirs = {
    "R": Point(1, 0),
    "D": Point(0, 1),
    "L": Point(-1, 0),
    "U": Point(0, -1)
}
points = [Point() for i in range(10)]
visited1 = set()
visited1.add((points[1].x, points[1].y))
visited2 = set()
visited2.add((points[-1].x, points[-1].y))

for line in lines:
    direction, num = line.split()
    for _ in range(int(num)):
        move = dirs[direction]
        points[0] += move
        for i in range(len(points)-1):
            h = points[i]
            t = points[i+1]
            between = h - t
            if abs(between.x) >= 2 or abs(between.y) >= 2:
                if between.x != 0:
                    between.x //= abs(between.x)
                if between.y != 0:
                    between.y //= abs(between.y)
                points[i+1] += between

        visited1.add((points[1].x, points[1].y))
        visited2.add((points[-1].x, points[-1].y))

maxX = max(visited2, key=lambda p: p[0])[0]
minX = min(visited2, key=lambda p: p[0])[0]
maxY = max(visited2, key=lambda p: p[1])[1]
minY = min(visited2, key=lambda p: p[1])[1]

# visualization
for y in range(minY, maxY+1):
    for x in range(minX, maxX+1):
        if x == y == 0:
            print("s", end="")
        elif (x, y) in visited2:
            print("#", end="")
        else:
            print(".", end="")
    print()

# star 1
print(len(visited1))
# star 1
print(len(visited2))
