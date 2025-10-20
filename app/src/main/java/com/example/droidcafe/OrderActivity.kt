package com.example.droidcafe

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {

    private lateinit var citySpinner: Spinner
    private var selectedDeliveryMethod: String = "Next Day Ground Delivery"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setupCitySpinner()
    }

    private fun setupCitySpinner() {
        citySpinner = findViewById(R.id.city_spinner)

        // Define the city options
        val cities = arrayOf(
            "Pilih Kota",
            "Kota Bandung",
            "Kota Cimahi",
            "Kabupaten Bandung",
            "Kota Jakarta",
            "Kota Surabaya"
        )

        // Create adapter for spinner
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.sameday -> if (checked) {
                selectedDeliveryMethod = getString(R.string.same_day_messenger_service)
                displayToast("Metode pengiriman: $selectedDeliveryMethod")
            }
            R.id.nextday -> if (checked) {
                selectedDeliveryMethod = getString(R.string.next_day_ground_delivery)
                displayToast("Metode pengiriman: $selectedDeliveryMethod")
            }
            R.id.pickup -> if (checked) {
                selectedDeliveryMethod = getString(R.string.pick_up)
                displayToast("Metode pengiriman: $selectedDeliveryMethod")
            }
        }
    }

    // NEW: Handle order submission
    fun onSubmitOrder(view: View) {
        val name = findViewById<android.widget.EditText>(R.id.name_text).text.toString()
        val address = findViewById<android.widget.EditText>(R.id.address_text).text.toString()
        val phone = findViewById<android.widget.EditText>(R.id.phone_text).text.toString()
        val note = findViewById<android.widget.EditText>(R.id.note_text).text.toString()
        val selectedCity = citySpinner.selectedItem.toString()

        // Validation
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            displayToast("Harap lengkapi semua data yang diperlukan")
            return
        }

        if (selectedCity == "Pilih Kota") {
            displayToast("Harap pilih kota tujuan")
            return
        }

        // Create order summary
        val orderSummary = """
            Order Summary:
            Nama: $name
            Alamat: $address
            Telepon: $phone
            Kota Tujuan: $selectedCity
            Metode Pengiriman: $selectedDeliveryMethod
            Catatan: ${if (note.isEmpty()) "Tidak ada" else note}
        """.trimIndent()

        displayToast("Pesanan berhasil dikirim!\n$orderSummary")

        // Here you can add code to save the order or send it to a server
    }

    private fun displayToast(message: String) {
        Toast.makeText(
            applicationContext, message,
            Toast.LENGTH_LONG
        ).show()
    }
}