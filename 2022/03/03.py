import string

score = 0
while True:
    s1 = set(input())
    s2 = set(input())
    s3 = set(input())

    for c in s1 & s2 & s3:
        score += string.ascii_letters.index(c) + 1

    print(score)
