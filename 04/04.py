import sys

lines = sys.stdin.read().strip().split("\n")

score1 = 0
score2 = 0
for line in lines:
    l1, r1, l2, r2 = line.replace(",", "-").split("-")

    if (l1 <= l2 and r1 >= r2) or (l2 <= l1 and r2 >= r1):
        score1 += 1

    if (r2 >= l1 >= l2) or (r2 >= r1 >= l2) or (r1 >= l2 >= l1) or (r1 >= r2 >= l1):
        score2 += 1

print(score1)
print(score2)

