#!/usr/bin/env python
# Staff Selection Algorithm
#		based upon the MGSV auto-staffing function
#
# brief - Each staff member has a ranking and a department.
# 		  Each department should maintain the best combination of
#		  staff at any given time.

# Imports

import staffGenerator

# Constants

BASE_STAFF_LIMIT = 15

# Functions

def main():
	
	base = BaseStaff()
	staff = staffGenerator.generate_staff(100)
	base.add_staff(staff)
	print(base.long_str())
	
def algorithm(staff, staff_list, base):
	
	if base.at_capacity(staff_list):
		ex_staff_mem = staff_list[0]
			
		# Equally matched staff
		if ex_staff_mem == staff:
			base.dismiss(staff)

		# Newcomer is better
		elif ex_staff_mem < staff:
			base.dismiss(ex_staff_mem)
			staff_list[0] = staff
			staff_list.sort()

		# 'Worst' existing staff is better
		else:
			base.dismiss(staff)
	else:
		staff_list.append(staff)
		staff_list.sort()

# Classes

class BaseStaff(object):
	def __init__(self):
		self.staff_com = []
		self.staff_int = []
		self.staff_res = []
		self.staff_dev = []
		self.staff_sup = []
		self.staff_med = []
		self.dismissed = []
		
	def __str__(self):
		all = self.get_total_staff()
		str = "BASE STAFF//:%d\n" % (all)
		str += "COMBAT:%d\n" % (len(self.staff_com))
		str += "INTEL:%d\n" % (len(self.staff_int))
		str += "RESEARCH:%d\n" % (len(self.staff_res))
		str += "DEVELOPMENT:%d\n" % (len(self.staff_dev))
		str += "SUPPORT:%d\n" % (len(self.staff_sup))
		str += "MEDICAL:%d\n" % (len(self.staff_med))
		str += "DISMISSED:%d\n" % (len(self.dismissed))
		return str

	def at_capacity(self, staff_list):
		return (len(staff_list) == BASE_STAFF_LIMIT)

	def dismiss(self, staff):
		self.dismissed.append(staff)
	
	def long_str(self):
		stre = "BASE STAFF/DETAILS//\n"
		stre += "**COMBAT:\n%s" % ("\n".join(map(str, self.staff_com)))
		stre += "\n\n"
		stre += "**INTEL:\n%s" % ("\n".join(map(str, self.staff_int)))
		stre += "\n\n"
		stre += "**RESEARCH:\n%s" % ("\n".join(map(str, self.staff_res)))
		stre += "\n\n"
		stre += "**DEVELOPMENT\n%s" % ("\n".join(map(str, self.staff_dev)))
		stre += "\n\n"
		stre += "**SUPPORT\n%s" % ("\n".join(map(str, self.staff_sup)))
		stre += "\n\n"
		stre += "**MEDICAL\n%s" % ("\n".join(map(str, self.staff_med)))
		stre += "\n\n"
		stre += "**DISMISSED\n%s" % ("\n".join(map(str, self.dismissed)))
		stre += "\n"

		return stre

	def get_total_staff(self):
		all_staff = len(self.staff_com+self.staff_int+self.staff_res+self.staff_dev+self.staff_sup+self.staff_med)
		return all_staff

	def add_staff(self, staff_list):

		for staff in staff_list:
				
			if staff.dept == staffGenerator.STAFF_DEPT[0]:
				# COMBAT
				algorithm(staff, self.staff_com, self)
			if staff.dept == staffGenerator.STAFF_DEPT[1]:
				# INTEL
				algorithm(staff, self.staff_int, self)
			if staff.dept == staffGenerator.STAFF_DEPT[2]:
				# RESEARCH
				algorithm(staff, self.staff_res, self)
			if staff.dept == staffGenerator.STAFF_DEPT[3]:
				# MEDICAL
				algorithm(staff, self.staff_med, self)
			if staff.dept == staffGenerator.STAFF_DEPT[4]:
				# SUPPORT
				algorithm(staff, self.staff_sup, self)
			if staff.dept == staffGenerator.STAFF_DEPT[5]:
				# DEVELOPMENT
				algorithm(staff, self.staff_dev, self)
	
if __name__ == "__main__":
	main()

