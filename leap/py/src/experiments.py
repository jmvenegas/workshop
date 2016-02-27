"""
    @author justin.venegas@gmail.com
    @file experiments.py
    @brief prototyping space for Leap Motion controller
"""
import Leap

class ActionListener(Leap.Listener):

    def on_connect(self, controller):
        print("Connected")
        controller.enable_gesture(Leap.Gesture.TYPE_SWIPE)

    def on_frame(self, controller):
        frame = controller.frame()
        # print "Frame id: %d, timestamp: %d, hands: %d, fingers: %d, tools: %d, gestures: %d" % (
        #   frame.id,
        #   frame.timestamp,
        #   len(frame.hands),
        #   len(frame.fingers),
        #   len(frame.tools),
        #   len(frame.gestures()))

        for gesture in frame.gestures():
            if gesture.type == Leap.Gesture.TYPE_SWIPE:
                print("That's a swipe!")
                return

def main():
    print("##### Python Leap Experiments #####")
    listener = ActionListener()
    controller = Leap.Controller()
    controller.add_listener(listener)

    raw_input("waiting...\n")
    controller.remove_listener(listener)

if __name__ == "__main__":
    main()