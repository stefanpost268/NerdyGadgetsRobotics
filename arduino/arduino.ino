
int adirectionPin = 12;
int apwmPin = 3;
int abrakePin = 9;

int bdirectionPin = 13;
int bpwmPin = 11;
int bbrakePin = 8;

void setup()
{
    // define pins
    pinMode(adirectionPin, OUTPUT);
    pinMode(apwmPin, OUTPUT);
    pinMode(abrakePin, OUTPUT);

    pinMode(bdirectionPin, OUTPUT);
    pinMode(bpwmPin, OUTPUT);
    pinMode(bbrakePin, OUTPUT);

    pinMode(A4, INPUT);
    pinMode(A5, INPUT);
}

void loop()
{
    int x = analogRead(A4);
    int y = analogRead(A5);
    x = map(x, 0, 1023, -255, 255);
    y = map(y, 0, 1023, -255, 255);

    switch (x >= 0) {
        case true:
            left(x);
            break;
        case false:
            right(x);
            break;
    }

    switch (y >= 0) {
        case true:
          digitalWrite(bbrakePin, LOW);
          up(y);
          break;
        case false:
          digitalWrite(bbrakePin, LOW);
          down(y);
          break;
        default:
          digitalWrite(bbrakePin, HIGH);
    }
}

void left(int x) {
    digitalWrite(adirectionPin, HIGH);
    analogWrite(apwmPin, x);
}

void right(int x) {
    digitalWrite(adirectionPin, LOW);
    analogWrite(apwmPin, abs(x));
}

void up(int y) {
    digitalWrite(bdirectionPin, HIGH);
    analogWrite(bpwmPin, y);
}

void down(int y) {
    digitalWrite(bdirectionPin, LOW);
    analogWrite(bpwmPin, abs(y));
}
