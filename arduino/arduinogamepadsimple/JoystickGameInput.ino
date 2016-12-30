/**
 * @file - JoystickGameInput.ino
 * @author - justin.venegas@gmail.com
 * Read Joystick X,Y axes & Trigger, Thumb buttons [x,y,trigger,thumb]
 * Send data over wire
 * Example data - 186:205:1:0
 */
int scaler = 4;

void setup() {
  Serial.begin(9600);
  pinMode(3, INPUT_PULLUP);
  pinMode(4, INPUT_PULLUP);
}

void loop() {
  getValues();
  delay(50);
}

void getValues() {
  byte xAxisAnalog = (analogRead(A0)/scaler);
  byte yAxisAnalog = (analogRead(A1)/scaler);

  byte button1Analog = !digitalRead(3);
  byte button2Analog = !digitalRead(4);

  Serial.print(xAxisAnalog);
  Serial.print(":");
  Serial.print(yAxisAnalog);
  Serial.print(":");
  Serial.print(button1Analog);
  Serial.print(":");
  Serial.print(button2Analog);
  Serial.println(); 
}

