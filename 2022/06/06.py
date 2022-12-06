import sys

lines = sys.stdin.read().strip()

for i in range(len(lines[4:])):
    if len(set(lines[i:i+4])) == 4:
        print(i+4)
        break

for i in range(len(lines[14:])):
    if len(set(lines[i:i+14])) == 14:
        print(i+14)
        break
