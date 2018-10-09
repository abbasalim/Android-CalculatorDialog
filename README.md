# Android-CalculatorDialog

[![](https://jitpack.io/v/abbasalim/Android-CalculatorDialog.svg)](https://jitpack.io/#abbasalim/Android-CalculatorDialog)



<p align="center">
  <img src="http://uupload.ir/files/mw47_screenshot_from_2018-08-16_19-33-34.png" width="350" title="example">
</p>





#How to Use


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.abbasalim:Android-CalculatorDialog:v2.0'
	}
	

  1:Easy Way (input must Integer,Export is Math.round(result)):
  
	 easyCalculate(YourActivity.this,Your_editText,",",false);
	 //or
	 easyCalculate(this,et_price);// remove ",",No abs
	
  
  2:Other Way :
  
	 public void showCalculator(View view) {
             int value = 0;
             try {
                 value = Integer.parseInt(et_price.getText().toString().trim());
             } catch (NumberFormatException e) {
                 e.printStackTrace();
             }
             new CalculatorDialog(this) {
                 @Override
                 public void onResult(String result) {
     
                     NumberFormat nf = NumberFormat.getInstance();
                     double number = 0;
                     try {
                         number =nf.parse(result).doubleValue();
                         et_price.setText(number+ "");
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
     
                 }
             }.setValue(value).showDIalog();
         }
	
  #نکته
  
  کاربران ایرانی ممکنه واسه افزودن کتابخونه به دلیل تحریم های ظالمانه بر علیه کشورمون نیاز به تغییر آی پی داشته باشند!
  
WaveAcc.ir