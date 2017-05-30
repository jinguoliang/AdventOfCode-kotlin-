#!/usr/bin/python
#coding=utf-8

# 读取数据
# 遍历
# x,y d


import numpy as np


f = open('day1.data', 'r')
line = f.readline()
steps = line.split(', ')

position = np.array((0, 0))
direction = np.array((0, 1))


def _turn(direction, is_right):
    print(direction)
    print(is_right)
    x, y = direction
    direction = np.array((y, -x))
    direction *= (-1 if is_right else 1)
    print(direction)
    return direction

positions = [position]
firstTwice = []

def _findFirstTwice(pos):

    global positions
    for p in positions:
        if (p == pos).all():
            firstTwice.append(pos)
            return
    positions.append(pos)

def _step(pos, direction, step):
    if len(firstTwice) == 0:
        for i in range(1, step + 1):
            _findFirstTwice(pos + direction * i)

    return pos + direction * step

for step in steps:
    is_right = step[0] == 'R'
    n = int(step[1:])

    direction = _turn(direction, is_right)
    position = _step(position, direction, n)

print(firstTwice)
print(abs(firstTwice[0][0]) +abs(firstTwice[0][1]))
print(position)
print(abs(position[0]) +abs(position[1]))
