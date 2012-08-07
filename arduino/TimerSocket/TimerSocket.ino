#define RELAY 7
#define RELAY_OFF HIGH
#define RELAY_ON LOW

unsigned long interval = 0, startTimestamp = 0;
int nextStatus = RELAY_ON;

void setup() {
  pinMode(RELAY, OUTPUT);
  digitalWrite(RELAY, RELAY_OFF);
  Serial.begin(9600);
}

void loop() {
    if (Serial.available()) {
        char buffer[10], data;
        int i = 0;
        data = Serial.read();
        while (data != '\n') {
            buffer[i++] = data;
            while (!Serial.available()) {}
            data = Serial.read();
        }
        if (buffer[0] == 'S') {
            Serial.print(!digitalRead(RELAY));
        }
        else {
            for (i = 0; i < 10; i++) {
                if (buffer[i] == ' ') {
                    break;
                }
            }
            buffer[i] = 0;
            interval = atoi(buffer);
            startTimestamp = millis() / 1000;
            if (buffer[i + 1] == '0') {
                nextStatus = RELAY_OFF;
            }
            else {
                nextStatus = RELAY_ON;
            }
        }
    }
    if ((millis() / 1000) - startTimestamp >= interval) {
        digitalWrite(RELAY, nextStatus);
    }
}
