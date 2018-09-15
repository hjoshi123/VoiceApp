package edu.msrit.csrlearn;

import android.view.KeyEvent;

public class KeyFinder {

    static public String getKeyPressed(int keycode) {
        switch (keycode) {
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
                return "enter";
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_NUMPAD_0:
            case KeyEvent.KEYCODE_INSERT:
                return "0";
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_NUMPAD_1:
            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:
            case KeyEvent.KEYCODE_MOVE_END:
                return "1";
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_NUMPAD_2:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return "2";
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_NUMPAD_3:
            case KeyEvent.KEYCODE_DPAD_DOWN_RIGHT:
            case KeyEvent.KEYCODE_PAGE_DOWN:
                return "3";
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_NUMPAD_4:
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return "4";
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_NUMPAD_5:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return "5";
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_NUMPAD_6:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return "6";
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_NUMPAD_7:
            case KeyEvent.KEYCODE_DPAD_UP_LEFT:
            case KeyEvent.KEYCODE_MOVE_HOME:
                return "7";
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_NUMPAD_8:
            case KeyEvent.KEYCODE_DPAD_UP:
                return "8";
            case KeyEvent.KEYCODE_9:
            case KeyEvent.KEYCODE_NUMPAD_9:
            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:
            case KeyEvent.KEYCODE_PAGE_UP:
                return "9";
            default:
                return "*";
        }
    }
}
