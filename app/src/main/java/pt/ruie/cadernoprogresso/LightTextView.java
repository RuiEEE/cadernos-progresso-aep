package pt.ruie.cadernoprogresso;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LightTextView extends TextView {
	
	Typeface light;

	public LightTextView(Context context) {
		super(context);
	}
	
	public LightTextView(Context context,AttributeSet attrs){
		super(context,attrs);
		light = Typeface.createFromAsset(context.getAssets(),"Roboto-Light.ttf");
		this.setTypeface(light);
	}

}

