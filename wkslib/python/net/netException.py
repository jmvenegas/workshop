"""
    @author justin.venegas@gmail.com
    @file netException.py
    @brief Exception class for net modules
"""

"""
    Classes
"""


class AddressException(Exception):
    def __init__(self, exception_arg):
        Exception.__init__(self, "Address Exception raised: %s" % exception_arg)
        self.exception_arg = exception_arg
