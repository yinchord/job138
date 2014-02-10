package com.geetion.job138.util;

import java.io.FileDescriptor;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundUtil extends FileUtil {
	/**
	 * 
	 * @author 80work
	 * @version 0.1
	 */
	public static final int RECORD_DATA = 0;
	public static MediaPlayer mediaPlayer;

	/**
	 * 播放音频
	 * 
	 * @param uri
	 *            音频文件URI
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */

	public static void playSound(Uri uri, Activity context) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying())
				return;
			mediaPlayer.release();
		}
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(context, uri);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	public static void stopSound() {
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}

	/**
	 * 播放音频
	 * 
	 * @param fileDescriptor
	 *            音频文件
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public static void playSound(FileDescriptor fileDescriptor, int offset, int length) throws IllegalArgumentException, IllegalStateException, IOException {
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying())
				return;
			mediaPlayer.release();
		}
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(fileDescriptor, offset, length);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	/**
	 * 调出录音机
	 * 
	 * @param activity
	 *            当前的活动activity
	 * @inheritDoc 该方法返回的回调 resultCode 为 FileUitl.RECORD_DATA
	 */
	public static void record(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/*");
		activity.startActivityForResult(intent, RECORD_DATA);
	}
}
