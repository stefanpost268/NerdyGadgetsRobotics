
int adirectionPin = 12;
int apwmPin = 3;
int abrakePin = 9;

int bdirectionPin = 13;
int bpwmPin = 11;
int bbrakePin = 8;

float speedy = 0.50;
float speedx = 0.75;

void setup()
{
    // define pins
    pinMode(adirectionPin, OUTPUT);
    pinMode(apwmPin, OUTPUT);
    pinMode(abrakePin, OUTPUT);

    pinMode(bdirectionPin, OUTPUT);
    pinMode(bpwmPin, OUTPUT);
    pinMode(bbrakePin, OUTPUT);

    pinMode(A2, INPUT);
    pinMode(A3, INPUT);
}

void loop()
{
    int x = analogRead(A2);
    int y = analogRead(A3);
    x = map(x, 0, 1023, -255, 255);
    y = map(y, 0, 1023, -255, 255);

    switch (x >= 0) {
        case true:
            digitalWrite(bbrakePin, LOW);
            left(x);
            break;
        case false:
            digitalWrite(bbrakePin, LOW);
            right(x);
            break;
        default:
          digitalWrite(bbrakePin, HIGH);    
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
    analogWrite(apwmPin, x*speedx);
}

void right(int x) {
    digitalWrite(adirectionPin, LOW);
    analogWrite(apwmPin, abs(x)*speedx);
}

void up(int y) {
    digitalWrite(bdirectionPin, HIGH);
    analogWrite(bpwmPin, y*speedy);
}

void down(int y) {
    digitalWrite(bdirectionPin, LOW);
    analogWrite(bpwmPin, abs(y));
}