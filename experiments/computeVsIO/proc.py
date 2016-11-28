import psutil
import time

MYPID = 28161 # -- Change to a.out PID -- 

class Node(object):
    def __init__(self, n_left=None, n_right=None):
        self.left = n_left
        self.right = n_right

def get_proc_fd(fd):
    p = psutil.Process(fd)
    return p.open_files()

def create_bin_tree(node, level):
   if level <= 1:
        return node
   else:
        return create_bin_tree(Node(node, node), level-1)

def enum_tree(node):
    if node.left != None:
        enum_tree(node.left)
    if node.right != None:
        enum_tree(node.right)

# Python test script for getting all open FDs for a process
print("Python Proc Enum Test")

# Create Tree same size as FD enum
r_node = Node()
root = create_bin_tree(r_node, 16) # 2**16 = 65k

# Timer - enum Tree in Memory
print("Starting Tree Enum Timer")
tr_start_time = time.time()*1000
enum_tree(root)
tr_end_time = (time.time()*1000) - tr_start_time
print("Tree Enum took: %d" % tr_end_time)

# Timer - /PROC/PID/FD
print("Starting Proc Enum Timer")
start_time = time.time()*1000
proc_list = get_proc_fd(MYPID)
end_time = (time.time()*1000) - start_time
print("Proc Enum took: %d" % end_time)
#print("Proc Enum List: %s" % proc_list)

print("Test Done")

# How is the proc enum done?
# psutil.Process.open_files()
#->   popenfile = namedtuple('popenfile', ['path', 'fd'])

# Why *1000
#-> Get better millisecond precision
