package com.helpinghands.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpinghand.R;
import com.helpinghands.adapter.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;

/**
 * Created by ahmadnajar on 3/8/17.
 */

public class FancyAlertDialog extends DialogFragment {

    public static final String TAG = FancyAlertDialog.class.getSimpleName();
    private Builder builder;
    private static FancyAlertDialog instance = new FancyAlertDialog();

    public static FancyAlertDialog getInstance() {
        return instance;
    }

    private CardView cardView;
    private AppCompatImageView image;
    private TextView title, subTitle, body;
    private Button positive, negative;
    private LinearLayout buttonsPanel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            builder = (Builder) savedInstanceState.getSerializable(Builder.class.getSimpleName());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Builder.class.getSimpleName(), builder);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_view_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (builder != null) {
            if (builder.getTextTitle() != null) {
                title.setText(builder.getTextTitle());
            } else {
                title.setVisibility(View.GONE);
            }
            if (builder.getTitleColor() != 0) {
                title.setTextColor(ContextCompat.getColor(getActivity(), builder.getTitleColor()));
            }
            if (builder.getTextSubTitle() != null) {
                subTitle.setText(builder.getTextSubTitle());
            } else {
                subTitle.setVisibility(View.GONE);
            }
            if (builder.getSubtitleColor() != 0) {
                subTitle.setTextColor(ContextCompat.getColor(getActivity(), builder.getSubtitleColor()));
            }
            if (builder.getBody() != null) {
                body.setText(builder.getBody());
            } else {
                body.setVisibility(View.GONE);
            }
            body.setText(builder.getBody());
            if (builder.getBodyColor() != 0) {
                body.setTextColor(ContextCompat.getColor(getActivity(), builder.getBodyColor()));
            }

            if (builder.getPositiveButtonText() != null) {
                positive.setText(builder.getPositiveButtonText());
                if (builder.getPositiveTextColor() != 0) {
                    positive.setTextColor(ContextCompat.getColor(getActivity(), builder.getPositiveTextColor()));
                }
                if (builder.getOnPositiveClicked() != null) {
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.getOnPositiveClicked().OnClick(v, getDialog());
                        }
                    });
                }
            } else {
                positive.setVisibility(View.GONE);
            }
            if (builder.getNegativeButtonText() != null) {
                negative.setText(builder.getNegativeButtonText());
                if (builder.getNegativeColor() != 0) {
                    negative.setTextColor(ContextCompat.getColor(getActivity(), builder.getNegativeColor()));
                }
                if (builder.getOnNegativeClicked() != null) {
                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.getOnNegativeClicked().OnClick(v, getDialog());
                        }
                    });
                }
            } else {
                negative.setVisibility(View.GONE);
            }


            if (builder.getImageRecourse() != 0) {
                Drawable imageRes = VectorDrawableCompat.create(getResources(), builder.getImageRecourse(), getActivity().getTheme());
                image.setImageDrawable(imageRes);
            } else if (builder.getImageDrawable() != null) {
                if (builder.getImageDrawable().isEmpty()){
                    image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_user));
                }else {
                    Picasso.with(getActivity())
                            .load(builder.getImageDrawable())
                            .error(R.drawable.ic_user)
                            .transform(new CircleTransform())
                            .into(image);
                }
                //image.setImageDrawable(builder.getImageDrawable());
            } else {
                image.setVisibility(View.GONE);
            }

            if (builder.getBackgroundColor() != 0) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(), builder.getBackgroundColor()));
            }


            if (builder.isAutoHide()) {
                int time = builder.getTimeToHide() != 0 ? builder.getTimeToHide() : 10000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdded() && getActivity() != null)
                            dismiss();
                    }
                }, time);
            }

            if (builder.getTitleFont() != null) {
                title.setTypeface(builder.getTitleFont());
            }

            if (builder.getSubTitleFont() != null) {
                subTitle.setTypeface(builder.getSubTitleFont());
            }

            if (builder.getBodyFont() != null) {
                body.setTypeface(builder.getBodyFont());
            }

            if (builder.getPositiveButtonFont() != null) {
                positive.setTypeface(builder.getPositiveButtonFont());
            }
            if (builder.getNegativeButtonFont() != null) {
                negative.setTypeface(builder.getNegativeButtonFont());
            }

            if (builder.getAlertFont() != null) {
                title.setTypeface(builder.getAlertFont());
                subTitle.setTypeface(builder.getAlertFont());
                body.setTypeface(builder.getAlertFont());
                positive.setTypeface(builder.getAlertFont());
                negative.setTypeface(builder.getAlertFont());
            }

            if (builder.getButtonsGravity() != null) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                switch (builder.getButtonsGravity()) {
                    case LEFT:
                        params.gravity = Gravity.LEFT;
                        break;
                    case RIGHT:
                        params.gravity = Gravity.RIGHT;
                        break;
                    case CENTER:
                        params.gravity = Gravity.CENTER;
                        break;
                }
                buttonsPanel.setLayoutParams(params);
            }
        }
    }

    private void initViews(View view) {
        cardView = (CardView) view.findViewById(R.id.card_view);
        image = (AppCompatImageView) view.findViewById(R.id.image);
        cardView = (CardView) view.findViewById(R.id.card_view);
        title = (TextView) view.findViewById(R.id.title);
        subTitle = (TextView) view.findViewById(R.id.sub_title);
        body = (TextView) view.findViewById(R.id.body);
        positive = (Button) view.findViewById(R.id.position);
        negative = (Button) view.findViewById(R.id.negative);
        buttonsPanel = (LinearLayout) view.findViewById(R.id.buttons_panel);
    }

    private Dialog show(Activity activity, Builder builder) {
        this.builder = builder;
        show(((AppCompatActivity) activity).getSupportFragmentManager(), TAG);
        return getDialog();
    }


    public static class Builder implements Serializable {

        private String positiveButtonText;
        private String negativeButtonText;

        private String textTitle;
        private String textSubTitle;
        private String body;

        private OnPositiveClicked onPositiveClicked;
        private OnNegativeClicked onNegativeClicked;


        private boolean autoHide;

        private int timeToHide;

        private int positiveTextColor;
        private int backgroundColor;
        private int negativeColor;
        private int titleColor;
        private int subtitleColor;
        private int bodyColor;

        private int imageRecourse;
        private String imageDrawable;

        private Typeface titleFont;
        private Typeface subTitleFont;
        private Typeface bodyFont;
        private Typeface positiveButtonFont;
        private Typeface negativeButtonFont;

        private Typeface alertFont;

        private Activity activity;

        private PanelGravity buttonsGravity;

        public PanelGravity getButtonsGravity() {
            return buttonsGravity;
        }

        public Builder setButtonsGravity(PanelGravity buttonsGravity) {
            this.buttonsGravity = buttonsGravity;
            return this;
        }

        public Typeface getAlertFont() {
            return alertFont;
        }

        public Builder setAlertFont(String alertFont) {
            this.alertFont = Typeface.createFromAsset(activity.getAssets(), alertFont);
            return this;
        }

        public Typeface getPositiveButtonFont() {
            return positiveButtonFont;
        }

        public Builder setPositiveButtonFont(String positiveButtonFont) {
            this.positiveButtonFont = Typeface.createFromAsset(activity.getAssets(), positiveButtonFont);
            return this;
        }

        public Typeface getNegativeButtonFont() {
            return negativeButtonFont;
        }

        public Builder setNegativeButtonFont(String negativeButtonFont) {
            this.negativeButtonFont = Typeface.createFromAsset(activity.getAssets(), negativeButtonFont);
            return this;
        }

        public Typeface getTitleFont() {
            return titleFont;
        }


        public Builder setTitleFont(String titleFontPath) {
            this.titleFont = Typeface.createFromAsset(activity.getAssets(), titleFontPath);
            return this;
        }

        public Typeface getSubTitleFont() {
            return subTitleFont;
        }

        public Builder setSubTitleFont(String subTitleFontPath) {
            this.subTitleFont = Typeface.createFromAsset(activity.getAssets(), subTitleFontPath);
            return this;
        }

        public Typeface getBodyFont() {
            return bodyFont;
        }

        public Builder setBodyFont(String bodyFontPath) {
            this.bodyFont = Typeface.createFromAsset(activity.getAssets(), bodyFontPath);
            return this;
        }


        public int getTimeToHide() {
            return timeToHide;
        }

        public Builder setTimeToHide(int timeToHide) {
            this.timeToHide = timeToHide;
            return this;
        }

        public boolean isAutoHide() {
            return autoHide;
        }

        public Builder setAutoHide(boolean autoHide) {
            this.autoHide = autoHide;
            return this;
        }

        public Activity getActivity() {
            return activity;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder(Activity context) {
            this.activity = context;
        }

        public int getPositiveTextColor() {
            return positiveTextColor;
        }

        public Builder setPositiveColor(int positiveTextColor) {
            this.positiveTextColor = positiveTextColor;
            return this;
        }


        public int getBackgroundColor() {
            return backgroundColor;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public int getNegativeColor() {
            return negativeColor;
        }

        public Builder setNegativeColor(int negativeColor) {
            this.negativeColor = negativeColor;
            return this;
        }


        public int getTitleColor() {
            return titleColor;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public int getSubtitleColor() {
            return subtitleColor;
        }

        public Builder setSubtitleColor(int subtitleColor) {
            this.subtitleColor = subtitleColor;
            return this;
        }

        public int getBodyColor() {
            return bodyColor;
        }

        public Builder setBodyColor(int bodyColor) {
            this.bodyColor = bodyColor;
            return this;
        }

        public int getImageRecourse() {
            return imageRecourse;
        }

        public Builder setImageRecourse(int imageRecourse) {
            this.imageRecourse = imageRecourse;
            return this;
        }

        public String getImageDrawable() {
            return imageDrawable;
        }

        public Builder setImageDrawable(String imageDrawable) {
            this.imageDrawable = imageDrawable;
            return this;
        }

        public String getPositiveButtonText() {
            return positiveButtonText;
        }


        public Builder setPositiveButtonText(int positiveButtonText) {
            this.positiveButtonText = activity.getString(positiveButtonText);
            return this;
        }

        public Builder setPositiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public String getNegativeButtonText() {
            return negativeButtonText;
        }

        public Builder setNegativeButtonText(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public Builder setNegativeButtonText(int negativeButtonText) {
            this.negativeButtonText = activity.getString(negativeButtonText);
            return this;
        }

        public String getTextTitle() {
            return textTitle;
        }

        public Builder setTextTitle(String textTitle) {
            this.textTitle = textTitle;
            return this;
        }

        public Builder setTextTitle(int textTitle) {
            this.textTitle = activity.getString(textTitle);
            return this;
        }

        public String getTextSubTitle() {
            return textSubTitle;
        }

        public Builder setTextSubTitle(String textSubTitle) {
            this.textSubTitle = textSubTitle;
            return this;
        }

        public Builder setTextSubTitle(int textSubTitle) {
            this.textSubTitle = activity.getString(textSubTitle);
            return this;
        }

        public String getBody() {
            return body;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setBody(int body) {
            this.body = activity.getString(body);
            return this;
        }

        public OnPositiveClicked getOnPositiveClicked() {
            return onPositiveClicked;
        }

        public Builder setOnPositiveClicked(OnPositiveClicked onPositiveClicked) {
            this.onPositiveClicked = onPositiveClicked;
            return this;
        }

        public OnNegativeClicked getOnNegativeClicked() {
            return onNegativeClicked;
        }

        public Builder setOnNegativeClicked(OnNegativeClicked onNegativeClicked) {
            this.onNegativeClicked = onNegativeClicked;
            return this;
        }


        public Builder build() {
            return this;
        }


        public Dialog show() {
            return FancyAlertDialog.getInstance().show(activity, this);
        }


    }


    public interface OnPositiveClicked {
        void OnClick(View view, Dialog dialog);
    }

    public interface OnNegativeClicked {
        void OnClick(View view, Dialog dialog);
    }

    public enum PanelGravity {
        LEFT,
        RIGHT,
        CENTER
    }


}