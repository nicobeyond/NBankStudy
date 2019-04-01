package com.nbank.study;

import android.os.Parcel;
import android.os.Parcelable;

import ico.ico.util.log;

public class ParcelTest implements Parcelable {

    private String mSerializedPattern;
    private int mDisplayMode;
    private boolean mInputEnabled;
    private boolean mInStealthMode;
    private boolean mTactileFeedbackEnabled;

    public ParcelTest() {
        mSerializedPattern = "dasdas";
        mDisplayMode = 100;
        mInputEnabled = true;
        mInStealthMode = false;
        mTactileFeedbackEnabled = true;
    }

    private ParcelTest(Parcel in) {
        mSerializedPattern = in.readString();
        mDisplayMode = in.readInt();
        mInputEnabled = (Boolean) in.readValue(null);
        mInStealthMode = (Boolean) in.readValue(null);
        mTactileFeedbackEnabled = (Boolean) in.readValue(null);
    }

    @Override
    public String toString() {
        return "ParcelTest{" +
                "mSerializedPattern='" + mSerializedPattern + '\'' +
                ", mDisplayMode=" + mDisplayMode +
                ", mInputEnabled=" + mInputEnabled +
                ", mInStealthMode=" + mInStealthMode +
                ", mTactileFeedbackEnabled=" + mTactileFeedbackEnabled +
                '}';
    }

    @Override
    public int describeContents() {
            return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSerializedPattern);
        dest.writeInt(mDisplayMode);
        dest.writeValue(mInputEnabled);
        dest.writeValue(mInStealthMode);
        dest.writeValue(mTactileFeedbackEnabled);
    }

    public static final Parcelable.Creator<ParcelTest> CREATOR =
            new Creator<ParcelTest>() {
                public ParcelTest createFromParcel(Parcel in) {
                    log.w("dasdas");
                    return new ParcelTest(in);
                }

                public ParcelTest[] newArray(int size) {
                    return new ParcelTest[size];
                }
            };
}
