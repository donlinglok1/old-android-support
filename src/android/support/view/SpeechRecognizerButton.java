package android.support.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.n.It;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SpeechRecognizerButton extends RelativeLayout {
	private final Context context;
	private transient SpeechRecognizer speech;
	private transient Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	private transient boolean isListen = false;
	private transient String language = "en-US";
	private transient Button btnRecord;

	public Button getButton() {
		return btnRecord;
	}

	public static abstract interface SpeechRecognizerButtonCallback {
		public abstract void onResult(String result);
	}

	private transient SpeechRecognizerButtonCallback callback;

	public void setCallback(final SpeechRecognizerButtonCallback callback) {
		this.callback = callback;
	}

	public SpeechRecognizerButton(final Context context) {
		super(context);
		this.context = context;
		init();
	}

	public SpeechRecognizerButton(final Context context, final String language) {
		super(context);
		this.context = context;
		this.language = language;
		init();
	}

	@SuppressWarnings("deprecation")
	private final void init() {
		speech = SpeechRecognizer.createSpeechRecognizer(context);
		speech.setRecognitionListener(recognitionListener);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
		intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language);

		btnRecord = new Button(context);
		btnRecord.setText("Press here and speak");
		btnRecord.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		btnRecord.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View v, final MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					stop();
					break;
				case MotionEvent.ACTION_DOWN:
					start();
					v.performClick();
					break;
				}
				return false;
			}
		});
		addView(btnRecord);

	}

	public final void start() {
		if (!isListen) {
			speech.startListening(intent);
			isListen = true;
			final Vibrator myVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			myVibrator.vibrate(100);
		}
	}

	public final void stop() {
		if (isListen) {
			speech.stopListening();
			isListen = false;
		}
	}

	private final transient RecognitionListener recognitionListener = new RecognitionListener() {
		@Override
		public void onBeginningOfSpeech() {
			Log.i("debug", "onBeginningOfSpeech--");
		}

		@Override
		public void onBufferReceived(final byte[] buffer) {
			Log.i("debug", "onBufferReceived--");
		}

		@Override
		public void onEndOfSpeech() {
			Log.i("debug", "onEndOfSpeech--");
			stop();
		}

		/**
		 * switch (error) { case SpeechRecognizer.ERROR_NETWORK_TIMEOUT: break;
		 * case SpeechRecognizer.ERROR_NETWORK: break; case
		 * SpeechRecognizer.ERROR_AUDIO: break; case
		 * SpeechRecognizer.ERROR_SERVER: break; case
		 * SpeechRecognizer.ERROR_CLIENT: break; case
		 * SpeechRecognizer.ERROR_SPEECH_TIMEOUT: break; case
		 * SpeechRecognizer.ERROR_NO_MATCH: break; case
		 * SpeechRecognizer.ERROR_RECOGNIZER_BUSY: break; case
		 * SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: break; default:
		 * break; }
		 **/
		@Override
		public void onError(final int error) {
			Log.i("debug", "onError--" + error);
			stop();
		}

		@Override
		public void onEvent(final int eventType, final Bundle params) {
			Log.i("debug", "onEvent--" + eventType);
		}

		@Override
		public void onPartialResults(final Bundle partialResults) {
			Log.i("debug", "onPartialResults--");
		}

		@Override
		public void onReadyForSpeech(final Bundle params) {
			Log.i("debug", "onReadyForSpeech--");
		}

		@Override
		public void onResults(final Bundle bundle) {
			final ArrayList<String> lists = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			String result = lists.get(0);
			result = result.replace(" ", "").replace("湧", "涌").replace("啟", "啓").replace("砲", "炮").replace("舂磡", "舂坎")
					.replace("濠", "蠔").replace("白泥", "上下白泥").replace("錦繡花園", "錦繡加洲").replace("洛馬", "落馬")
					.replace("樂馬", "落馬").replace("加州花園", "錦繡加洲").replace("古洞", "古洞/落馬洲").replace("落馬洲", "古洞/落馬洲")
					.replace("山村", "山邨").replace("富村", "富邨").replace("安村", "安邨").replace("利村", "利邨");
			Log.e("", "" + result);
			if (!It.isNull(callback)) {
				callback.onResult(result);
			}
		}

		@Override
		public void onRmsChanged(final float rmsdB) {
			Log.i("debug", "rmsChange--" + rmsdB);
		}
	};
}
