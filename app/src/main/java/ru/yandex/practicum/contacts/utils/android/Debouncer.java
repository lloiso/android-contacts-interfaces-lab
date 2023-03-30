package ru.yandex.practicum.contacts.utils.android;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import ru.yandex.practicum.contacts.presentation.main.MainViewModel;

import androidx.annotation.NonNull;

public class Debouncer implements OnDebounceListener {


    private static final int MESSAGE_ID = 1;
    private static final int DELAY = 500;
    private final OnDebounceListener listener;

//    private final MainViewModel viewModel;

    public Debouncer(OnDebounceListener listener) {
        this.listener = listener;
//        final Debouncer debouncer = new Debouncer(this);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            if (message.what == MESSAGE_ID) {
                doOnDebounce();
                return;
            }
            super.handleMessage(message);
        }
    };

    // метод отправляет сообщение, для обновления данных с задержкой в 500мс
    public void updateValue(String value) {
        final Message message = Message.obtain(handler, MESSAGE_ID, value);
        handler.removeMessages(MESSAGE_ID);
        handler.sendMessageDelayed(message, DELAY);
    }

    // выполнить действие по прошествии 500мс, если нового события в течении 500мс не было отправлено
    private void doOnDebounce() {
        listener.onDebounce();
    }
    @Override
    public void onDebounce() {

    }
}
