import re
from sys import stdin
searches = ["one", "two", "three", "four", "five", "six", "seven", "eight","nine"]
lines = stdin.readlines()

sum = 0
for line in lines:
    for i, f in enumerate(searches):
        line = line.replace(f, f"{f[0]}|{str(i+1)}|{f[-1]}")
    
    digits = "".join(filter(lambda c: c.isdigit(), line))
    sum += int(digits[0]+digits[-1])
print(sum)
