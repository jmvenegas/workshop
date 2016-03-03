"""
    @author justin.venegas@gmail.com
    @file netParser.py
    @brief Module with 'static' conversion and parsing functions
"""

"""
    Imports
"""
# Python
import struct
import socket

# Local
from netConstants import *
from netException import *

"""
    Functions
"""
# Address Conversion (ipv4)


def address2integer(ip_address):
    return struct.unpack(NET_STRUCT_UNPACK_FMT, socket.inet_aton(ip_address))[0]


def integer2address(addr_integer):
    return socket.inet_ntoa(struct.pack(NET_STRUCT_UNPACK_FMT, addr_integer))


def increment_address(ip_address, val, byte=0):
    raise ("Unimplemented Exception: %s" % increment_address.__name__)
