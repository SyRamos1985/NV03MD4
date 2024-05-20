

package com.example.nv03md4;

import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;

import android.media.AudioManager;
import android.media.AudioFocusRequest;

import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;



public class MainActivity extends AppCompatActivity {


    private AudioManager audioManager;
    private AudioHelper audioHelper;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioHelper = new AudioHelper(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        textToSpeech = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.getDefault());
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
                                                    /**
                                                     * Called when the endpointer is ready for the user to start speaking.
                                                     *
                                                     * @param params parameters set by the recognition service. Reserved for future use.
                                                     */
                                                    @Override
                                                    public void onReadyForSpeech(Bundle params) {

                                                    }

                                                    /**
                                                     * The user has started to speak.
                                                     */
                                                    @Override
                                                    public void onBeginningOfSpeech() {

                                                    }

                                                    /**
                                                     * The sound level in the audio stream has changed. There is no guarantee that this method will
                                                     * be called.
                                                     *
                                                     * @param rmsdB the new RMS dB value
                                                     */
                                                    @Override
                                                    public void onRmsChanged(float rmsdB) {

                                                    }

                                                    /**
                                                     * More sound has been received. The purpose of this function is to allow giving feedback to the
                                                     * user regarding the captured audio. There is no guarantee that this method will be called.
                                                     *
                                                     * @param buffer a buffer containing a sequence of big-endian 16-bit integers representing a
                                                     *               single channel audio stream. The sample rate is implementation dependent.
                                                     */
                                                    @Override
                                                    public void onBufferReceived(byte[] buffer) {

                                                    }

                                                    /**
                                                     * Called after the user stops speaking.
                                                     */
                                                    @Override
                                                    public void onEndOfSpeech() {

                                                    }

                                                    /**
                                                     * A network or recognition error occurred.
                                                     *
                                                     * @param error code is defined in {@link SpeechRecognizer}. Implementations need to handle any
                                                     *              integer error constant to be passed here beyond constants prefixed with ERROR_.
                                                     */
                                                    @Override
                                                    public void onError(int error) {

                                                    }

                                                    /**
                                                     * Called when recognition results are ready.
                                                     *
                                                     * <p>
                                                     * Called with the results for the full speech since {@link #onReadyForSpeech(Bundle)}.
                                                     * To get recognition results in segments rather than for the full session see
                                                     * {@link RecognizerIntent#EXTRA_SEGMENTED_SESSION}.
                                                     * </p>
                                                     *
                                                     * @param results the recognition results. To retrieve the results in {@code
                                                     *                ArrayList<String>} format use {@link Bundle#getStringArrayList(String)} with
                                                     *                {@link SpeechRecognizer#RESULTS_RECOGNITION} as a parameter. A float array of
                                                     *                confidence values might also be given in {@link SpeechRecognizer#CONFIDENCE_SCORES}.
                                                     */
                                                    @Override
                                                    public void onResults(Bundle results) {

                                                    }

                                                    /**
                                                     * Called when partial recognition results are available. The callback might be called at any
                                                     * time between {@link #onBeginningOfSpeech()} and {@link #onResults(Bundle)} when partial
                                                     * results are ready. This method may be called zero, one or multiple times for each call to
                                                     * {@link SpeechRecognizer#startListening(Intent)}, depending on the speech recognition
                                                     * service implementation.  To request partial results, use
                                                     * {@link RecognizerIntent#EXTRA_PARTIAL_RESULTS}
                                                     *
                                                     * @param partialResults the returned results. To retrieve the results in
                                                     *                       ArrayList&lt;String&gt; format use {@link Bundle#getStringArrayList(String)} with
                                                     *                       {@link SpeechRecognizer#RESULTS_RECOGNITION} as a parameter
                                                     */
                                                    @Override
                                                    public void onPartialResults(Bundle partialResults) {

                                                    }

                                                    /**
                                                     * Reserved for adding future events.
                                                     *
                                                     * @param eventType the type of the occurred event
                                                     * @param params    a Bundle containing the passed parameters
                                                     */
                                                    @Override
                                                    public void onEvent(int eventType, Bundle params) {

                                                    }
                                                }
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        {
            @Override
            public void onRmsChanged(float rmsdB) {
        }


            @Override
            public void onReadyForSpeech(Bundle params) {
        }

            @Override
            public void onResults(Bundle results) {
            List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                processRecognizedCommand(matches.get(0));
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int error) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });

            requestAudioFocus();
            startSpeechRecognition();
        }

        private void requestAudioFocus() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setOnAudioFocusChangeListener(new AudioManager.OnAudioFocusChangeListener() {
                            @Override
                            public void onAudioFocusChange(int focusChange) {

                            }
                        })
                        .build();
                audioManager.requestAudioFocus(audioFocusRequest);
            } else {

                audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            }
        }

        private void startSpeechRecognition() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            speechRecognizer.startListening(intent);
        }

        private void processRecognizedCommand(String command) {
            if (command != null && !command.isEmpty()) {
                textToSpeech.speak("Comando reconhecido: " + command, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                textToSpeech.speak("Nenhum comando reconhecido.", TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (textToSpeech != null) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
            if (speechRecognizer != null) {
                speechRecognizer.destroy();
            }
        }
    }