"""
@brief - Palindrome, String, and Tree Practice
"""
#******************************************************************************
class Node(object):
	def __init__(self, root, left=None, right=None):
		self.root = root # this is an integer value
		self.left = left # these are node object refs
		self.right = right

def serialize(node):
	# preorder recursive serialize a binary tree
	if node is None:
		return -1
	else:
		return node.root, serialize(node.left), serialize(node.right)
#******************************************************************************

def palindrome_stack(palindrome):
    str_stack = []
    for i in palindrome:
        str_stack.append(i)
    for i in palindrome:
        if i != str_stack.pop():
            return False
    return True
    
def palindrome_string(palindrome):
    for i in range(len(palindrome)/2):
        if palindrome[i] != palindrome[len(palindrome)-(i+1)]:
            return False
    return True
#******************************************************************************
def is_ordered(word, order):
	word_map = {}
	for i in range(len(word)):
		kv = {word[i]:i}
		word_map.update(kv)
	for i in range(1,len(order)):
		if ( (word_map[order[i]] - word_map[order[i-1]]) < 0):
			return False
	return True
#******************************************************************************
# Testing
def main():
	# Serialize Testing
	bt = Node(10,
		Node(4,
			Node(1),
			Node(2)),
		Node(0,
			Node(8),
			Node(6)))

	print(serialize(bt))
	# truth sequence should be: [10,4,1,-1,-1,2,-1,-1,0,8,-1,-1,6,-1,-1]

	# is_ordered Testing
	word = "hello world"
	order = "old"

	print(is_ordered(word, order))
	# truth print is: true

	print("palindrome stack(list)")
    print(palindrome_stack("autotub"))
    print(palindrome_stack("catscats"))
    print(palindrome_stack("abcddcba"))
    print(palindrome_stack("abcba"))

    print("palindrome string(string)")
    print(palindrome_string("autotub"))
    print(palindrome_string("catscats"))
    print(palindrome_string("abcddcba"))
    print(palindrome_string("abcba"))

if __name__ == "__main__":
	main()