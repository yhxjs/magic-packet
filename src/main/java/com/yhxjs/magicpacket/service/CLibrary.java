package com.yhxjs.magicpacket.service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public interface CLibrary extends Library {

    CLibrary INSTANCE = Native.load(Platform.isWindows() ? "ws2_32" : "c", CLibrary.class);

    //Linux
    int setsockopt(int sockfd, int level, int optname, byte[] optval, int optlen);

    //Windows
    int setsockopt(int sockfd, int level, int optname, int[] optval, int optlen);
}
