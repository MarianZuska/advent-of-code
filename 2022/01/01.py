import sys

ma = 0
x = input()

elems = [[]]
for line in sys.stdin:
    line = line[:-1]
    if line == "EOF":
        elems.append([])
        break
    if line == "":
        elems.append([])
    else:
        elems[-1].append(int(line))

elems = sorted([sum(lis) for lis in elems], reverse=True)
print(elems[0])
print(sum(elems[:3]))
