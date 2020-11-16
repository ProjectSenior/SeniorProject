package com.example.seniorproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ir.farshid_roohi.linegraph.ChartEntity

class InfomationActivity : AppCompatActivity() {
    private val graph1 = floatArrayOf(113000f, 183000f, 188000f, 695000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000f)
    private val graph2 = floatArrayOf(0f, 245000f, 1011000f, 1000f, 0f, 0f, 47000f, 20000f, 12000f, 124400f, 160000f)
    private val legendArr = arrayOf("05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31")
    //for create animation
    lateinit var scaleUp: Animation;
    lateinit var scaleDown: Animation;
    private lateinit var auth: FirebaseAuth
    var numberOfInputWords: Int = 0;
    var checknum:Int=0;
    var tabspace:String="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid
//        setupLineChartData()
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down)

        //go back to dashboard
        var btnBackToDashboard = findViewById<View>(R.id.backInfomation)
        btnBackToDashboard.setOnClickListener {
            btnBackToDashboard.startAnimation(scaleDown)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        var database =FirebaseDatabase.getInstance().reference.child("Information").child(user.toString())
        var textView = findViewById<TextView>(R.id.textInfo)
        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for (i in snapshot.children) {
                    var humiditydb = i.child("humidity").getValue()
                    var moisturedb = i.child("moisture").getValue()
                    var tempdb = i.child("temp").getValue()
                    var datedb = i.child("date").getValue()
                    val words = datedb.toString().trim()
                    numberOfInputWords = words.length
                    checknum=0
                    tabspace=""
                    for( i in numberOfInputWords until 18){
                        tabspace = tabspace+"-"
                        checknum+=1
                    }
                    sb.append("$datedb " +"$moisturedb" +"$tempdb"+"$humiditydb\n")
//                    sb.append(" $usernamedb  " + "                          $scoredb\n")
                }
                textView.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this,"showtext"+name.toString(),Toast.LENGTH_LONG).show()
            }

        }
        database.addValueEventListener(getdata)
    }

//    private fun setupLineChartData() {
//        var lineChart = findViewById<LineChart>(R.id.reportingChart)
//        val yVals = ArrayList<Entry>()
//        yVals.add(Entry(0f, 6f, ))
//        yVals.add(Entry(1f, 2f, ))
//        yVals.add(Entry(2f, 4f, ))
//        yVals.add(Entry(8f,6f))
//        val xVals = ArrayList<Entry>()
//        xVals.add(Entry(1f,2f))
//        xVals.add(Entry(3f,5f))
//        xVals.add(Entry(6f,7f))
//        val set2 :LineDataSet
//        val set1: LineDataSet
//        set1 = LineDataSet(yVals, "DataSet 1")
//        set2 = LineDataSet(xVals,"DataSet 2")
//        // set1.fillAlpha = 110
//        // set1.setFillColor(Color.RED);
//
//        // set the line to be drawn like this "- - - - - -"
//        // set1.enableDashedLine(5f, 5f, 0f);
//        // set1.enableDashedHighlightLine(10f, 5f, 0f);
//        set1.color = Color.BLUE
//                set1.setCircleColor(Color.BLUE)
//        set1.lineWidth = 1f
//        set1.circleRadius = 3f
//        set1.setDrawCircleHole(false)
//        set1.valueTextSize = 0f
//        set1.setDrawFilled(false)
//        set2.color = Color.RED
//        set2.setCircleColor(Color.RED)
//        set2.lineWidth = 1f
//        set2.circleRadius = 3f
//        set2.setDrawCircleHole(false)
//        set2.valueTextSize = 0f
//        set2.setDrawFilled(false)
//
//        val dataSets = ArrayList<ILineDataSet>()
//        dataSets.add(set1)
//        dataSets.add(set2)
//        val data = LineData(dataSets)
//
//        // set data
//        lineChart.setData(data)
//        lineChart.description.isEnabled = true
//        lineChart.legend.isEnabled = false
//        lineChart.setPinchZoom(true)
//        lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
//        lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
//        lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
//        //lineChart.setDrawGridBackground()
//        lineChart.xAxis.labelCount = 11
//        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//    }
}