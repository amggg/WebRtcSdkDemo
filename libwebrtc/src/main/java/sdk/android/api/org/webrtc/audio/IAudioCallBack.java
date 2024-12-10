package org.webrtc.audio;

import java.nio.ByteBuffer;

public interface IAudioCallBack {

    void onAudioData(ByteBuffer byteBuffer);
}
