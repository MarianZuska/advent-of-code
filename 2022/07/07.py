import sys
import bisect

lines = sys.stdin.read().strip().split("\n")

elems = dict()
elems["/"] = (None, [], 0)
final_sum = 0

sizes = []


def sum_sub_dirs(cur_dir):
    global final_sum
    prev_dir, sub_dirs, val = elems[cur_dir]
    for sub_dir in sub_dirs:
        val += sum_sub_dirs(sub_dir)
    elems[cur_dir] = (prev_dir, sub_dirs, val)

    if val <= 100000:
        final_sum += val

    sizes.append(val)
    return val


def add_elem(elem, prev_elem):
    if elem not in elems:
        prev_dir, next_dirs, val = elems[prev_elem]
        next_dirs.append(elem)
        elems[prev_elem] = (prev_dir, next_dirs, val)
        elems[elem] = (prev_elem, [], 0)


def change_dir(curr, goal):
    if goal == "..":
        curr = elems[curr][0]
    elif goal == "/":
        curr = "/"
    else:
        add_elem(curr+goal, curr)
        curr = curr+goal
    return curr


cur_dir = "/"
for line in lines:
    line = line.split()
    if line[0] == "$":
        if line[1] == "cd":
            cur_dir = change_dir(cur_dir, line[2])
    else:
        if line[0] != "dir":
            prev_dir, next_dirs, sz = elems[cur_dir]
            sz += int(line[0])
            elems[cur_dir] = (prev_dir, next_dirs, sz)

sum_sub_dirs("/")

space_left = 70000000 - elems["/"][2]
space_needed = 30000000 - space_left
sizes.sort()

# directories
print(elems)

# star 1
print(final_sum)

# star 2
print(sizes[bisect.bisect(sizes, space_needed)])
