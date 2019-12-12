package com.example.searchable.autocompletetextview.custom_view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.example.searchable.autocompletetextview.R;

/**
 * Created by Ravindu Fernando on 2019-12-12 at 05:28 PM
 * Ref - https://www.drzon.net/posts/how-to-create-a-clearable-autocomplete-dropdown-with-autocompletetextview/
 */
public class ClearableAutoCompleteTextView extends AutoCompleteTextView {

    // was the text just cleared?
    boolean justCleared = false;

    // The image we defined for the clear button
    private Drawable imgClearButton = getResources().getDrawable(
            R.drawable.ic_clear);

    private Drawable imgSearchIcon = getResources().getDrawable(
            R.drawable.ic_search);


    // if not set otherwise, the default clear listener clears the text in the
    // text view
    private OnClearListener defaultClearListener = new OnClearListener() {

        @Override
        public void onClear() {
            ClearableAutoCompleteTextView et = ClearableAutoCompleteTextView.this;
            et.setText("");
            hideClearButton();
        }
    };

    private OnClearListener onClearListener = defaultClearListener;

    public interface OnClearListener {
        void onClear();
    }

    /* Required methods, not used in this implementation */
    public ClearableAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    /* Required methods, not used in this implementation */
    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /* Required methods, not used in this implementation */
    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        // Set the bounds of the clear button
//        this.setCompoundDrawablesWithIntrinsicBounds(null, null,imgClearButton, null);

        // Set the bounds of the search icon & clear button
        this.setCompoundDrawablesWithIntrinsicBounds(imgSearchIcon, null, imgClearButton, null);

        this.setCompoundDrawablePadding(15);

        // if the clear button is pressed, fire up the handler. Otherwise do nothing
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ClearableAutoCompleteTextView et = ClearableAutoCompleteTextView.this;

                if (et.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > et.getWidth() - et.getPaddingRight() - imgClearButton.getIntrinsicWidth()) {
                    onClearListener.onClear();
                    justCleared = true;
                }
                return false;
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (getText() != null && getText().toString().trim().length() > 0) {
                    showClearButton();
                } else {
                    hideClearButton();
                }
            }
        });

        hideClearButton();
    }

    public void setImgClearButton(Drawable imgClearButton) {
        this.imgClearButton = imgClearButton;
    }

    public void setOnClearListener(final OnClearListener clearListener) {
        this.onClearListener = clearListener;
    }

    public void hideClearButton() {
        this.setCompoundDrawables(imgSearchIcon, null, null, null);
    }

    public void showClearButton() {
//        this.setCompoundDrawablesWithIntrinsicBounds(null, null, imgClearButton, null); // to hide search button when typing
        this.setCompoundDrawablesWithIntrinsicBounds(imgSearchIcon, null, imgClearButton, null);
    }
}
