import sys

lines = sys.stdin.read().split("\n")

stacks = [[] for i in range(9)]

first = 0

for line in lines:
    first += 1
    if line[1] != '1':
        elems = line[1::4]
        print(elems)
        for i in range(len(elems)):
            if elems[i] != ' ':
                stacks[i].append(elems[i])
    else:
        break

stacks = list(map((lambda l: l[::-1]), stacks))
print(stacks)

for line in lines[first+1:-1]:
    line = line.split()
    print(line)
    amount, s1, s2 = int(line[1]), int(line[3]), int(line[5])

    move = stacks[s1-1][-amount:]#[::-1] #(add for star 1)
    stacks[s1 - 1] = stacks[s1 - 1][:-amount]
    stacks[s2 - 1] = stacks[s2 - 1] + move
    print(amount, s1, s2)
    print(stacks)

for stack in stacks:
    print(stack[-1], end="")
print()

