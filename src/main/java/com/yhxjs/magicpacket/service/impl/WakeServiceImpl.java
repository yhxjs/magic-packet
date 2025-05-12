package com.yhxjs.magicpacket.service.impl;

import com.yhxjs.magicpacket.service.WakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;

@Slf4j
@Service
public class WakeServiceImpl implements WakeService {

    @Override
    public void send(String ip, Integer mask, String mac, Integer port) throws IOException {
        int[] ipBytes = parseIpAddress(ip);
        int[] subnetMask = calculateSubnetMask(mask);
        int[] networkAddress = calculateNetworkAddress(ipBytes, subnetMask);
        int[] broadcastAddress = calculateBroadcastAddress(networkAddress, mask);
        sendMagicPacket(formatIpAddress(broadcastAddress), mac, port);
    }

    private void sendMagicPacket(String ipAddress, String macAddress, int port) throws IOException {
        byte[] macBytes = getMacBytes(macAddress);
        byte[] magicPacket = new byte[6 + 16 * macBytes.length];
        for (int i = 0; i < 6; i++) {
            magicPacket[i] = (byte) 0xFF;
        }
        for (int i = 6; i < magicPacket.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, magicPacket, i, macBytes.length);
        }
        InetAddress address = InetAddress.getByName(ipAddress);
        DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, address, port);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setOption(StandardSocketOptions.IP_TOS, 0x40);
            socket.send(packet);
            log.info("已发送幻包到{} via {}:{}", macAddress, ipAddress, port);
        }
    }

    private byte[] getMacBytes(String macAddress) throws IllegalArgumentException {
        String cleanedMac = macAddress.replace("-", "");
        byte[] bytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            String hex = cleanedMac.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(hex, 16);
        }
        return bytes;
    }

    private int[] parseIpAddress(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        int[] bytes = new int[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = Integer.parseInt(parts[i]);
        }
        return bytes;
    }

    private int[] calculateSubnetMask(int prefixLength) {
        int[] mask = new int[4];
        for (int i = 0; i < 4; i++) {
            if (prefixLength >= 8) {
                mask[i] = 255;
                prefixLength -= 8;
            } else if (prefixLength > 0) {
                mask[i] = 256 - (1 << (8 - prefixLength));
                prefixLength = 0;
            } else {
                mask[i] = 0;
            }
        }
        return mask;
    }

    private int[] calculateNetworkAddress(int[] ipBytes, int[] subnetMask) {
        int[] networkAddress = new int[4];
        for (int i = 0; i < 4; i++) {
            networkAddress[i] = ipBytes[i] & subnetMask[i];
        }
        return networkAddress;
    }

    private int[] calculateBroadcastAddress(int[] networkAddress, int prefixLength) {
        int[] broadcastAddress = new int[4];
        int remainingBits = 32 - prefixLength;
        System.arraycopy(networkAddress, 0, broadcastAddress, 0, 4);
        for (int i = 3; i >= 0 && remainingBits > 0; i--) {
            int bitsToSet = Math.min(8, remainingBits);
            broadcastAddress[i] |= (1 << bitsToSet) - 1;
            remainingBits -= bitsToSet;
        }
        return broadcastAddress;
    }

    private String formatIpAddress(int[] ipBytes) {
        return ipBytes[0] + "." + ipBytes[1] + "." + ipBytes[2] + "." + ipBytes[3];
    }
}
