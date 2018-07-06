//
//                   _oo0oo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  0\  =  /0
//                ___/`---'\___
//              .' \\|     |// '.
//             / \\|||  :  |||// \
//            / _||||| -:- |||||- \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |_/ |
//           \  .-\__  '-'  ___/-. /
//         ___'. .'  /--.--\  `. .'___
//       ."" '<  `.___\_<|>_/___.' >' "".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `_.   \_ __\ /__ _/   .-` /  /
//  =====`-.____`.___ \_____/___.-`___.-'=====
//                   `=---='
//
//
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//            ·ð×æ±£ÓÓ         ÓÀÎÞBUG
//
package com.whb.caculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Caculator extends Activity {
    private static final String TAG = "Caculator";
    private static final boolean DEBUG = false;

	private Context mContext = null;
	private Button mOne = null;
	private Button mTwo = null;
	private Button mThree = null;
	private Button mFour = null;
	private Button mFive = null;
	private Button mSix = null;
	private Button mSeven = null;
	private Button mEight = null;
	private Button mNine = null;
	private Button mZero = null;
	private Button mDot = null;
	private Button mEqual = null;
	private Button mDelete = null;
	private Button mAdd = null;
	private Button mSubtract = null;
	private Button mMultiply = null;
	private Button mDivide = null;
	private Button mClear = null;
	private TextView mInput = null;
	private TextView mResult = null;

	private static final Pattern INVALID_DATA_REGULAR = Pattern.compile("(\\d*\\.\\d*\\.)");
	private StackData mDataStack = new StackData();
	private StackSymbol mSymbolStack = new StackSymbol();

    private static final double PRECISION_LOW = -0.0000001;
    private static final double PRECISION_UP = 0.0000001;
    private static final double INVALID = 4294967295.0;

    private static final int NONE = 0;
	private static final int ADD = 1;
	private static final int SUB = 2;
	private static final int MULTI = 3;
	private static final int DIV = 4;

    private static final int EQUAL = 0;
	private static final int LESS = -1;
	private static final int MORE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		
		init(mContext);
	}
	
	private void init(Context context) {
		mOne = (Button)findViewById(R.id.one);
		mOne.setOnClickListener(mClickListener);
		
		mTwo = (Button)findViewById(R.id.two);
		mTwo.setOnClickListener(mClickListener);
		
		mThree = (Button)findViewById(R.id.three);
		mThree.setOnClickListener(mClickListener);
		
		/*--------------------------------------------*/
		
		mFour = (Button)findViewById(R.id.four);
		mFour.setOnClickListener(mClickListener);
		
		mFive = (Button)findViewById(R.id.five);
		mFive.setOnClickListener(mClickListener);
		
		mSix = (Button)findViewById(R.id.six);
		mSix.setOnClickListener(mClickListener);

		/*--------------------------------------------*/
		
		mSeven = (Button)findViewById(R.id.seven);
		mSeven.setOnClickListener(mClickListener);
		
		mEight = (Button)findViewById(R.id.eight);
		mEight.setOnClickListener(mClickListener);
		
		mNine = (Button)findViewById(R.id.nine);
		mNine.setOnClickListener(mClickListener);

		/*--------------------------------------------*/
		
        mDot = (Button)findViewById(R.id.dot);
        mDot.setOnClickListener(mClickListener);

		mZero = (Button)findViewById(R.id.zero);
		mZero.setOnClickListener(mClickListener);

		mEqual = (Button)findViewById(R.id.equal);
		mEqual.setOnClickListener(mClickListener);

		/*--------------------------------------------*/
		
		mAdd = (Button)findViewById(R.id.add);
		mAdd.setOnClickListener(mClickListener);
		
		mSubtract = (Button)findViewById(R.id.subtract);
		mSubtract.setOnClickListener(mClickListener);
		
		mMultiply = (Button)findViewById(R.id.multiply);
		mMultiply.setOnClickListener(mClickListener);
		
		mDivide = (Button)findViewById(R.id.divide);
		mDivide.setOnClickListener(mClickListener);

		/*--------------------------------------------*/
		
		mDelete = (Button)findViewById(R.id.delete);
		mDelete.setOnClickListener(mClickListener);
		
		mClear = (Button)findViewById(R.id.clear);
		mClear.setOnClickListener(mClickListener);

		/*--------------------------------------------*/
		
		mInput = (TextView)findViewById(R.id.input);
		mInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		
		mResult = (TextView)findViewById(R.id.result);
		mResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
	}

	private String getLastDataString() {
		String str = "";
		if (mInput != null) {
            str = mInput.getText().toString();
            int index = -1;
            while((index = getNextSymbolIndex(str)) > -1) {
            	str = str.substring(index+1);
            }
		}

        return str;
	}

    private void digitButtonPressed(String str) {
		if (mInput != null) {
	    	if(str.equalsIgnoreCase(".")) {
			    String lastDataString =  getLastDataString();
	    	    String temp = lastDataString + str;
	    	    Matcher m = INVALID_DATA_REGULAR.matcher(temp);
	    	    if (m.matches()) {
	    	        // do nothing;
	    		    return ;
	    	    }
	    	}

	    	mInput.setText(mInput.getText().toString() + str);
		}
    }

    private boolean checkLastCharIsOperator () {
        if(mInput != null && !TextUtils.isEmpty(mInput.getText().toString())) {
            String str = mInput.getText().toString();
            char ch = str.charAt(str.length()-1);
            switch (ch) {
            case '+':
            case '-':
            case 'x':
            case '/':
                return true;

            default:
                return false;
            }
        }

        return false;
    }

    private void symbolButtonPressed(String str) {
    	if (checkLastCharIsOperator()) {
             return; // last char is operator, do nothing.
    	}
    	
    	String string = mInput.getText().toString();
        mInput.setText( string + str);
    }

	private View.OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (arg0 != null) {
				switch(arg0.getId()){
				case R.id.one:
					digitButtonPressed("1");
					break;
					
				case R.id.two:
					digitButtonPressed("2");
					break;
					
				case R.id.three:
					digitButtonPressed("3");
					break;
					
				case R.id.four:
					digitButtonPressed("4");
					break;
					
				case R.id.five:
					digitButtonPressed("5");
					break;
				
				case R.id.six:
					digitButtonPressed("6");
					break;
					
				case R.id.seven:
					digitButtonPressed("7");
					break;
				
				case R.id.eight:
					digitButtonPressed("8");
					break;
				
				case R.id.nine:
					digitButtonPressed("9");
					break;
					
				case R.id.zero:
					digitButtonPressed("0");
					break;
					
				case R.id.dot:
					digitButtonPressed(".");
					break;
				
				case R.id.add:
					symbolButtonPressed("+");
					break;
					
				case R.id.subtract:
					symbolButtonPressed("-");
					break;
				
				case R.id.multiply:
					symbolButtonPressed("x");
					break;
					
				case R.id.divide:
					symbolButtonPressed("/");
					break;
					
				case R.id.equal:
					pressedEqualButton();
					break;
					
				case R.id.clear:
					clear();
				    break;
				    
				case R.id.delete:
					pressedDeleteButton();
					break;
				    
				default:
					loge("no support this operator, do nothing!");
					break;
				}
			}
		}
	};
	
	private double caculator(double paramA, double paramB, int operator, boolean existParamB) {
        double result = 0.0;
		switch (operator) {
		case ADD:
            if(!existParamB) {
            	paramB = 0.0;
            }
			result = paramA + paramB;
            break;

		case SUB:
            if(!existParamB) {
            	paramB = 0.0;
            }
			result = paramA - paramB;
            break;

		case MULTI:
            if(!existParamB) {
            	paramB = 1.0;
            }
			result = paramA * paramB;
            break;

		case DIV:
            if(!existParamB) {
            	paramB = 1.0;
            }

            if (paramB != 0) {
				result = paramA / paramB;
            } else {
                loge("divide param can't be zero");
    			result = INVALID;
            }
            break;

        default:
        	result = INVALID;
            break;
		}

		if(DEBUG) {
            logi(paramA + convertSymbol2String(operator) + paramB + "=" + result);
        }
        return result;
	}

    private boolean compareDoubleValue(double a, double b) {
        if ((a-b) > PRECISION_LOW && (a-b) < PRECISION_UP) {
            return true;
        }

        return false;
    }

    private double caculator() {
        double firstValue = 0.0;
        double secondValue = 0.0;
        double thirdValue = 0.0;

        int firstOperator = NONE;
        int secondOperator = NONE;

        boolean firstFlag = true;
        boolean allowPop2ndData = true;

        boolean exist2ndValue = false;

        do {
            if (firstFlag) {
                firstFlag = false;
                firstValue = mDataStack.pop();
            }

            if (!mDataStack.isEmpty()) {
                exist2ndValue = true;
                if (allowPop2ndData) {
                    secondValue = mDataStack.pop();
                }
            } else if(mDataStack.isEmpty() && allowPop2ndData) {
            	exist2ndValue = false;
            }

            if (!mSymbolStack.isEmpty()) {
                if (firstOperator == NONE) {
                    firstOperator = mSymbolStack.pop();
                }
            }

            if (!mSymbolStack.isEmpty()) {
                if (secondOperator == NONE) {
                    secondOperator = mSymbolStack.pop();
                }
            }

            if (firstOperator == NONE) {
                break;
            } else if (compareSymbolPriority(firstOperator, secondOperator) == MORE ||
                    compareSymbolPriority(firstOperator, secondOperator) == EQUAL) {
                firstValue = caculator(firstValue, secondValue, firstOperator, exist2ndValue);
                allowPop2ndData = true;
                if (compareDoubleValue(firstValue, INVALID)) {
                    reset();
                    return INVALID;
                } else {
                    secondValue = 0.0;
                    firstOperator = secondOperator;
                    secondOperator = NONE;
                }
			} else if (compareSymbolPriority(firstOperator, secondOperator) == LESS) {
                boolean existThirdValue = false;
                if (!mDataStack.isEmpty()) {
                	existThirdValue = true;
                    thirdValue = mDataStack.pop();
                }

                secondValue = caculator(secondValue, thirdValue, secondOperator, existThirdValue);
                allowPop2ndData = false;
                if (compareDoubleValue(secondValue, INVALID)) {
                    reset();
                    return INVALID;
                } else {
                    thirdValue = 0.0;
                    secondOperator = NONE;
                }
            }
        } while (firstOperator != NONE);

        return firstValue;
	}

    private void reset() {
        mDataStack.clear();
        mSymbolStack.clear();
    }
    
    private void clear() {
    	reset();
    	updateInput("");
    	updateResult("");
    }

    private int compareSymbolPriority(int symbolA, int symbolB) {
        int priorityA = mapSymbolPriority(symbolA);
        int priorityB = mapSymbolPriority(symbolB);
        if (priorityA > priorityB) {
            return MORE;
        } else if (priorityA == priorityB) {
            return EQUAL;
        } else {
            return LESS;
        }
    }

    private int mapSymbolPriority(int symbol) {
        switch (symbol) {
        case ADD:
        case SUB:
            return 1;

        case MULTI:
        case DIV:
            return 2;

        default:
            return 0;
        }
    }

 	private void updateResult(String info) {
	    if (mResult != null) {
		    mResult.setText(info);
	    }
	}
	
	private void showErrorToast(String errorMessage) {
        // for debug
		if (DEBUG) {
		    Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void pressedDeleteButton() {
        String str = mInput.getText().toString();
	    int length = str.length();
        if (length > 0) {
        	updateInput(str.substring(0, length-1));
        	updateResult("");
        }
	}

	private void pressedEqualButton() {
        String input = null;
        if (mInput != null) {
            input = mInput.getText().toString();
        }
        parseInput(input);

        double result = caculator();
        if (compareDoubleValue(result,INVALID)) {
            showErrorToast("divide param can't be zero");
            updateResult("can't divide by 0");
        } else {
        	updateResult(result);
        }
    }
	
    private void updateResult(double result) {
        if (mResult != null) {
        	mResult.setText(String.valueOf(Double.valueOf(result)));
        }
    }

    private void updateInput(String input) {
        if (mInput != null) {
        	mInput.setText(input);
        }
    }

    private int getNextSymbolIndex(String input) {
        int index = -1;
        if (TextUtils.isEmpty(input)) {
            loge("no input, do nothing!");
        } else {
            int add = input.indexOf("+");
            int sub = input.indexOf("-");
            int multi = input.indexOf("x");
            int div = input.indexOf("/");

            if (add >= 0) {
                index = add;
            }
            
            if (sub >= 0) {
                if (index < 0) {
                    index = sub;
                } else {
                    index = Math.min(index, sub);
                }
            }
            
            if (multi >= 0) {
                if (index < 0) {
                    index = multi;
                } else {
                    index = Math.min(index, multi);
                }
            }
            
            if (div >= 0) {
                if (index < 0) {
                    index = div;
                } else {
                    index = Math.min(index, div);
                }
            }
        }

        return index;
    } 

    private int convertString2Symbol(char ch) {
        int symbol = NONE;
        switch (ch) {
        case '+':
            symbol = ADD;
            break;

        case '-':
            symbol = SUB;
            break;

        case 'x':
            symbol = MULTI;
            break;

        case '/':
            symbol = DIV;
        break;

        default:
        	loge("symbol string is null or empty!");
            break;
        }
        return symbol;
	}

    private String convertSymbol2String(int symbol) {
        switch (symbol) {
        case ADD:
            return "+";

        case SUB:
            return "-";

        case MULTI:
            return "x";

        case DIV:
            return "/";

        default:
			return "NONE";
        }
	}

    private void parseInput(String input){
        if (TextUtils.isEmpty(input)) {
            loge("no input, do nothing!");
            return ;
        }

		reset();
		
        // -25+32
        int index = -1;
        double value;
        do {
            index = getNextSymbolIndex(input);
            value = 0.0;

	        if (index == 0) { // mean the first value exist symbol.
	            index = getNextSymbolIndex(input.substring(1));
	            if (index == -1) {
	            	if (input.length() > 1) { // mean the first value is not only symbol.
	                    value = Double.valueOf(input).doubleValue();
	                }
	            } else {
	                index += 1;
                    value = Double.valueOf(input.substring(0, index)).doubleValue();
	            }
	        } else if (index > 0) {
                value = Double.valueOf(input.substring(0, index)).doubleValue();
            } else {
                if (!TextUtils.isEmpty(input)) {
                    value = Double.valueOf(input).doubleValue();
                }
            }

            mDataStack.push(value);

            if (index != -1) {
                mSymbolStack.push(convertString2Symbol(input.charAt(index)));
            }

            if (index != -1) {
                input = input.substring(index+1);
            }
        } while (index != -1 && !TextUtils.isEmpty(input));

        mDataStack.dump();
        mSymbolStack.dump();
    }

    private void logi(String str) {
        if (DEBUG) {
    	    Log.i(TAG, str);
        }
    }

    private void loge(String str) {
    	Log.e(TAG, str);
    }

	public class StackData {
        private static final String TAG = "StackData";
        private double[] mData = null;
        private static final int DEEP = 100;
        public static final double INVALID_DATA = 4294967295.0;
        private int mLength = 0;
        private int mIndex = 0;

        public StackData() {
            mData = new double[DEEP];
        }
 
        public void push(double data) {
            if (mLength < DEEP) {
                mData[mLength++] = data;
            } else {
                loge("StackData is full, can't push any data!");
            }
        }

        public double pop() {
            if (mLength > 0) {
                double data = mData[mIndex];
                mData[mIndex++] = 0.0;
                mLength--;
                if (mLength == 0) {
                    mIndex = 0;
                }
                return data;
            } else {
                loge("StackData is empty, can't pop any data!");
                mIndex = 0;
                return 0.0;
            }
		}

        public boolean isEmpty() {
            if (mLength == 0) {
                return true;
            }

            return false;
        }

        public void clear() {
            while(mLength > 0) {
                pop();
            }
        }

        public void dump() {
        	if(DEBUG) {
                logi("----------data dump begin--------------");
                StringBuilder sb = new StringBuilder();
                for (int i=0; i<mLength; i++) {
                    sb.append(mData[i]);
                    sb.append(", ");
                }
                logi(sb.toString());
                logi("----------data dump end--------------");
        	}
        }

        private void logi(String str) {
        	Log.i(Caculator.TAG, "[" + TAG + "]" + str);
        }

        private void loge(String str) {
        	Log.i(Caculator.TAG, "[" + TAG + "]" + str);
        }
	}

    public class StackSymbol {
        private static final String TAG = "StackSymbol";
        private static final int DEEP = 100;

        private int[] mData = null;
        private int mLength = 0;
        private int mIndex = 0;

        public StackSymbol() {
            mData = new int[DEEP];
        }
 
        public void push(int data) {
            if (mLength < DEEP) {
                mData[mLength++] = data;
            } else {
                loge("StackSymbol is full, can't push any data!");
            }
        }

        public int pop() {
            if (mLength > 0) {
                int data = mData[mIndex];
                mData[mIndex++] = 0xffffffff;
                mLength--;
                if (mLength == 0) {
                    mIndex = 0;
                }
                return data;
            } else {
                loge("StackSymbol is empty, can't pop any data!");
                mIndex = 0;
                return NONE;
            }
		}

        public boolean isEmpty() {
            if (mLength == 0) {
                return true;
            }

            return false;
        }

        public void clear() {
            while(mLength > 0) {
                pop();
            }
        }

        public void dump() {
        	if(DEBUG) {
        	    logi("----------symbol dump begin--------------");
        	    StringBuilder sb = new StringBuilder();
        	    for (int i=0; i<mLength; i++) {
        	        sb.append(convertSymbol2String(mData[i]));
        	        sb.append(", ");
        	    }
        	    logi(sb.toString());
        	    logi("----------symbol dump end--------------");
        	}
        }

        private void logi(String str) {
        	Log.i(Caculator.TAG, "[" + TAG + "]" + str);
        }

        private void loge(String str) {
        	Log.e(Caculator.TAG, "[" + TAG + "]" + str);
        }
	}
}

