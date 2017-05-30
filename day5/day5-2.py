#!/usr/bin/env python
# -*- coding:utf-8 -*-

import hashlib
import itertools

def output(password):
    print(''.join(['_' if c is None else c for c in password]))

def main():
    """入口"""
    door_id = 'uqwqemis'
    password_length = 8
    password = [None] * password_length

    for i in itertools.count(0):
        hexdigest = hashlib.md5(door_id + str(i)).hexdigest()

        if hexdigest.startswith('00000'):
            pos, c = hexdigest[5:7]

            if not pos.isdigit():
                continue

            position = int(pos)
            if position < 8 and password[position] is None:
                password[position] = c
                output(password)

            if None not in password:
                print('finish')
                break

if __name__ == "__main__":
    main()
