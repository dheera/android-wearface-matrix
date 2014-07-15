package wearmatrixface.net.dheera.wearfacematrix;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class FaceActivity extends Activity implements SurfaceHolder.Callback {
    final private boolean D = true;
    final private String TAG = "WearFaceMatrix";
    final private FaceActivity self = this;

    private DrawTask mDrawTask;
    private SurfaceHolder mSurfaceHolder = null;
    private Canvas drawCanvas = null;

    // widgets
    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(D) Log.d(TAG, "onCreate();");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_face);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mSurfaceView = (SurfaceView) stub.findViewById(R.id.surfaceView);
                mSurfaceView.getHolder().addCallback(self);
            }
        });
        mDrawTask = new DrawTask();
        mDrawTask.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        /*
        drawCanvas = holder.lockCanvas();
        if (drawCanvas != null) {
            drawCanvas.drawRGB(255, 30, 30);
            // holder.unlockCanvasAndPost(drawCanvas);
        }*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
        mSurfaceHolder = holder;
        /*
        drawCanvas = holder.lockCanvas();
        if (drawCanvas != null) {
            drawCanvas.drawRGB(255, 30, 30);
            // holder.unlockCanvasAndPost(drawCanvas);
        }*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawCanvas = null;
    }

    @Override
    protected void onPause() {
        //  onPause();
        if(D) Log.d(TAG, "onPause();");
        super.onPause();

        mDrawTask.setPaused();
    }

    @Override
    protected void onResume() {
        //  onResume();
        if(D) Log.d(TAG, "onResume();");
        super.onResume();

        mDrawTask.setUnpaused();
    }

    private class DrawTask extends Thread {
        final private String TAG = "WearFaceMatrix/DrawTask";

        final private String[] matrixChars = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "@", "$", "&", "%", "(", ")", "*", "%", "!", "#", "ア", "イ", "ウ", "エ",
            "オ", "カ", "キ", "ク", "ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ", "ト",
            "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ"};

        final private boolean[][][] digitMasks = {
                { // 0
                        {false, true , true , false},
                        {true , false, false, true },
                        {true , false, false, true },
                        {true , false, false, true },
                        {true , false, false, true },
                        {true , false, false, true },
                        {false, true , true , false},
                },
                { // 1
                        {false, false, true , false},
                        {false, true , true , false},
                        {true , false, true , false},
                        {false, false, true , false},
                        {false, false, true , false},
                        {false, false, true , false},
                        {false, false, true , false},
                },
                { // 2
                        {false, true , true , false},
                        {true , false, false, true },
                        {true , false, false, true },
                        {false, false, true , false},
                        {false, true , false, false},
                        {true , false, false, false},
                        {true , true , true , true },
                },
                { // 3
                        {false, true , true , false},
                        {true , false, false, true },
                        {false, false, false, true },
                        {false, true , true , false},
                        {false, false, false, true },
                        {true , false, false, true },
                        {false, true , true , false},
                },
                { // 4
                        {true , false, false, true },
                        {true , false, false, true },
                        {true , false, false, true },
                        {true , true , true , true },
                        {false, false, false, true },
                        {false, false, false, true },
                        {false, false, false, true },
                },
                { // 5
                        {true , true , true , true },
                        {true , false, false, false},
                        {true , false, false, false},
                        {true , true , true , true },
                        {false, false, false, true },
                        {false, false, false, true },
                        {true , true , true , false},
                },
                { // 6
                        {false, true , true , true },
                        {true , false, false, false},
                        {true , false, false, false},
                        {true , true , true , false},
                        {true , false, false, true },
                        {true , false, false, true },
                        {false, true , true , false},
                },
                { // 7
                        {true , true , true , true },
                        {false, false, false, true },
                        {false, false, false, true },
                        {false, false, false, true },
                        {false, false, true,  false},
                        {false, false, true,  true },
                        {false, false, true,  true },
                },
                { // 8
                        {false, true , true , false},
                        {true , false, false, true },
                        {true , false, false, true },
                        {false, true , true , false},
                        {true , false, false, true },
                        {true , false, false, true },
                        {false, true , true , false},
                },
                { // 9
                        {false, true , true , true },
                        {true , false, false, true },
                        {true , false, false, true },
                        {false, true , true , true },
                        {false, false, false, true },
                        {false, false, false, true },
                        {false, false, false, true },
                },
        };

        private final int numRows = 23;
        int charWidth;
        int xOffset;

        private String[][] theMatrix = new String[numRows][numRows];
        private int[][] theIntensities = new int[numRows][numRows];
        private boolean[][] timeMask = new boolean[numRows][numRows];

        private volatile boolean paused = false;
        private final Object signal = new Object();
        private Random random = new Random();
        private Paint[] paints = new Paint[8];
        private Paint paintTime;
        private Paint paintTimePause;
        int[] timedigits = new int[4];
        int oldMinute = -1;

        public DrawTask() {
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            charWidth = size.x / numRows;
            xOffset = (size.x - charWidth * numRows)/2;

            int i, j;
            for(i = 0; i <=5 ; i++) {
                paints[i] = new Paint();
                paints[i].setColor(Color.rgb(0, i*32, 0));
                paints[i].setTextSize(charWidth - 1);
            }
            paints[6] = new Paint();
            paints[6].setColor(Color.rgb(63, 255, 63));
            paints[6].setTextSize(charWidth - 1);
            paints[7] = new Paint();
            paints[7].setColor(Color.rgb(191, 255, 191));
            paints[7].setTextSize(charWidth - 1);
            paintTime = new Paint();
            paintTime.setColor(Color.rgb(127, 255, 191));
            paintTime.setAlpha(191);
            paintTime.setStyle(Paint.Style.FILL);
            paintTimePause = new Paint();
            paintTimePause.setColor(Color.WHITE);
            paintTimePause.setStyle(Paint.Style.FILL);

            for(i = 0; i < numRows; i++) {
                for (j = 0; j < numRows; j++) {
                    theMatrix[i][j] = matrixChars[random.nextInt(matrixChars.length)];
                    theIntensities[i][j]=0;
                    timeMask[i][j] = false;
                }
            }
            updateTime();
        }

        public boolean updateTime() {
            int i, j, d;
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            if(minute != oldMinute) {
                timedigits[0] = hour / 10;
                timedigits[1] = hour % 10;
                timedigits[2] = minute / 10;
                timedigits[3] = minute % 10;

                for (d = 0; d < 4; d++) {
                    for (i = 0; i < 4; i++) {
                        for (j = 0; j < 7; j++) {
                            timeMask[i + 2 + d * 5][j + 8] = digitMasks[timedigits[d]][j][i];
                        }
                    }
                }
                oldMinute = minute;
                return true;
            } else {
                return false;
            }
        }

        public void run() {
            while(true) {
                boolean needToDraw = updateTime();
                if ((!paused || needToDraw) && mSurfaceHolder != null && (drawCanvas = mSurfaceHolder.lockCanvas()) != null) {
                    runOnUiThread(new Runnable() {
                        public void run() {

                        }
                    });
                    Random random = new Random();
                    drawCanvas.drawRGB(0, 0, 0);

                    int i, j;
                    if(paused) {
                        for(i = 2; i < numRows - 2; i++) {
                            for(j = 7; j < 15; j++) {
                                if(timeMask[i][j]) {
                                    drawCanvas.drawRect(xOffset + i * charWidth - 1, j * charWidth + 2, xOffset + i*charWidth + charWidth - 3, j*charWidth + charWidth,  paintTimePause);
                                }
                            }
                        }
                    } else {
                        for (i = 0; i < numRows; i++) {
                            for (j = numRows-1; j > 0; j--) {
                                if(theIntensities[i][j] == 7 || (j<5 && random.nextInt(24)==0)) {
                                    drawCanvas.drawText(theMatrix[i][j], xOffset + i * charWidth, j * charWidth, paints[7]);
                                    if(random.nextInt(2)==0) {
                                        if (j < numRows - 1) {
                                            theIntensities[i][j + 1] = 7;
                                            theMatrix[i][j + 1] = matrixChars[random.nextInt(matrixChars.length)];
                                        }
                                        theIntensities[i][j] = 6;
                                    }
                                } else {
                                    drawCanvas.drawText(theMatrix[i][j], xOffset + i * charWidth, j * charWidth, paints[theIntensities[i][j]]);
                                    theIntensities[i][j]+=random.nextInt(5)-3;
                                    if(theIntensities[i][j]<0) theIntensities[i][j]=0;
                                    if(theIntensities[i][j]>=7) theIntensities[i][j]=6;
                                }
                                if(timeMask[i][j]) {
                                    drawCanvas.drawRect(xOffset + i * charWidth - 1, j * charWidth + 2, xOffset + i*charWidth + charWidth - 3, j*charWidth + charWidth,  paintTime);
                                }
                            }
                        }
                    }
                    mSurfaceHolder.unlockCanvasAndPost(drawCanvas);
                }
                try {
                    if(paused) Thread.sleep(5000);
                    else Thread.sleep(30);
                } catch (Exception e) {
                    // interrupted, no problem
                }
            }
        }

        public void setPaused() {
            paused = true;
            oldMinute = -1;
            this.interrupt();
        }

        public void setUnpaused() {
            int i, j;
            oldMinute = -1;
            paused = false;
            for(i = 0; i < numRows; i++) {
                for (j = 0; j < numRows; j++) {
                    theIntensities[i][j]=0;
                }
            }
            this.interrupt();
        }
    }
}
