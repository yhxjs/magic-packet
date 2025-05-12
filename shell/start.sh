#!/bin/bash
echo starting
nohup java --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED -jar magic-packet-0.0.1.jar > magic-packet.log 2>&1
