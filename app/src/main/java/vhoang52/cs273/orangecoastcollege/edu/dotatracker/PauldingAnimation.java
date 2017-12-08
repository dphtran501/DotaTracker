package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

public class PauldingAnimation extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BallBounce(this));
    }
}


class BallBounce extends View {
    int screenW;
    int screenH;
    int X;
    int Y;
    int initialY ;
    int headW;
    int headH;
    int angle;
    float vY;
    float acc;
    Bitmap pauldingHead, space;

    public BallBounce(Context context) {
        super(context);
        pauldingHead = BitmapFactory.decodeResource(getResources(),R.drawable.paulding_head); //load a pauldingHead image
        space = BitmapFactory.decodeResource(getResources(),R.drawable.space_bkgrd); //load a background
        headW = pauldingHead.getWidth();
        headH = pauldingHead.getHeight();
        acc = 0.2f;
        vY = 0;
        initialY = 100;
        angle = 0;
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        space = Bitmap.createScaledBitmap(space, w, h, true); //Resize background to fit the screen.
        X = (int) (screenW /2) - (headW / 2) ; //Center pauldingHead into the centre of the screen.
        Y = initialY;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw background.
        canvas.drawBitmap(space, 0, 0, null);

        //Compute roughly pauldingHead speed and location.
        Y+= (int) vY; //Increase or decrease vertical position.
        if (Y > (screenH - headH)) {
            vY =(-1)* vY; //Reverse speed when bottom hit.
        }
        vY += acc; //Increase or decrease speed.

        //Increase rotating angle.
        if (angle++ >360)
            angle =0;

        //Draw pauldingHead
        canvas.save(); //Save the position of the canvas.
        canvas.rotate(angle, X + (headW / 2), Y + (headH / 2)); //Rotate the canvas.
        canvas.drawBitmap(pauldingHead, X, Y, null); //Draw the pauldingHead on the rotated canvas.
        canvas.restore(); //Rotate the canvas back so that it looks like pauldingHead has rotated.

        //Call the next frame.
        invalidate();
    }
}