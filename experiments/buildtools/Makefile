# Experimental Makefile
#
#  justin.venegas@gmail.com 
#  brief - makefile for trying out different make mechanisms
#
#

# Secondary Expansion:
#  observations from playing with .SECONDEXPANSION target

#Need to have the '.SECONDEXPANSION' target before any prerequisites that use it
.SECONDEXPANSION:

FIRSTVAR = egg
SECONDVAR = chicken

# This is exaggerated, but the general rule is {r=2*v}
#	where 'r' is resolution target and 'v' is the escaped variable.
sdex: $(FIRSTVAR) $$$$$$$$(SECONDVAR)
	@echo "Now you're doing second expansion!"

$(FIRSTVAR):
	@echo "Egg came first"

$$$$(SECONDVAR):
	@echo "Chicken came first"
