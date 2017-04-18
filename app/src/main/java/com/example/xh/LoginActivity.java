package com.example.xh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;

import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via account/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0x1;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mAccountView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private  String[] accounts = { "18236593333", "13463373657", "18235784765", "18234637686" };
    private TextInputLayout accountinput,passwordinput;
    private Button mAccountSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mAccountSignInButton = (Button) findViewById(R.id.account_sign_in_button);
        mAccountView = (AutoCompleteTextView) findViewById(R.id.account);
        accountinput=(TextInputLayout) findViewById(R.id.accountinput);
        passwordinput=(TextInputLayout) findViewById(R.id.passwordinput);
        mPasswordView=(EditText)findViewById(R.id.password);
        //accountinput.setHint("username");//和子EditText(给EditText通过.setHint("")设置hint时不能出现浮动标签，要在布局文件设置) hint同时使用，此有效
        //populateAutoComplete();
        initBaiDuStatistics();
        //是在我们编辑完之后点击软键盘上的确定键键才会触发
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ( id == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    //inputMethodManager.showSoftInput(getWindow().getDecorView(),InputMethodManager.SHOW_FORCED);//显示
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    //attemptLogin();
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mAccountView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(accountinput.getError()!=null){
                    accountinput.setError(null);
                }
               String s1= s.toString();
                if (s1.equals("")||passwordinput.getEditText().getText().toString().equals("")){
                    mAccountSignInButton.setClickable(false);
                    mAccountSignInButton.setTextColor(getResources().getColor(R.color.btninvalid));
                }else{
                    mAccountSignInButton.setClickable(true);
                    mAccountSignInButton.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(passwordinput.getError()!=null){
                    passwordinput.setError(null);
                }
                String s1= s.toString();
                if (s1.equals("")||accountinput.getEditText().getText().toString().equals("")){
                    mAccountSignInButton.setClickable(false);
                    mAccountSignInButton.setTextColor(getResources().getColor(R.color.btninvalid));
                }else{
                    mAccountSignInButton.setClickable(true);
                    mAccountSignInButton.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        mAccountSignInButton.setOnClickListener(this);
        mAccountSignInButton.setClickable(false);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,accounts);
        mAccountView.setAdapter(arrayAdapter);//输入至少两个字符才会提示
    }
    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
         */
        StatService.onPause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        StatService.onResume(this);
    }
    private void initBaiDuStatistics() {
        StatService.setAppChannel(this, "code4android", true);
        StatService.setSessionTimeOut(1);
        StatService.setOn(this, StatService.EXCEPTION_LOG);
        StatService.setDebugOn(true);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mAccountView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid account, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String account = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        //或者使用下面获取文本,
        String account1 = accountinput.getEditText().getText().toString();
        String password1 = passwordinput.getEditText().getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid account address.
       if (TextUtils.isEmpty(account)||!isAccountValid(account)) {
            //mAccountView.setError(getString(R.string.error_invalid_account));
            accountinput.setError(getString(R.string.error_invalid_account));
            focusView = mAccountView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!cancel&&(TextUtils.isEmpty(password)||!isPasswordValid(password))) {
            passwordinput.setError(getString(R.string.error_invalid_password));
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(account, password);
            mAuthTask.execute((Void) null);
        }
    }
    private boolean isAccountValid(String name) {
        //TODO: Replace this with your own logic
        Pattern pattern= Pattern.compile("^(13[0-9]|14[5|7]|15\\d|17[6|7]|18[\\d])\\d{8}$");
        return pattern.matcher(name).matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_sign_in_button:
                attemptLogin();
                break;
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mAccount;
        private final String mPassword;

        UserLoginTask(String account, String password) {
            mAccount = account;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();


            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

