# Android-CalculatorDialog

[![](https://jitpack.io/v/abbasalim/Android-CalculatorDialog.svg)](https://jitpack.io/#abbasalim/Android-CalculatorDialog)



<p align="center">
  <img src="http://uupload.ir/files/r1gk_screenshot_1571043912.png" width="350" title="example">
	 <img src="http://uupload.ir/files/zu29_screenshot_1571043933.png" width="350" title="example">
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
	        implementation 'com.github.abbasalim:Android-CalculatorDialog:v3.0.1'
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
     
                     NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
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
