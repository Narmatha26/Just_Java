package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        String addWhippedCream, addChocolate;

        EditText nameText = findViewById(R.id.name_text);
        String name =  nameText.getText().toString();


        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        if(hasWhippedCream == true){
           addWhippedCream = "Yes";
        }else{
           addWhippedCream = "No";
        }

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        if(hasChocolate == true){
            addChocolate = "Yes";
        }else{
            addChocolate = "No";
        }

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, addWhippedCream, addChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"JustJava order for " + name );
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); }
    }

    private String createOrderSummary(int price, String whippedCreamChecked, String chocolateChecked, String customerName) {
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd whipped cream? " + whippedCreamChecked;
        priceMessage += "\nAdd chocolate? " + chocolateChecked + "\nQuantity: " + quantity +  "\nTotal : $" + price + "\nThank You!";
        return priceMessage;
    }


    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            basePrice += 1;
        }
        if(addChocolate){
            basePrice += 2;
        }
        return basePrice * quantity;
    }

    public void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(" " + number);
    }

    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(getApplicationContext(), "You cannot order more than 100 cups of coffee!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    public void decrement(View view) {
        if (quantity < 2) {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 cups of coffee!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }
}