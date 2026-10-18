package it.sephiroth.demo.slider.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import ua.stu.view.scpview.R;

/**
 * Легка та сучасна реалізація висувної панелі без застарілих залежностей.
 */
public class MultiDirectionSlidingDrawer extends RelativeLayout {

    private int handleId;
    private int contentId;
    private View handle;
    private View content;
    private boolean isOpened = false;
    private int topOffset = 0;

    public MultiDirectionSlidingDrawer(Context context) {
        super(context);
        init(context, null);
    }

    public MultiDirectionSlidingDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultiDirectionSlidingDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiDirectionSlidingDrawer);
            handleId = a.getResourceId(R.styleable.MultiDirectionSlidingDrawer_handle, 0);
            contentId = a.getResourceId(R.styleable.MultiDirectionSlidingDrawer_content, 0);
            topOffset = a.getDimensionPixelSize(R.styleable.MultiDirectionSlidingDrawer_topOffset, 0);
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (handleId != 0) {
            handle = findViewById(handleId);
            if (handle != null) {
                handle.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggle();
                    }
                });
            }
        }
        if (contentId != 0) {
            content = findViewById(contentId);
            if (content != null) {
                content.setVisibility(GONE);
            }
        }
    }

    public void setTopOffset(int offset) {
        this.topOffset = offset;
    }

    public void toggle() {
        if (isOpened) {
            animateClose();
        } else {
            animateOpen();
        }
    }

    public void animateOpen() {
        if (content != null) {
            content.setVisibility(VISIBLE);
            content.setAlpha(0f);
            content.animate()
                    .alpha(1f)
                    .setDuration(250)
                    .start();
        }
        isOpened = true;
    }

    public void animateClose() {
        if (content != null) {
            content.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            content.setVisibility(GONE);
                        }
                    })
                    .start();
        }
        isOpened = false;
    }

    public void open() {
        animateOpen();
    }

    public void close() {
        animateClose();
    }

    public boolean isOpened() {
        return isOpened;
    }
}
