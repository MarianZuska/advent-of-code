import sys

lines = sys.stdin.read().strip().split("\n")


def check_line(is_horizontal, line, direction):
    max_height = -1
    n = len(lines[0]) if is_horizontal else len(lines)

    for i in range(0, n):
        if direction < 0:
            i = n - i - 1
        indices = (line, i) if is_horizontal else (i, line)

        tree = int(lines[indices[0]][indices[1]])
        if tree > max_height:
            visible[indices[0]][indices[1]] = True
            max_height = tree


def check_tree(i, j):
    tree = lines[j][i]
    score = 1

    cur_score = 0
    start = i-1
    while start >= 0:
        cur_score += 1
        if lines[j][start] >= tree:
            break
        start -= 1
    score *= cur_score

    cur_score = 0
    start = i+1
    while start < len(lines[0]):
        cur_score += 1
        if lines[j][start] >= tree:
            break
        start += 1
    score *= cur_score

    cur_score = 0
    start = j-1
    while start >= 0:
        cur_score += 1
        if lines[start][i] >= tree:
            break
        start -= 1
    score *= cur_score

    cur_score = 0
    start = j+1
    while start < len(lines):
        cur_score += 1
        if lines[start][i] >= tree:
            break
        start += 1
    score *= cur_score

    return score



visible = [[False for i in range(len(lines[0]))] for j in range(len(lines))]

# star 1
for j in range(len(lines)):
    check_line(False, j, 1)
    check_line(False, j, -1)


for i in range(len(lines[0])):
    check_line(True, i, 1)
    check_line(True, i, -1)

# visualize
# for v in visible:
#     print(*v)

print(sum([sum(l) for l in visible]))

# star 2

# visualize
# for j in range(len(lines)):
#     for i in range(len(lines[0])):
#         print(check_tree(i, j), end=" ")
#     print()

max_score = 0
for j in range(len(lines)):
    for i in range(len(lines[0])):
        max_score = max(max_score, check_tree(i, j))
print(max_score)