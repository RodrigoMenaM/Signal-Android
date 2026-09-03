/*
 * Copyright 2026 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package org.thoughtcrime.securesms.video.videoconverter;

import static org.junit.Assert.assertEquals;

import android.media.AudioFormat;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public final class AudioTrackConverterTest {

  @Test
  public void createEncoderInputFormat_usesDecodedPcmFormat() {
    MediaFormat decoderOutputFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_RAW, 44_100, 2);
    decoderOutputFormat.setInteger(MediaFormat.KEY_PCM_ENCODING, AudioFormat.ENCODING_PCM_16BIT);

    MediaFormat encoderInputFormat = AudioTrackConverter.createEncoderInputFormat(decoderOutputFormat, 128_000);

    assertEquals(MediaFormat.MIMETYPE_AUDIO_AAC, encoderInputFormat.getString(MediaFormat.KEY_MIME));
    assertEquals(44_100, encoderInputFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE));
    assertEquals(2, encoderInputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT));
    assertEquals(128_000, encoderInputFormat.getInteger(MediaFormat.KEY_BIT_RATE));
    assertEquals(MediaCodecInfo.CodecProfileLevel.AACObjectLC,
                 encoderInputFormat.getInteger(MediaFormat.KEY_AAC_PROFILE));
    assertEquals(AudioFormat.ENCODING_PCM_16BIT,
                 encoderInputFormat.getInteger(MediaFormat.KEY_PCM_ENCODING));
  }
}
