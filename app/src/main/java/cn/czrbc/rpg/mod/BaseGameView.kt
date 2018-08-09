package cn.czrbc.rpg.mod

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author a9573
 * @date 2018/08/09.
 */
 abstract class BaseGameView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback, Runnable {
    private  var mSurfaceHolder: SurfaceHolder = this.holder
    private var mThread: Thread = Thread(this)
    protected var paint: Paint = Paint()
    protected var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    protected var moving = false
    protected var drawable: Boolean = false
    protected var moveTime = 0
    protected var fingerPoint = PointF(0f,0f)
    protected var preMove = false
    init {
        mSurfaceHolder.addCallback(this)
        textPaint.color = Color.WHITE
//        textPaint.style = Paint.Style.STROKE// 空心
        textPaint.strokeJoin = Paint.Join.ROUND
        textPaint.strokeCap = Paint.Cap.ROUND
//        textPaint.strokeWidth = 5f
        textPaint.textSize = 40f
    }

    @Synchronized
    private fun draw(){
        var canvas:Canvas? = null
        try {
           canvas= mSurfaceHolder.lockCanvas()
            beforeDraw()
            canvas.drawColor(Color.BLACK)
            drawBackGround(canvas,paint)
            drawEffect(canvas,paint)
            drawRole(canvas,paint)
        }catch (ex:Exception){
            ex.printStackTrace()
        }finally {
            afterDraw()
            if (canvas!=null)
                mSurfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
    abstract fun beforeDraw()
    abstract fun drawBackGround(canvas: Canvas,paint: Paint)
    abstract fun drawEffect(canvas: Canvas,paint: Paint)
    abstract fun drawRole(canvas: Canvas,paint: Paint)
    abstract fun afterDraw()


    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        drawable =false
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        Map.screenWidth = this.width.toFloat()
        Map.screenHeight = this.height.toFloat()
        drawable =true
        mThread.start()
    }

    override fun run() {
        while (drawable) {
            try {
                move()
                draw()
                Thread.sleep(50)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

    var maxMoveTime =0
    private fun move(){
        if (moving ){
            if (moveTime<maxMoveTime) {
                roleMove()
                moveTime++
            }else{
                moveTime = 0
                maxMoveTime =0
                moving =false
                preMove = false
            }
        }else{
            moveTime = 0
            maxMoveTime =0
        }
    }
    abstract fun roleMove()
    abstract fun getMoveTimeAll():Int

    // 在地图上移动的目标点 destination:目的地，终点
    var destination =PointF(0f,0f)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                fingerPoint.x = event.rawX
                fingerPoint.y = event.rawY
                destination = Map.getMapPoint(fingerPoint)
                moving =false
                preMove =true
                return true
            }
            MotionEvent.ACTION_UP ->{
                fingerPoint.x = event.rawX
                fingerPoint.y = event.rawY
                moving = true
                destination = Map.getMapPoint(fingerPoint)
                maxMoveTime =getMoveTimeAll()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}


