score = 0
while True:
    x, y = input().split()
    x = ["A", "B", "C"].index(x)
    y = ["X", "Y", "Z"].index(y)
    score += y*3
    used = (x + (y-1)) % 3
    score += used+1
    print(score)

