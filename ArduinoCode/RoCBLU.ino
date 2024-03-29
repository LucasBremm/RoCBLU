                                                                                                                                                                                                                                                                                                                     #include <SoftwareSerial.h>
 
SoftwareSerial serial(5, 6);

const int AIA = 3;
const int AIB = 9;
const int BIA = 10;
const int BIB = 11;
int speedA;
int speedB;
 
String data = "";
 
void setup() {
  serial.begin(9600);
  Serial.begin(9600);
  pinMode(AIA, OUTPUT);
  pinMode(AIB, OUTPUT);
  pinMode(BIA, OUTPUT);
  pinMode(BIB, OUTPUT);
  delay(1000);
  Serial.println("Iniciado");
}
 
void loop() {

  char entry;
  naoMove();
  speedA = 200;//direita
  speedB = 150;//esquerda
  while(serial.available() > 0) {
    entry  = char(serial.read()); 
    data += entry;
    if(entry == 'F'){
      naoMove();
      delay(500);
      Serial.println("mover Frente");
      moverFrente();
      delay(500);
    }

    else if(entry == 'T'){
      naoMove();
      delay(500);
      Serial.println("mover Tras");
      moverRe();
      delay(500);
    }

    else if(entry == 'E'){
      naoMove();
      delay(500);
      Serial.println("mover Esquerda");
      moverEsquerda();
      delay(250);
    }

    else if(entry == 'D'){
      naoMove();
      delay(500);
      Serial.println("mover Direita");
      moverDireita();
      delay(250);
    }
  }
  
  naoMove();
  Serial.println(data);
 
  data = "";
  
  delay(1000);
}

void moverFrente() {
  analogWrite(AIA, 0);
  analogWrite(AIB, speedA);
  analogWrite(BIA, 0);
  analogWrite(BIB, speedB);  

}
void moverRe() {
  analogWrite(AIA, speedA);
  analogWrite(AIB, 0);
  analogWrite(BIA, speedB);
  analogWrite(BIB, 0);  
}
void moverDireita() {
  analogWrite(AIA, speedA);
  analogWrite(AIB, 0);
  analogWrite(BIA, 0);
  analogWrite(BIB, speedB);

}
void moverEsquerda() {

  analogWrite(AIA, 0);
  analogWrite(AIB, speedA);
  analogWrite(BIA, speedB);
  analogWrite(BIB, 0);
}
void naoMove() {
  analogWrite(AIA, 0);
  analogWrite(AIB, 0);
  analogWrite(BIA, 0);
  analogWrite(BIB, 0);
}
