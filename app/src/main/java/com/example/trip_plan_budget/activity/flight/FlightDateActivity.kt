package com.example.trip_plan_budget.activity.flight

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.andrewjapar.rangedatepicker.CalendarPicker.SelectionMode
import com.example.trip_plan_budget.R
import com.example.trip_plan_budget.model.FlightModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_flight_date.*
import java.util.*

class FlightDateActivity : AppCompatActivity() {
    private var to: Date? = null
    private var from: Date? = null

    private lateinit var model: FlightModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_date)

        if (intent != null) {
            model = intent.getParcelableExtra("flight") as FlightModel
            calendar_view.setMode(if (model.isRoundTrip) SelectionMode.RANGE else SelectionMode.SINGLE)
            if (!model.isRoundTrip) {
                calendar_view.setOnStartSelectedListener { startDate, label ->
                    from = startDate
                    departure_date.text = label
                    return_date.text = "-"
                }
            } else {
                calendar_view.setOnRangeSelectedListener { start, end, startLabel, endLabel ->
                    from = start
                    to = end
                    return_date.text = endLabel
                    departure_date.text = startLabel
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.flight_date_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_flight_dates_done) {
            if (to != null && from != null) {
                model.to = to
                model.from = from
                val intent = Intent(this, PriceListActivity::class.java)
                intent.putExtra("flight", model)
                startActivity(intent)
                return true
            } else {
                Snackbar.make(findViewById(R.id.root_flight_date_activity), "Please Fill the dates.", Snackbar.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}