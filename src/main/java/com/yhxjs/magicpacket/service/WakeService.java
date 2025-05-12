package com.yhxjs.magicpacket.service;

import java.io.IOException;

public interface WakeService {

    void send(String ip, Integer mask, String mac, Integer port) throws IOException;
}
