#!/usr/bin/env python
# Staffing Generator for Staff Selection Algorithm

# Imports

import random

# Constants

STAFF_FILE = "staff.txt"
STAFF_DESC = ["STALKING","ANGRY","LAZY","HAUNTING","SPOOKY","SLEEPY","PLAYFUL","TRICKSY","RAGING","SNEAKY"]

STAFF_ANIM = ["HYRAX","DRAGON","TAPIR","SNAKE","HAWK","OCELOT","SHARK","OTTER","WOLF","FINCH"]

STAFF_RANK = ["D","C","B","A","S"]

STAFF_DEPT = ["COMBAT","INTEL","RESEARCH","MEDICAL","SUPPORT","DEVELOPMENT"]

# Functions

def generate_staff(staff_num):
	
	staff_list = []

	for i in range(staff_num):
	
		desc = random.choice(STAFF_DESC)
		anim = random.choice(STAFF_ANIM)
		rank = random.choice(STAFF_RANK)
		dept = random.choice(STAFF_DEPT)

		st_name = "%s %s" % (desc,anim)

		new_staff = Staff(st_name, rank, dept)

		staff_list.append(new_staff)
	
	return staff_list

def write_out_staff(staff):
	fh = open(STAFF_FILE,'w',buffering=0)
	for i in range(len(staff)):
		fh.write("%s\n" % (staff[i]))
	fh.close()

def main():
	staff = generate_staff(100)
	write_out_staff(staff)
	print("Staff Generated")

# Classes

class Staff(object):
	def __init__(self, name, rank, dept):
		self.name = name
		self.rank = rank
		self.dept = dept

	def __str__(self):
		str = "%s:%s:%s" % (self.name, self.rank, self.dept)
		return str

	def __eq__(self, staff):
		si = STAFF_RANK.index(self.rank)
		ci = STAFF_RANK.index(staff.rank)
		return (si == ci)

	def __lt__(self, staff):
		si = STAFF_RANK.index(self.rank)
		ci = STAFF_RANK.index(staff.rank)
		return (si < ci)
if __name__ == "__main__":
	main()
